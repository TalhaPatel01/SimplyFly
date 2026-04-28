package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.AdminStatDto;
import com.springboot.simplyfly.repository.AppUserRepository;
import com.springboot.simplyfly.repository.BookingRepository;
import com.springboot.simplyfly.repository.RouteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RouteRepository routeRepository;

    @Test
    public void AdminStatsTest(){
        when(appUserRepository.findTotalUsers()).thenReturn(10L);
        when(appUserRepository.findTotalOwners()).thenReturn(5L);
        when(bookingRepository.findTotalBookings()).thenReturn(20L);
        when(routeRepository.findTotalRoutes()).thenReturn(8L);

        AdminStatDto result = adminService.getAdminStats();

        Assertions.assertEquals(new AdminStatDto(10L, 5L, 20L, 8L), result);
    }
}