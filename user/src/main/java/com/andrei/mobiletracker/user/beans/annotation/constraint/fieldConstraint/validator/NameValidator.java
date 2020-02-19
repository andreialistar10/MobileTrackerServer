package com.andrei.mobiletracker.user.beans.annotation.constraint.fieldConstraint.validator;

import com.andrei.mobiletracker.user.beans.annotation.constraint.fieldConstraint.NameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<NameConstraint, String> {

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {

        return field!=null && field.matches("^[a-zA-Z][a-zA-Z\\- ]+[a-zA-Z]$");
    }
}
