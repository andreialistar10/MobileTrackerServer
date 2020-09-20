package com.andrei.mobiletracker.location.beans.annotation.constraint.validator;

import com.andrei.mobiletracker.location.beans.annotation.constraint.LocationTimestampConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocationTimestampValidator implements ConstraintValidator<LocationTimestampConstraint, Long> {

   public boolean isValid(Long timestamp, ConstraintValidatorContext context) {
      return timestamp == null || timestamp < System.currentTimeMillis();
   }
}
