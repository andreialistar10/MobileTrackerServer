package com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository.MyUserRolePersistence;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

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

    @ManyToOne(targetEntity = MyUserRolePersistence.class)
    @JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "USER_ROLE_ID")
    private MyUserRolePersistence role;
}
