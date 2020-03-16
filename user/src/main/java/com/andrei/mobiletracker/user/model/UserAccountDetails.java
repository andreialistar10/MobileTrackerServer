package com.andrei.mobiletracker.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "USER_ACCOUNT_DETAILS")
public class UserAccountDetails {

    @Id
    @Column(name = "USERNAME")
    @Length(max = 255)
    private String username;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @PrimaryKeyJoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
//    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
//    @MapsId
    private UserAccount user;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String email;
}
