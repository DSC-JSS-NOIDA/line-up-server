package com.dscjss.lineup.validation;


import com.dscjss.lineup.users.dto.UserDto;
import com.dscjss.lineup.validation.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {


    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        UserDto user = (UserDto) obj;
        if(user.getPassword() == null || user.getMatchingPassword() == null)
            return false;

        return user.getPassword().equals(user.getMatchingPassword());
    }

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }
}
