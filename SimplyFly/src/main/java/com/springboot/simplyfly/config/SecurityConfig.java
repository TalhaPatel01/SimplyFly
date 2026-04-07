package com.springboot.simplyfly.config;

import com.springboot.simplyfly.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final AppUserService appUserService;
    private final JwtFilter jwtFilter;

//    @Bean
//    public UserDetailsService users() {
//        UserDetails customer1 = User.builder()
//                .username("harry")
//                .password("{noop}potter")
//                .authorities("USER")
//                .build();
//        UserDetails customer2 = User.builder()
//                .username("ronald")
//                .password("{noop}weasley")
//                .authorities("USER")
//                .build();
//        UserDetails executive1 = User.builder()
//                .username("hermione")
//                .password("{noop}granger")
//                .authorities("OWNER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{noop}admin")
//                .authorities("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(customer1,customer2,executive1, admin);
//    }

    @Bean
    public SecurityFilterChain bankingSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS,"/**")
                                .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/airport/add")
                                .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/user/sign-up")
                                .permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/route/flight/search")
                                .authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/booking/create-booking")
                                .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/booking/get-by-user")
                                .authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/payment/{bookingId}")
                                .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/user/get-all")
                                .permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/user/get/{id}")
                                .authenticated()
                        .anyRequest().permitAll()
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());  //Spring understand that i am using this technique
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}