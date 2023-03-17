package com.edu.fileservice.service.util.interfaces;

import com.edu.fileservice.model.File;
import org.springframework.core.io.Resource;

import java.nio.file.FileSystemException;

/**
 * The DAO to the maintain operations with file entity.
 *
 * @author Fedorov Aleksandr
 */
public interface FileManager {

    /**
     * Upload transmitted file with these params
     *
     * @param resource file for upload
     * @return method result message
     */
    void upload(byte[] resource) throws FileSystemException;

    /**
     * Download file by transmitted file entity
     *
     * @param file file entity for download file
     * @return file for transmitted parameter
     */
    Resource download(File file);

    /**
     * Deletes file for transmitted file entity
     *
     * @param file file entity for delete
     */
    void delete(File file);
}
