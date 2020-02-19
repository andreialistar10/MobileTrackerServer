package com.andrei.mobiletracker.user.service;

import com.andrei.mobiletracker.user.dto.MyUserDetailRequestDto;
import com.andrei.mobiletracker.user.model.MyUserDetail;

public interface UserService {

    MyUserDetail signup(MyUserDetailRequestDto userDetailDto);
}
