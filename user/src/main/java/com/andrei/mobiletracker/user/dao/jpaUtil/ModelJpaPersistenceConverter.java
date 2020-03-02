package com.andrei.mobiletracker.user.dao.jpaUtil;

import com.andrei.mobiletracker.user.dao.notActivatedAccountDao.impl.jpaRepository.NotActivatedAccountPersistence;
import com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository.UserAccountPersistence;
import com.andrei.mobiletracker.user.dao.userDetailDao.impl.jpaRepository.UserAccountDetailPersistence;
import com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository.UserAccountRolePersistence;
import com.andrei.mobiletracker.user.model.UserAccount;
import com.andrei.mobiletracker.user.model.UserAccountDetail;
import com.andrei.mobiletracker.user.model.UserAccountRole;
import com.andrei.mobiletracker.user.model.NotActivatedAccount;
import org.springframework.stereotype.Component;

@Component
public class ModelJpaPersistenceConverter {

    public UserAccountRole convertMyUserRolePersistenceToMyUserRole(UserAccountRolePersistence userAccountRolePersistence){

        return userAccountRolePersistence == null ? null : UserAccountRole.builder()
                .id(userAccountRolePersistence.getId())
                .type(userAccountRolePersistence.getName())
                .build();
    }

    public UserAccountRolePersistence convertMyUserRoleToMyUserRolePersistence(UserAccountRole userAccountRole){

        return userAccountRole == null ? null : UserAccountRolePersistence.builder()
                .id(userAccountRole.getId())
                .name(userAccountRole.getType())
                .build();
    }

    public UserAccount convertMyUserPersistenceToMyUser(UserAccountPersistence userAccountPersistence) {

        return userAccountPersistence == null ? null : UserAccount.builder()
                .username(userAccountPersistence.getUsername())
                .password(userAccountPersistence.getPassword())
                .role(this.convertMyUserRolePersistenceToMyUserRole(userAccountPersistence.getRole()))
                .build();
    }

    public UserAccountPersistence convertMyUserToMyUserPersistence(UserAccount userAccount){

        return userAccount == null ? null : UserAccountPersistence.builder()
                .username(userAccount.getUsername())
                .password(userAccount.getPassword())
                .role(this.convertMyUserRoleToMyUserRolePersistence(userAccount.getRole()))
                .build();
    }

    public UserAccountDetail convertMyUserDetailPersistenceToMyUserDetail(UserAccountDetailPersistence userAccountDetailPersistence) {

        return userAccountDetailPersistence == null ? null : UserAccountDetail.builder()
                .user(this.convertMyUserPersistenceToMyUser(userAccountDetailPersistence.getUser()))
                .firstName(userAccountDetailPersistence.getFirstName())
                .lastName(userAccountDetailPersistence.getLastName())
                .email(userAccountDetailPersistence.getEmail())
                .build();
    }

    public UserAccountDetailPersistence convertMyUserDetailToMyUserDetailPersistence(UserAccountDetail userAccountDetail) {

        return userAccountDetail == null ? null : UserAccountDetailPersistence.builder()
                .username(userAccountDetail.getUser().getUsername())
                .user(this.convertMyUserToMyUserPersistence(userAccountDetail.getUser()))
                .firstName(userAccountDetail.getFirstName())
                .lastName(userAccountDetail.getLastName())
                .email(userAccountDetail.getEmail())
                .build();
    }

    public NotActivatedAccount convertNotActivatedAccountPersistenceToNotActivatedAccount(NotActivatedAccountPersistence notActivatedAccountPersistence){

        return notActivatedAccountPersistence == null ? null : NotActivatedAccount.builder()
                .token(notActivatedAccountPersistence.getToken())
                .username(notActivatedAccountPersistence.getUsername())
                .build();
    }

    public NotActivatedAccountPersistence convertNotActivatedAccountToNotActivatedAccountPersistence(NotActivatedAccount notActivatedAccount){

        return notActivatedAccount == null ? null : NotActivatedAccountPersistence.builder()
                .token(notActivatedAccount.getToken())
                .username(notActivatedAccount.getUsername())
                .build();
    }
}
