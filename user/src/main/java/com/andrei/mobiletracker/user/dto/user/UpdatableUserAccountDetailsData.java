package com.andrei.mobiletracker.user.dto.user;

import com.andrei.mobiletracker.user.beans.annotation.constraint.fieldConstraint.NameConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatableUserAccountDetailsData {

    @NameConstraint
    private String lastName;

    @NameConstraint
    private String firstName;
}
