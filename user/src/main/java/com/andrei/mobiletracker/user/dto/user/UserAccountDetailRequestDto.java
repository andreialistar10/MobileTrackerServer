package com.andrei.mobiletracker.user.dto.user;

import com.andrei.mobiletracker.user.beans.annotation.constraint.dtoConstraint.UserAccountDetailRequestDtoConstraint;
import com.andrei.mobiletracker.user.beans.annotation.constraint.fieldConstraint.NameConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UserAccountDetailRequestDtoConstraint
public class UserAccountDetailRequestDto {

    @NotBlank
    @Length(max = 255)
    private String username;

    @NotBlank
    @Length(min = 8, max = 255)
    private String password;

    @NotBlank
    @Length(min = 8, max = 255)
    private String repeatPassword;

    @Length(max = 55)
    @NameConstraint(message = "First name must be a well-formed name")
    private String firstName;

    @Length(max = 65)
    @NameConstraint(message = "First name must be a well-formed name")
    private String lastName;

    @Email
    @Length(max = 255)
    private String email;
}
