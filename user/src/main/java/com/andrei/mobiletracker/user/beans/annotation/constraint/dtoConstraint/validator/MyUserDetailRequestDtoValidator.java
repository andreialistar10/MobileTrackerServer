package com.andrei.mobiletracker.user.beans.annotation.constraint.dtoConstraint.validator;

import com.andrei.mobiletracker.user.beans.annotation.constraint.dtoConstraint.MyUserDetailRequestDtoConstraint;
import com.andrei.mobiletracker.user.dto.MyUserDetailRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyUserDetailRequestDtoValidator implements ConstraintValidator<MyUserDetailRequestDtoConstraint, MyUserDetailRequestDto> {


    @Override
    public boolean isValid(MyUserDetailRequestDto myUserDetailRequestDto, ConstraintValidatorContext constraintValidatorContext) {

        return myUserDetailRequestDto.getPassword().equals(myUserDetailRequestDto.getRepeatPassword());
    }
}
