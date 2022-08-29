package com.edu.ecommerce.aspect;

import com.edu.ecommerce.model.Role;
import com.edu.ecommerce.security.AccessRole;
import com.edu.ecommerce.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Service
@RequiredArgsConstructor
public class AccessRoleAspect {
    private final AuthenticatedUser authenticatedUser;

    @Around(value = "within(@org.springframework.web.bind.annotation.RestController *)" +
            "&& !within(*..UserController) && !within(*..TokenController) && !within(*..ExternalUserController)")
    public Object controllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
            var name = authenticatedUser.getCurrentUser().getUserRole().getName();
            var signature = (MethodSignature) joinPoint.getSignature();
            var method = signature.getMethod();
            var methodAnnotation = method.getAnnotation(AccessRole.class);
            var declaringClass = method.getDeclaringClass();
            var classAnnotation = declaringClass.getAnnotation(AccessRole.class);
            var collectOfClassArgs = Objects.nonNull(classAnnotation) ? Arrays.stream(classAnnotation.value()).map(Role::getName).collect(Collectors.toList()) : null;
            if (Objects.nonNull(methodAnnotation)) {
                var collectOfMethodArgs = Arrays.stream(methodAnnotation.value()).map(Role::getName).collect(Collectors.toList());
                collectOfMethodArgs.addAll(collectOfClassArgs);
                return isRoleInList(joinPoint, name, collectOfMethodArgs);
            } else if (Objects.nonNull(classAnnotation)) {
                return isRoleInList(joinPoint, name, collectOfClassArgs);
            } else {
                return joinPoint.proceed();
            }
        }

    private Object isRoleInList(ProceedingJoinPoint joinPoint, String name, List<String> collectOfMethodArgs) throws Throwable {
        if (collectOfMethodArgs.stream().anyMatch(s -> s.equals(name) || (name.startsWith(s) && s.endsWith("_")))) {
            return joinPoint.proceed();
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough Rights");
        }
    }
}
