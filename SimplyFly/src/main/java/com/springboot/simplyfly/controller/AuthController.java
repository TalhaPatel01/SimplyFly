package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.AppUserDto;
import com.springboot.simplyfly.model.AppUser;
import com.springboot.simplyfly.service.AppUserService;
import com.springboot.simplyfly.utility.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin (origins = "http://localhost:5173/")
public class AuthController {
    private final JwtUtility jwtUtility;
    private final AppUserService appUserService;

    @GetMapping("/login")
    public ResponseEntity<?> login(Principal principal){
        String loggedInUser = principal.getName();
        Map<String,String> map = new HashMap<>();
        map.put("token", jwtUtility.generateToken(loggedInUser));
        return ResponseEntity.status(HttpStatus.OK)
                .body(map);
    }

    @GetMapping("/appUser-details")
    public ResponseEntity<AppUserDto> getAppUserDetails(Principal principal){
        String username = principal.getName();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(username);
        return ResponseEntity.ok(new AppUserDto(
                appUser.getUsername(),
                appUser.getRole().toString()
        ));
    }
}