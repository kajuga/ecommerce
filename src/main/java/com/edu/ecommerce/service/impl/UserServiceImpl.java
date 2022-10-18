package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.configuration.MessageStrings;
import com.edu.ecommerce.dto.login.LoginDto;
import com.edu.ecommerce.dto.user.SignInResponseDto;
import com.edu.ecommerce.dto.user.SignUpDto;
import com.edu.ecommerce.dto.user.SignUpResponseDto;
import com.edu.ecommerce.exceptions.*;

import com.edu.ecommerce.mapper.SignUpUserMapper;
import com.edu.ecommerce.model.AuthenticationToken;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.model.UserRole;
import com.edu.ecommerce.repository.RoleRepository;
import com.edu.ecommerce.repository.UserRepository;
import com.edu.ecommerce.security.AuthenticatedUser;
import com.edu.ecommerce.service.interfaces.AuthenticationService;
import com.edu.ecommerce.service.interfaces.DataService;
import com.edu.ecommerce.service.interfaces.RoleService;
import com.edu.ecommerce.service.interfaces.UserService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * The service implementation to the maintain operations with user.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final AuthenticatedUser authenticatedUser;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final DataService dataService;
    private final AuthenticationService authenticationService;
    private final SignUpUserMapper signUpUserMapper;

    protected static final String ADMINISTRATOR_ROLE = "ADMINISTRATOR";
    protected static final String MANAGER_ROLE = "MANAGER";
    protected static final String SPECIALIST_ROLE = "SPECIALIST";
    protected static final String EXTERNAL_PREFIX = "EXTERNAL_";


    @Transactional
    @Override
    public User create(User user) {
        String userRoleName = user.getUserRole().getName();
        var isCurrentUserAdmin = isUserRoleEquals(ADMINISTRATOR_ROLE);
        var isDestinationUserAdmin = ADMINISTRATOR_ROLE.equals(userRoleName);
        var isCurrentUserManager = isUserRoleEquals(MANAGER_ROLE);
        var isDestinationUserSpecialist = SPECIALIST_ROLE.equals(userRoleName);
        var isCurrentUserSpecialist = isUserRoleEquals(SPECIALIST_ROLE);
        var isDestinationUserExternal = userRoleName.startsWith(EXTERNAL_PREFIX);
        if ((isCurrentUserAdmin && !isDestinationUserAdmin)
                || (isCurrentUserManager && isDestinationUserSpecialist)
                || ((isCurrentUserSpecialist || isCurrentUserManager) && isDestinationUserExternal)
        ) {
            checkUserEmailExist(user.getEmail());
            String encryptedPassword = user.getPassword();
            try {
                encryptedPassword = hashPassword(user.getPassword());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            user.setPassword(encryptedPassword);
            return userRepository.save(user);
        } else {
            throw new ArgumentNotValidException("Not enough rights.");
        }
    }

    @Transactional
    @Override
    public SignUpResponseDto createUnregisteredExternalUser(SignUpDto signupDto) throws CustomException {

        if (!Objects.nonNull(roleService.findById(signupDto.getRoleId()))) {
            throw new CustomException("User role is not exists");
        }
        checkUserEmailExist(signupDto.getEmail());

        try {
            String encryptedPassword = hashPassword(signupDto.getPassword());

            User user = signUpUserMapper.fromDto(signupDto);
            user.setPassword(encryptedPassword);
            //TODO поправить на "искать по преффиксу"
            user.setUserRole(roleService.findById(1003L));
            userRepository.save(user);
            final AuthenticationToken authenticationToken = new AuthenticationToken(user);
            authenticationService.saveConfirmationToken(authenticationToken);
            return new SignUpResponseDto("success", "user created successfully");
        } catch (Exception e) {
            logger.error("Error creating user", e);
            throw new CustomException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public User update(Long id, User requestUser) {
        User userById = findById(id);
        checkUpdateAccess(userById, requestUser);
        String encryptedPassword = requestUser.getPassword();
        try {
            encryptedPassword = hashPassword(requestUser.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        requestUser.setPassword(encryptedPassword);
        dataService.copyNonNullProperties(requestUser, userById);
        return userRepository.save(userById);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        if (!isUserExternal()) {
            return userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id: %s not found!", id)));
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough rights.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        if (!isUserExternal()) {
            return userRepository.findAllByOrder();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough rights.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .filter(v -> !v.isEmpty())
                .map(v -> v.get(0))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User email = %s not found in system.", email)));
    }


    @Override
    public List<User> getAllExternalUsers() {
        return userRepository.findAllExternalUsers();
    }


    @Override
    public List<User> getAllUsersByRoleId(Long id) {
        var byId = roleRepository.findById(id);
        if (byId.isPresent()) {
            var userRole = byId.get();
            return userRepository.findAllByUserRole(userRole);
        } else {
            throw new ResourceNotFoundException(String.format("Role with id: %s not found!", id));
        }
    }


    @Transactional
    @Override
    public User delete(Long id) {
        var findUser = findById(id);
        String userRoleName = findUser.getUserRole().getName();
        var isCurrentUserAdmin = isUserRoleEquals(ADMINISTRATOR_ROLE);
        var isDestinationUserAdmin = ADMINISTRATOR_ROLE.equals(userRoleName);
        var isCurrentUserManager = isUserRoleEquals(MANAGER_ROLE);
        var isDestinationUserSpecialist = SPECIALIST_ROLE.equals(userRoleName);
        var isDestinationUserExternal = userRoleName.startsWith(EXTERNAL_PREFIX);
        var isCurrentUserSpecialist = isUserRoleEquals(SPECIALIST_ROLE);
        if ((isCurrentUserAdmin && !isDestinationUserAdmin)
                || (isCurrentUserManager && (isDestinationUserSpecialist || isDestinationUserExternal))
                || isCurrentUserSpecialist && isDestinationUserExternal) {
            userRepository.deleteById(id);
            return findUser;
        } else {
            throw new ArgumentNotValidException("Not enough rights.");
        }
    }



    void checkUserEmailExist(String email) {
        if (isUserEmailAlreadyExist(email)) {
            throw new CrmException(String.format("User with email = %s is already exist.", email));
        }
    }

    public boolean isUserEmailAlreadyExist(String email) {
        var count = userRepository.countByEmail(email);
        return Objects.nonNull(count) && count > 0;
    }

    @Override
    public Boolean isUserRoleEquals(String role) {
        User currentUser = authenticatedUser.getCurrentUser();
        return role.equals(currentUser.getUserRole().getName());
    }

    private void checkUpdateAccess(User destination, User request) {
        String requestUserRoleName = request.getUserRole().getName();
        String destinationUserRoleName = destination.getUserRole().getName();
        Long currentUserId = authenticatedUser.getCurrentUserId();

        if (isUserExternal()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough rights.");
        }

        var isCurrentUserAdmin = isUserRoleEquals(ADMINISTRATOR_ROLE);
        var isCurrentUserManager = isUserRoleEquals(MANAGER_ROLE);
        var isRequestUserExternal = requestUserRoleName.startsWith(EXTERNAL_PREFIX);
        if (!(isCurrentUserAdmin || isCurrentUserManager) &&
                !requestUserRoleName.equalsIgnoreCase(destinationUserRoleName)
                && !isRequestUserExternal
        ) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough rights.");
        }

        var isDestinationUserAdmin = destinationUserRoleName.equalsIgnoreCase(ADMINISTRATOR_ROLE);
        var isDestinationUserManager = destinationUserRoleName.equalsIgnoreCase(MANAGER_ROLE);
        if ((isCurrentUserManager &&
                (isDestinationUserAdmin ||
                        (isDestinationUserManager && !currentUserId.equals(destination.getId())))
        )) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough rights.");
        }

        var isCurrentUserSpecialist = isUserRoleEquals(SPECIALIST_ROLE);
        var isDestinationUserExternal = destinationUserRoleName.startsWith(EXTERNAL_PREFIX);
        if (isCurrentUserSpecialist &&
                ((!currentUserId.equals(destination.getId())) && !isDestinationUserExternal
                )
        ) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough rights.");
        }
    }

    private boolean isUserExternal() {
        return authenticatedUser.getCurrentUser().getUserRole().getName().startsWith(EXTERNAL_PREFIX);
    }

    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }


    @Override
    public SignInResponseDto signIn(LoginDto loginDto) throws AuthenticationFailException, CustomException {
        User user = findByEmail(loginDto.getEmail());
        if(!Objects.nonNull(user)){
            throw new AuthenticationFailException("user not present");
        }
        try {
            if (!user.getPassword().equals(hashPassword(loginDto.getPassword()))){
                throw  new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);
        if(!Objects.nonNull(token)) {
            throw new CustomException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }

        return new SignInResponseDto ("success", token.getToken());
    }

}