package com.andrei.mobiletracker.user.dao.jpaUtil;

import com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository.MyUserPersistence;
import com.andrei.mobiletracker.user.dao.userDetailDao.impl.jpaRepository.MyUserDetailPersistence;
import com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository.MyUserRolePersistence;
import com.andrei.mobiletracker.user.model.MyUser;
import com.andrei.mobiletracker.user.model.MyUserDetail;
import com.andrei.mobiletracker.user.model.MyUserRole;
import org.springframework.stereotype.Component;

@Component
public class ModelJpaPersistenceConverter {

    public MyUserRole convertMyUserRolePersistenceToMyUserRole(MyUserRolePersistence myUserRolePersistence){

        return myUserRolePersistence == null ? null : MyUserRole.builder()
                .id(myUserRolePersistence.getId())
                .type(myUserRolePersistence.getName())
                .build();
    }

    public MyUserRolePersistence convertMyUserRoleToMyUserRolePersistence(MyUserRole myUserRole){

        return myUserRole == null ? null : MyUserRolePersistence.builder()
                .id(myUserRole.getId())
                .name(myUserRole.getType())
                .build();
    }

    public MyUser convertMyUserPersistenceToMyUser(MyUserPersistence myUserPersistence) {

        return myUserPersistence == null ? null : MyUser.builder()
                .username(myUserPersistence.getUsername())
                .password(myUserPersistence.getPassword())
                .role(this.convertMyUserRolePersistenceToMyUserRole(myUserPersistence.getRole()))
                .build();
    }

    public MyUserPersistence convertMyUserToMyUserPersistence(MyUser myUser){

        return myUser == null ? null : MyUserPersistence.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .role(this.convertMyUserRoleToMyUserRolePersistence(myUser.getRole()))
                .build();
    }

    public MyUserDetail convertMyUserDetailPersistenceToMyUserDetail(MyUserDetailPersistence myUserDetailPersistence) {

        return myUserDetailPersistence == null ? null : MyUserDetail.builder()
                .user(this.convertMyUserPersistenceToMyUser(myUserDetailPersistence.getUser()))
                .firstName(myUserDetailPersistence.getFirstName())
                .lastName(myUserDetailPersistence.getLastName())
                .email(myUserDetailPersistence.getEmail())
                .build();
    }

    public MyUserDetailPersistence convertMyUserDetailToMyUserDetailPersistence(MyUserDetail myUserDetail) {

        return myUserDetail == null ? null : MyUserDetailPersistence.builder()
                .username(myUserDetail.getUser().getUsername())
                .user(this.convertMyUserToMyUserPersistence(myUserDetail.getUser()))
                .firstName(myUserDetail.getFirstName())
                .lastName(myUserDetail.getLastName())
                .email(myUserDetail.getEmail())
                .build();
    }
}
