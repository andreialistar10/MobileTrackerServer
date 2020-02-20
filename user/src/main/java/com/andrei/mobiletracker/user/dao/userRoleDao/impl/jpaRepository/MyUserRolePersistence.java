package com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.model.MyUserRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MY_USER_ROLES")
public class MyUserRolePersistence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ROLE_ID")
    private Integer id;

    @Column(name = "NAME", unique = true)
    @Enumerated(EnumType.STRING)
    private MyUserRoleType name;
}
