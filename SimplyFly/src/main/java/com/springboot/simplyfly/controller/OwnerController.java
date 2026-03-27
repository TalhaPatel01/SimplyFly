package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.OwnerPageDto;
import com.springboot.simplyfly.dto.OwnerReqDto;
import com.springboot.simplyfly.dto.OwnerResDto;
import com.springboot.simplyfly.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/owner")
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping("/add")
    public ResponseEntity<?> addOwner(@RequestBody OwnerReqDto ownerReqDto){
        ownerService.addOwner(ownerReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-all")
    public OwnerPageDto getAllOwners(@RequestParam (value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam (value = "size", required = false, defaultValue = "0") int size){
        return ownerService.getAllOwners(page,size);
    }

    @GetMapping("/get/{id}")
    public OwnerResDto getByOwnerId(@PathVariable long id){
        return ownerService.getByOwnerId(id);
    }
}