package com.andrei.mobiletracker.user.beans.annotation.constraint.dtoConstraint;

import com.andrei.mobiletracker.user.beans.annotation.constraint.dtoConstraint.validator.MyUserDetailRequestDtoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Spring MVC Custom Validation using AspectJ
 * Validation method that will be called when we want to validate an Entity is 'isValid' function from EntityValidator
 * When we will put '@Valid' annotation before an 'Entity' parameter, the possible error that may occur will be put in BindingResult
 * see in addEntity from Controller)
 * more information here: https://www.baeldung.com/spring-mvc-custom-validator
 * or ask me :P
 * (of course, we can define constraints for fields like @NotNull or @NotEmpty that already exist in Java)
 */
@Documented
@Constraint(validatedBy = MyUserDetailRequestDtoValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MyUserDetailRequestDtoConstraint {
    String message() default "Passwords does not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
