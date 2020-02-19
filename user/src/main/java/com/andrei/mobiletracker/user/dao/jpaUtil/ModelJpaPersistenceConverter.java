package com.andrei.mobiletracker.user.dao.jpaUtil;

import com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository.MyUserPersistence;
import com.andrei.mobiletracker.user.dao.userDetailDao.impl.jpaRepository.MyUserDetailPersistence;
import com.andrei.mobiletracker.user.model.MyUser;
import com.andrei.mobiletracker.user.model.MyUserDetail;
import org.springframework.stereotype.Component;

@Component
public class ModelJpaPersistenceConverter {

    public MyUser convertMyUserPersistenceToMyUser(MyUserPersistence myUserPersistence) {

        return myUserPersistence == null ? null : MyUser.builder()
                .username(myUserPersistence.getUsername())
                .password(myUserPersistence.getPassword())
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

    public MyUserPersistence convertMyUserToMyUserPersistence(MyUser myUser){

        return myUser == null ? null : MyUserPersistence.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
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
