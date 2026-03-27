package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.UserPageDto;
import com.springboot.simplyfly.dto.UserReqDto;
import com.springboot.simplyfly.dto.UserResDto;
import com.springboot.simplyfly.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserReqDto userReqDto){
        userService.addUser(userReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-all")
    public UserPageDto getAllUsers(@RequestParam (value = "page", required = false, defaultValue = "0") int page,
                                   @RequestParam (value = "size", required = false, defaultValue = "0") int size){
        return userService.getAllUsers(page,size);
    }

    @GetMapping("/get/{id}")
    public UserResDto getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }
}