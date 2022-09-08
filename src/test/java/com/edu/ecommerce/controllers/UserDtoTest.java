package com.edu.ecommerce.controllers;

//import static org.junit.jupiter.api.Assertions.*;
//
//
//import com.edu.ecommerce.dto.user.UserDto;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import java.util.Iterator;
//import java.util.Set;
//
//class UserDtoTest {
//    private static ValidatorFactory validatorFactory;
//    private static Validator validator;
//
//    @BeforeAll
//    public static void createValidator() {
//        validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
//    }
//
//    @AfterAll
//    public static void close() {
//        validatorFactory.close();
//    }
//
//
//    @Test
//    public void shouldHaveNoViolations() {
//        //given:
//        UserDto userDto = createUserDtoHasNoViolations();
//        //when:
//        Set<ConstraintViolation<UserDto>> violations
//                = validator.validate(userDto);
//        //then:
//        assertTrue(violations.isEmpty());
//    }
//
//    @Test
//    public void shouldHaveViolationsWithEmail() {
//        //given:
//        String errorEmail = ".jfksjf6dthsreh";
//        UserDto userDto = createUserDtoHasNoViolations();
//        userDto.setEmail(errorEmail);
//
//        //when:
//        Set<ConstraintViolation<UserDto>> violations
//                = validator.validate(userDto);
//
//        //then:
//        assertTrue(violations.size() == 1);
//
//        Iterator<ConstraintViolation<UserDto>> violationIterator = violations.iterator();
//        ConstraintViolation<UserDto> constraintViolation = violationIterator.next();
//
//        assertEquals(errorEmail, constraintViolation.getInvalidValue());
//    }
//
//    private UserDto createUserDtoHasNoViolations() {
//        UserDto userDto = new UserDto();
//        userDto.setFirstNameRu("Александр");
//        userDto.setLastNameRu("Федоров");
//        userDto.setFirstNameEn("Aleksandr");
//        userDto.setLastNameEn("Fedorov");
//        userDto.setBirthday(null);
//        userDto.setSkype(null);
//        userDto.setEmail("a.fed-or_ov@andersenlab.com");
//        userDto.setPhone(null);
//        userDto.setStatus(null);
//        userDto.setRoleId(null);
//        userDto.setOfficeId(null);
//        userDto.setTrainingGroupIds(null);
//        userDto.setTrainingIds(null);
//        return userDto;
//    }
//}