package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.UserPageDto;
import com.springboot.simplyfly.dto.UserReqDto;
import com.springboot.simplyfly.dto.UserResDto;
import com.springboot.simplyfly.dto.UserSignUpDto;
import com.springboot.simplyfly.enums.Role;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.mapper.AppUserMapper;
import com.springboot.simplyfly.mapper.UserMapper;
import com.springboot.simplyfly.model.AppUser;
import com.springboot.simplyfly.model.User;
import com.springboot.simplyfly.repository.AppUserRepository;
import com.springboot.simplyfly.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    public void addUser(UserReqDto userReqDto) {
        User user = UserMapper.mapToEntity(userReqDto);
        userRepository.save(user);
    }

    public UserPageDto getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<User> user = userRepository.findAllUsers(pageable);
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
             user.getAppUser().getUsername(),
             user.getName(),
                user.getEmail(),
                user.getPhone_no()
        );
    }

    public void userSignUp(@Valid UserSignUpDto userSignUpDto) {
        User user = UserMapper.mapFromSignUpToEntity(userSignUpDto);

        AppUser appUser = AppUserMapper.mapToDto(userSignUpDto);
        appUser.setRole(Role.USER);
        appUser.setPassword(passwordEncoder.encode(userSignUpDto.password()));

        appUserService.addAppUser(appUser);
        user.setAppUser(appUser);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}