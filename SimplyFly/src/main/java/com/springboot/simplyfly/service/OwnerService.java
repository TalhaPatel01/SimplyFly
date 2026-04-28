package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.*;
import com.springboot.simplyfly.enums.Role;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.mapper.OwnerMapper;
import com.springboot.simplyfly.model.AppUser;
import com.springboot.simplyfly.model.Owner;
import com.springboot.simplyfly.repository.BookingRepository;
import com.springboot.simplyfly.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final BookingRepository bookingRepository;

    public void addOwner(OwnerReqDto ownerReqDto) {
        Owner owner = OwnerMapper.mapToEntity(ownerReqDto);
        ownerRepository.save(owner);
    }

    public OwnerPageDto getAllOwners(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Owner> owner = ownerRepository.findAllOwner(pageable);
        long totalRecords = owner.getTotalElements();
        int totalPages = owner.getTotalPages();

        List<OwnerResDto> list = owner
                .stream()
                .map(OwnerMapper::mapToDto)
                .toList();

        return new OwnerPageDto(
                list,
                totalRecords,
                totalPages
        );
    }

    public OwnerResDto getByOwnerId(long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Owner id invalid"));

        return new OwnerResDto(
               owner.getOwner_id(),
               owner.getAppUser().getUsername(),
               owner.getAirline_name(),
               owner.getEmail(),
               owner.getPhone()
        );
    }

    public void addOwnerWithCredentials(OwnerSignUpDto ownerSignUpDto) {
        Owner owner = new Owner();
        owner.setAirline_name(ownerSignUpDto.airline_name());
        owner.setEmail(ownerSignUpDto.email());
        owner.setPhone(ownerSignUpDto.phone());

        AppUser appUser = new AppUser();
        appUser.setUsername(ownerSignUpDto.username());
        appUser.setPassword(passwordEncoder.encode(ownerSignUpDto.password()));
        appUser.setRole(Role.OWNER);
        appUserService.addAppUser(appUser);

        owner.setAppUser(appUser);
        ownerRepository.save(owner);
    }

    public OwnerStatResDto getOwnerStats(String username) {
        long totalFlights = ownerRepository.getTotalFlightsByOwner(username);
        long totalBookings = ownerRepository.getTotalBookingsByOwner(username);
        long totalRevenue = ownerRepository.getOwnerTotalRevenue(username);

        return new OwnerStatResDto(
                totalFlights,
                totalBookings,
                totalRevenue
        );
    }

    public List<TopRouteDto> getTopRoutes(String username) {
        return bookingRepository.getTopRoutes(
                username,
                PageRequest.of(0, 5)
        );
    }
}