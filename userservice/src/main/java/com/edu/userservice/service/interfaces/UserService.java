package com.edu.userservice.service.interfaces;

import com.edu.userservice.dto.user.SignInResponseDto;
import com.edu.userservice.dto.user.SignUpDto;
import com.edu.userservice.dto.user.SignUpResponseDto;
import com.edu.userservice.exceptions.CustomException;
import com.edu.userservice.model.User;

import java.util.List;

/**
 * The com.edu.fileservice.service to the maintain operations with user.
 */
public interface UserService {

    /**
     * Saves transmitted user.
     *
     * @param user for create
     * @return created user
     */
    User create(User user);


    /**
     * Create new User by unregistered user.
     *
     * @param signupDto user for create
     * @return created user DTO's
     */
    SignUpResponseDto createUnregisteredExternalUser(SignUpDto signupDto) throws CustomException;

    /**
     * Updates transmitted user.
     *
     * @param id   user id for update
     * @param user user for update
     * @return updated user
     */
    User update(Long id, User user);


    /**
     * Return user for transmitted id.
     *
     * @param id for find user
     * @return user for transmitted parameter
     */
    User findById(Long id);


    /**
     * Returns all users.
     *
     * @return list of users
     */
    List<User> findAll();


    /**
     * Return user for transmitted email.
     *
     * @param email for find user
     * @return user by email
     */
    User findByEmail(String email);

    /**
     * Return all external user.
     * @return list of users
     */
    List<User> getAllExternalUsers();

    /**
     * Return list of users by Role.
     * @return list of users
     */
    List<User> getAllUsersByRoleId(Long id);


    /**
     * Deletes user with transmitted id.
     *
     * @param id user id for delete
     * @return the user to be deleted
     */
    User delete(Long id);


    Boolean isUserRoleEquals(String role);



//    SignInResponseDto signIn(LoginDto loginDto) throws AuthenticationFailException, CustomException;

}