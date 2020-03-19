package com.andrei.mobiletracker.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REFRESH_TOKENS")
@IdClass(RefreshTokenKey.class)
public class RefreshToken {

    @Id
    @ManyToOne(targetEntity = UserAccount.class)
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
    private UserAccount userAccount;

    @Id
    @Column(name = "TOKEN")
    private String token;
}
