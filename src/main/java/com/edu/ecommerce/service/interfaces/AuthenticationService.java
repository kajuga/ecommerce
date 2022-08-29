package com.edu.ecommerce.service.interfaces;

import com.edu.ecommerce.exceptions.AuthenticationFailException;
import com.edu.ecommerce.model.AuthenticationToken;
import com.edu.ecommerce.model.User;

public interface AuthenticationService {

    public void saveConfirmationToken(AuthenticationToken authenticationToken);
    public AuthenticationToken getToken(User user);
    public User getUser(String token);
    public void authenticate(String token) throws AuthenticationFailException;

}
