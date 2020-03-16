package com.andrei.mobiletracker.user.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
class RefreshTokenKey implements Serializable {

    private String userAccount;
    private String token;
}
