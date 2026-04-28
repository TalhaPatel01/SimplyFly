package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.UserResDto;
import com.springboot.simplyfly.enums.Role;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.model.AppUser;
import com.springboot.simplyfly.model.User;
import com.springboot.simplyfly.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void getAllUsersTest(){
        AppUser appUser = new AppUser();
        appUser.setRole(Role.USER);

        User user1 = new User();
        user1.setUser_id(18L);
        user1.setName("Harry");
        user1.setEmail("harry@gmail.com");
        user1.setPhone_no("9876543210");
        user1.setAppUser(appUser);
        User user2 = new User();
        user2.setUser_id(19L);
        user2.setName("Barren");
        user2.setEmail("barren@gmail.com");
        user2.setPhone_no("9123456780");
        user2.setAppUser(appUser);
        List<User> list = List.of(user1,user2);

        Page<User> pageUser = new PageImpl<>(list);
        int page = 0;
        int size = 2;

        Pageable pageable = PageRequest.of(page,size);

        when(userRepository.findAllUsers(pageable)).thenReturn(pageUser);
        Assertions.assertEquals(2,userService.getAllUsers(0,2).list().size());
    }

    @Test
    public void getUserByIdWhenExist(){
        AppUser appUser = new AppUser();
        appUser.setUsername("harry123");

        User user1 = new User();
        user1.setUser_id(18L);
        user1.setName("Harry");
        user1.setAppUser(appUser);
        user1.setEmail("harry@gmail.com");
        user1.setPhone_no("9876543210");

        when(userRepository.findById(18L)).thenReturn(Optional.of(user1));

        UserResDto dto = new UserResDto(
                user1.getUser_id(),
                user1.getAppUser().getUsername(),
                user1.getName(),
                user1.getEmail(),
                user1.getPhone_no()
        );
        Assertions.assertEquals(dto,userService.getUserById(18L));

        UserResDto dto1 = new UserResDto(
                user1.getUser_id(),
                user1.getAppUser().getUsername(),
                user1.getName(),
                "1@gmail.com",
                user1.getPhone_no()
        );
        Assertions.assertNotEquals(dto1,userService.getUserById(18L));
        Mockito.verify(userRepository,Mockito.times(2)).findById(18L);
    }

    @Test
    public void getUserByIdWhenNotExist(){
        when(userRepository.findById(18L)).thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFoundException.class,()->{
            userService.getUserById(18L);
        });

        Assertions.assertEquals("Invalid user id",e.getMessage());
    }
}