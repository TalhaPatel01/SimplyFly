package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.OwnerResDto;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.model.Owner;
import com.springboot.simplyfly.repository.OwnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {
    @InjectMocks
    private OwnerService ownerService;
    @Mock
    private OwnerRepository ownerRepository;

    @Test
    public void getAllOwnersTest(){
        Owner owner1 = new Owner();
        owner1.setOwner_id(18L);
        owner1.setAirline_name("Air India");
        owner1.setEmail("air.india@gmail.com");
        owner1.setPhone("9876543210");
        Owner owner2 = new Owner();
        owner2.setOwner_id(19L);
        owner2.setAirline_name("Indigo");
        owner2.setEmail("indigo@gmail.com");
        owner2.setPhone("9123456780");
        List<Owner> list = List.of(owner1,owner2);

        Page<Owner> pageOwner = new PageImpl<>(list);
        int page=0;
        int size=2;

        Pageable pageable = PageRequest.of(page,size);

        when(ownerRepository.findAll(pageable)).thenReturn(pageOwner);
        Assertions.assertEquals(2,ownerService.getAllOwners(0,2).list().size());
    }

    @Test
    public void getOwnerByIdWhenExist(){
        Owner owner1 = new Owner();
        owner1.setOwner_id(18L);
        owner1.setAirline_name("Air India");
        owner1.setEmail("air.india@gmail.com");
        owner1.setPhone("9876543210");

        when(ownerRepository.findById(18L)).thenReturn(Optional.of(owner1));

        OwnerResDto dto = new OwnerResDto(
              owner1.getOwner_id(),
              owner1.getAirline_name(),
              owner1.getEmail(),
              owner1.getPhone()
        );
        Assertions.assertEquals(dto,ownerService.getByOwnerId(18L));

        OwnerResDto dto1 = new OwnerResDto(
                owner1.getOwner_id(),
                owner1.getAirline_name(),
                "1@gmail.com",
                owner1.getPhone()
        );
        Assertions.assertNotEquals(dto1,ownerService.getByOwnerId(18L));
    }

    @Test
    public void getOwnerByIdWhenNotExist(){
        when(ownerRepository.findById(18L)).thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFoundException.class,()->{
            ownerService.getByOwnerId(18L);
        });

        Assertions.assertEquals("Owner id invalid",e.getMessage());
    }
}