package com.edu.ecommerce.service.impl;


import com.edu.ecommerce.configuration.MessageStrings;
import com.edu.ecommerce.exceptions.AuthenticationFailException;
import com.edu.ecommerce.model.AuthenticationToken;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.repository.TokenRepository;
import com.edu.ecommerce.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenRepository repository;

    // save the confirmation token
    @Transactional
    @Override
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    @Transactional(readOnly = true)
    @Override
    public AuthenticationToken getToken(User user) {
        return repository.findTokenByUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(String token) {
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
        if (Objects.nonNull(authenticationToken)) {
            if (Objects.nonNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    // check if the token is valid
    public void authenticate(String token) throws AuthenticationFailException {
        if (!Objects.nonNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if (!Objects.nonNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }
}