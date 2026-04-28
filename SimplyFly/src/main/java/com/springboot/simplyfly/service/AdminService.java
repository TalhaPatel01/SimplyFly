package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.AdminReqDto;
import com.springboot.simplyfly.dto.AdminStatDto;
import com.springboot.simplyfly.enums.Role;
import com.springboot.simplyfly.model.AppUser;
import com.springboot.simplyfly.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookingRepository bookingRepository;
    private final RouteRepository routeRepository;

    public void addAdmin(AdminReqDto adminReqDto) {
        AppUser appUser = new AppUser();
        appUser.setUsername(adminReqDto.username());
        appUser.setPassword(passwordEncoder.encode(adminReqDto.password()));
        appUser.setRole(Role.ADMIN);
        appUserRepository.save(appUser);
    }

    public AdminStatDto getAdminStats() {
        long totalUsers = appUserRepository.findTotalUsers();
        long totalOwners = appUserRepository.findTotalOwners();
        long totalBookings = bookingRepository.findTotalBookings();
        long totalRoutes = routeRepository.findTotalRoutes();

        return new AdminStatDto(
                totalUsers,
                totalOwners,
                totalBookings,
                totalRoutes
        );
    }
}