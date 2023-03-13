package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.exceptions.ArgumentNotValidException;
import com.edu.ecommerce.exceptions.ResourceNotFoundException;
import com.edu.ecommerce.model.File;
import com.edu.ecommerce.model.FileType;
import com.edu.ecommerce.repository.FileRepository;
import com.edu.ecommerce.service.interfaces.FileService;
import com.edu.ecommerce.service.util.interfaces.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileManager fileManager;
    private final FileRepository fileRepository;

    private static final String[] photoFormats = {".jpg", ".jpeg", ".png"};

    private static final String[] docsFormats = {".pdf", ".doc", ".docx", ".xlsx", ".txt"};

    private static final String[] excludedFormats = {".ade", ".adp", ".apk", ".appx", ".appxbundle", ".bat", ".cab", ".chm",
            ".cmd", ".com", ".cpl", ".dll", ".dmg", ".exe", ".hta", ".ins", ".isp", ".iso", ".jar", ".js", ".jse", ".lib",
            ".lnk", ".mde", ".msc", ".msi", ".msix", ".msixbundle", ".msp", ".mst", ".nsh", ".pif", ".ps1", ".scr", ".sct",
            ".shb", ".sys", ".vb", ".vbe", ".vbs", ".vxd", ".wsc", ".wsf", ".wsh", ".sh"};

    private static final int MAX_FILE_SIZE = 5 * 1024 * 1024;

    @Transactional(readOnly = true)
    @Override
    public File findById(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("File with id: " + fileId + " not found!"));
    }

    @Transactional
    @Override
    public File uploadFile(MultipartFile resource, FileType type) throws FileSystemException {
        checkFormatAndSize(resource.getOriginalFilename(), resource.getSize(), type);
        File newFile = File.builder()
                .name(resource.getOriginalFilename())
                .size(resource.getSize())
                .createdDate(LocalDateTime.now())
                .build();

        File createdFile = fileRepository.save(newFile);
        return Optional.of(createdFile).orElseThrow(() -> new FileSystemException("File was not uploaded"));
    }

    @Transactional(rollbackFor = {FileSystemException.class})
    @Override
    public File uploadFileIcon(MultipartFile resource, FileType type) throws FileSystemException {
        checkFormatAndSize(resource.getOriginalFilename(), resource.getSize(), type);
        File createdFile = null;

        try {
            byte[] avatar = scaleAvatar(resource.getBytes());
            createdFile = createFile(avatar, resource.getOriginalFilename(), resource.getSize(), type);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(createdFile).orElseThrow(() -> new FileSystemException("File was not uploaded"));
    }

    @Override
    public Resource downloadFile(File file) {
        return fileManager.download(file);
    }

    @Transactional
    @Override
    public File deleteById(Long fileId) {
        File file = findById(fileId);
        fileRepository.delete(file);
//delete file from local path realisation
//        fileManager.delete(file);
        return file;
    }

    @Override
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }


    private void checkFormatAndSize(String name, long size, FileType type) throws FileSystemException {
        if (isNonContainFormats(photoFormats, name, FileType.PHOTO, type) ||
                isNonContainFormats(ArrayUtils.addAll(photoFormats, docsFormats), name, FileType.OTHER, type)) {
            throw new FileSystemException("Incorrect format for transmitted file !");

        }
        if (size >= MAX_FILE_SIZE) {
            throw new FileSystemException("File size exceeds 5MB !");
        }

    }

    private boolean isNonContainFormats(String[] availableFormats, String name, FileType toCompare,
                                        FileType requestType) {
        return Arrays.stream(availableFormats).noneMatch(format -> Objects.requireNonNull(name).contains(format)) &&
                toCompare.equals(requestType);
    }

    private File createFile(byte[] resource, String name, Long size, FileType type) throws IOException {

        String objectKey = generateKey();
        if (FileType.PHOTO.equals(type) && name != null || FileType.OTHER.equals(type) && name != null) {

            File newFile = File.builder()
                    .name(name)
                    .size(size)
                    .createdDate(LocalDateTime.now())
                    .build();

            File createdFile = fileRepository.save(newFile);
            fileManager.upload(resource);

            return createdFile;
        } else {
            throw new ArgumentNotValidException("This Type isn't PHOTO, OTHER or name is null");
        }
    }

    private String generateKey() {
        return UUID.randomUUID() + LocalDateTime.now().toString().replace(":", "-").replace(".", "-");
    }

    private byte[] scaleAvatar(byte[] imageData) throws IOException {
        try (ByteArrayInputStream input = new ByteArrayInputStream(imageData)) {
            BufferedImage image = ImageIO.read(input);

            var width = image.getWidth();
            var height = image.getHeight();

            if (width <= 200 && height <= 200) {
                return imageData;
            }
            var horizontalScaleRate = 200 / (double) width;
            var verticalScaleRate = 200 / (double) height;
            var scaleRate = Math.max(horizontalScaleRate, verticalScaleRate);

            var widthScaled = (int) (width * scaleRate);
            var heightScaled = (int) (height * scaleRate);
            var imageType = (image.getType() == 0) ? BufferedImage.TYPE_INT_ARGB : image.getType();

            var resizedImage = new BufferedImage(widthScaled, heightScaled, imageType);
            Graphics2D g = resizedImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(image, 0, 0, widthScaled, heightScaled, null);
            g.dispose();
            g.setComposite(AlphaComposite.Src);

            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                ImageIO.write(resizedImage, "jpg", output);
                return output.toByteArray();
            }
        }
    }
}