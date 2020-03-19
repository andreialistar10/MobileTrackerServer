package com.andrei.mobiletracker.user.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Entity
@Table(name = "USER_ACCOUNTS")
public class UserAccount {

    @Id
    @Column(name = "USERNAME")
    @Length(max = 255)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER_ROLE", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserAccountRole role;
}
