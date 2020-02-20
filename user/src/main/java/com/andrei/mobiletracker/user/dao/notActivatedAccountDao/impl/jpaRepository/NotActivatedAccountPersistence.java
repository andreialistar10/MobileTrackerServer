package com.andrei.mobiletracker.user.dao.notActivatedAccountDao.impl.jpaRepository;

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
public class NotActivatedAccountPersistence {

    @Id
    @Column(name = "TOKEN")
    private String token;

    @Column(name = "USERNAME", unique = true)
    private String username;
}
