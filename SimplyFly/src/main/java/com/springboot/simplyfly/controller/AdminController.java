package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.AdminReqDto;
import com.springboot.simplyfly.dto.AdminStatDto;
import com.springboot.simplyfly.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody AdminReqDto adminReqDto){
        adminService.addAdmin(adminReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //stats API
    @GetMapping("/stats")
    public AdminStatDto getAdminStats(Principal principal){
        return adminService.getAdminStats();
    }
}