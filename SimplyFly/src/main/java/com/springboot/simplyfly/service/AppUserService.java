package com.springboot.simplyfly.service;

import com.springboot.simplyfly.model.AppUser;
import com.springboot.simplyfly.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public void addAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.getAppUserByUsername(username);
        return appUser;
    }
}