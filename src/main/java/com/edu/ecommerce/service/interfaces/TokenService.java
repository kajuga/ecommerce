package com.edu.ecommerce.service.interfaces;

import com.edu.ecommerce.exceptions.AuthenticationFailException;
import com.edu.ecommerce.model.JwtToken;
import com.edu.ecommerce.model.Login;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.NoSuchAlgorithmException;

public interface TokenService {

    /**
     * Generate token from user credentials
     *
     * @param user user's credentials
     * @return token
     */
    JwtToken createToken(Login user) throws AuthenticationFailException, NoSuchAlgorithmException;

    /**
     * Get userDetails by token
     *
     * @param token generated token
     * @return userDetails
     */
    UserDetails getUserDetails(String token);



}