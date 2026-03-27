package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.UserPageDto;
import com.springboot.simplyfly.dto.UserReqDto;
import com.springboot.simplyfly.dto.UserResDto;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.mapper.UserMapper;
import com.springboot.simplyfly.model.User;
import com.springboot.simplyfly.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void addUser(UserReqDto userReqDto) {
        User user = UserMapper.mapToEntity(userReqDto);
        userRepository.save(user);
    }

    public UserPageDto getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<User> user = userRepository.findAll(pageable);
        long totalRecords = user.getTotalElements();
        int totalPages = user.getTotalPages();

        List<UserResDto> list = user
                .stream()
                .map(UserMapper::mapToDto)
                .toList();

        return new UserPageDto(
             list,
             totalRecords,
             totalPages
        );
    }

    public UserResDto getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Invalid user id"));

        return new UserResDto(
             user.getUser_id(),
             user.getName(),
                user.getEmail(),
                user.getPhone_no()
        );
    }
}