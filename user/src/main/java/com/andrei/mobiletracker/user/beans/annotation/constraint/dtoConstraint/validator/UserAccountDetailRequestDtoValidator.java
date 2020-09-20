package com.andrei.mobiletracker.user.beans.annotation.constraint.dtoConstraint.validator;

import com.andrei.mobiletracker.user.beans.annotation.constraint.dtoConstraint.UserAccountDetailRequestDtoConstraint;
import com.andrei.mobiletracker.user.dto.user.UserAccountDetailRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserAccountDetailRequestDtoValidator implements ConstraintValidator<UserAccountDetailRequestDtoConstraint, UserAccountDetailRequestDto> {


    @Override
    public boolean isValid(UserAccountDetailRequestDto userAccountDetailRequestDto, ConstraintValidatorContext constraintValidatorContext) {

        return userAccountDetailRequestDto.getPassword().equals(userAccountDetailRequestDto.getRepeatPassword());
    }
}
