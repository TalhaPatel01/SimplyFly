package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.UserReqDto;
import com.springboot.simplyfly.dto.UserResDto;
import com.springboot.simplyfly.dto.UserSignUpDto;
import com.springboot.simplyfly.model.User;

public class UserMapper {
    public static User mapToEntity(UserReqDto userReqDto){
        User user = new User();
        user.setName(userReqDto.name());
        user.setEmail(userReqDto.email());
        user.setPhone_no(userReqDto.phone_no());
        return user;
    }

    public static UserResDto mapToDto(User user){
        return new UserResDto(
              user.getUser_id(),
              user.getName(),
                user.getEmail(),
                user.getPhone_no()
        );
    }

    public static User mapFromSignUpToEntity(UserSignUpDto userSignUpDto){
        User user = new User();
        user.setName(userSignUpDto.name());
        user.setEmail(userSignUpDto.email());
        user.setPhone_no(userSignUpDto.phone_no());
        return user;
    }
}