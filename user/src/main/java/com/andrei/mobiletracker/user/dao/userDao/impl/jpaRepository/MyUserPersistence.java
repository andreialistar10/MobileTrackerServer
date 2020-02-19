package com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Entity
@Table(name = "MY_USERS")
public class MyUserPersistence {

    @Id
    @Column(name = "USERNAME")
    @Length(max = 255)
    private String username;

    @Column(name = "PASSWORD")
    private String password;
}
