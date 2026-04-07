package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.UserSignUpDto;
import com.springboot.simplyfly.model.AppUser;

public class AppUserMapper {
    public static AppUser mapToDto(UserSignUpDto userSignUpDto){
        AppUser appUser = new AppUser();
        appUser.setUsername(userSignUpDto.username());
        appUser.setPassword(userSignUpDto.password());
        return appUser;
    }
}