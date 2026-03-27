package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.UserReqDto;
import com.springboot.simplyfly.dto.UserResDto;
import com.springboot.simplyfly.model.User;

public class UserMapper {
    public static User mapToEntity(UserReqDto userReqDto){
        User user = new User();
        user.setName(userReqDto.name());
        user.setEmail(userReqDto.email());
        user.setPhone_no(userReqDto.phone_no());
        user.setPassword(userReqDto.password());
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
}