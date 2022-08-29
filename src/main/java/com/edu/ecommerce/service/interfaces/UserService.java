package com.edu.ecommerce.service.interfaces;

import com.edu.ecommerce.dto.login.LoginDto;
import com.edu.ecommerce.dto.user.SignInResponseDto;
import com.edu.ecommerce.dto.user.SignUpDto;
import com.edu.ecommerce.dto.user.SignUpResponseDto;
import com.edu.ecommerce.exceptions.AuthenticationFailException;
import com.edu.ecommerce.exceptions.CustomException;
import com.edu.ecommerce.model.User;
import java.util.List;

/**
 * The service to the maintain operations with user.
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


    SignUpResponseDto signUp(SignUpDto signupDto) throws CustomException;

    SignInResponseDto signIn(LoginDto loginDto) throws AuthenticationFailException, CustomException;

}