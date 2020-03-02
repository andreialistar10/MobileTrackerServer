package com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.model.UserAccountRoleType;
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
@Table(name = "USER_ACCOUNT_ROLES")
public class UserAccountRolePersistence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ROLE_ID")
    private Integer id;

    @Column(name = "NAME", unique = true)
    @Enumerated(EnumType.STRING)
    private UserAccountRoleType name;
}
