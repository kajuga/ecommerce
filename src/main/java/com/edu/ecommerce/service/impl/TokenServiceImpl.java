package com.edu.ecommerce.service.impl;

import com.edu.ecommerce.configuration.MessageStrings;
import com.edu.ecommerce.exceptions.AuthenticationFailException;
import com.edu.ecommerce.model.JwtToken;
import com.edu.ecommerce.model.Login;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.security.UserPrincipal;
import com.edu.ecommerce.service.interfaces.RoleService;
import com.edu.ecommerce.service.interfaces.TokenService;
import com.edu.ecommerce.service.interfaces.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.util.Optional.ofNullable;


@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private static final String CRM_ISSUER = "crm-ha-app";

    private final UserService userService;
    private final RoleService roleService;

    private final String secretKey;

    public TokenServiceImpl(Environment environment, @Lazy UserService userService, RoleService roleService) {
        secretKey = ofNullable(environment.getProperty("secret.key"))
                .orElseThrow(() -> new IllegalArgumentException("Required property: 'secret.key'"));
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public JwtToken createToken(Login login) throws AuthenticationFailException, NoSuchAlgorithmException {
        var user = authentication(login);

        var role = roleService.findByUserEmail(user.getEmail());
        return createToken(user.getId(), role.getName());
    }

    @Override
    public UserDetails getUserDetails(String token) {
        if (Objects.isNull(token) || !Jwts.parser().isSigned(token)) {
            throw new AuthenticationServiceException("Signed id_token required.");
        }
        var claims = getClaims(token);
        var cid = getUid(claims);
        var userRole = (String) claims.get("role");

        SimpleGrantedAuthority authority;
        if (userRole != null) {
            authority = new SimpleGrantedAuthority(userRole);
        } else {
            throw new AuthenticationServiceException("User must have permissions for login");
        }

        return UserPrincipal.builder()
                .id(cid)
                .authorities(List.of(authority))
                .build();
    }



    private User authentication(Login login) throws AuthenticationFailException, NoSuchAlgorithmException {
        var user = userService.findByEmail(login.getEmail());
        if(!Objects.nonNull(user)){
            throw new AuthenticationFailException("user not present");
        }
        if (!user.getPassword().equals(hashPassword(login.getPassword()))) {
             throw new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        return user;
    }


    private Claims getClaims(String token) {
        return Jwts.parser()
                .requireIssuer(CRM_ISSUER)
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    private Long getUid(Claims claims) {
        return Optional.ofNullable(claims.get("uid"))
                .map(v -> ((Number) v).longValue())
                .orElse(null);
    }

    private JwtToken createToken(Long uid, String role) {
        var dateTime = LocalDateTime.now();
        var issuedDateInstant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        var expirationDateInstant = dateTime.plusHours(12).atZone(ZoneId.systemDefault()).toInstant();

        var customHeader = new HashMap<String, Object>();
        customHeader.put("typ", "JWT");

        var customClaims = new HashMap<String, Object>();
        customClaims.put("uid", uid);
        customClaims.put("role", role);

        var key = new SecretKeySpec(secretKey.getBytes(), HS512.getJcaName());

        var jwtBuilder = Jwts.builder()
                .setHeader(customHeader)
                .addClaims(customClaims)
                .setIssuer(CRM_ISSUER)
                .setIssuedAt(Date.from(issuedDateInstant))
                .setExpiration(Date.from(expirationDateInstant))
                .signWith(HS512, key);

        return new JwtToken(jwtBuilder.compact(), "Bearer", Duration.between(issuedDateInstant, expirationDateInstant).toSeconds(), role, uid);
    }


    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }

}