package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.exceptions.ArgumentNotValidException;
import com.edu.ecommerce.exceptions.CrmException;
import com.edu.ecommerce.exceptions.ResourceNotFoundException;

import com.edu.ecommerce.model.User;
import com.edu.ecommerce.repository.RoleRepository;
import com.edu.ecommerce.repository.UserRepository;
import com.edu.ecommerce.security.AuthenticatedUser;
import com.edu.ecommerce.service.interfaces.DataService;
import com.edu.ecommerce.service.interfaces.UserService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;


/**
 * The service implementation to the maintain operations with user.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticatedUser authenticatedUser;
    private final RoleRepository roleRepository;
    private final DataService dataService;

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
            return userRepository.save(user);
        } else {
            throw new ArgumentNotValidException("Not enough rights.");
        }
    }

    @Transactional
    @Override
    public User update(Long id, User requestUser) {
        User userById = findById(id);
        checkUpdateAccess(userById, requestUser);
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

    private boolean isUserEmailAlreadyExist(String email) {

        var count = userRepository.countByEmail(email);
        return Objects.nonNull(count) && count > 0;
    }

    private void checkUserEmailExist(String email) {
        if (isUserEmailAlreadyExist(email)) {
            throw new CrmException(String.format("User with email = %s is already exist.", email));
        }
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

}