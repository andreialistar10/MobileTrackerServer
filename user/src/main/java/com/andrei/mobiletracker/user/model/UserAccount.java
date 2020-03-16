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

    @ManyToOne(targetEntity = UserAccountRole.class, optional = false)
    @JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "USER_ROLE_ID")
    private UserAccountRole role;
}
