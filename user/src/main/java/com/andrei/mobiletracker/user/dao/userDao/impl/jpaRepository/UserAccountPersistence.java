package com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository.UserAccountRolePersistence;
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
public class UserAccountPersistence {

    @Id
    @Column(name = "USERNAME")
    @Length(max = 255)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToOne(targetEntity = UserAccountRolePersistence.class)
    @JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "USER_ROLE_ID")
    private UserAccountRolePersistence role;
}
