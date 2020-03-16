package com.andrei.mobiletracker.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "NOT_ACTIVATED_ACCOUNTS")
public class NotActivatedAccount {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "TOKEN", unique = true, nullable = false)
    private String token;
}
