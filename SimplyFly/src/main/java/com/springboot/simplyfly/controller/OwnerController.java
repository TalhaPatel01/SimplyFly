package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.*;
import com.springboot.simplyfly.service.OwnerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/owner")
@CrossOrigin(origins = "http://localhost:5173/")
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

    @PostMapping("/sign-up")
    public void addOwnerWithCredentials(@Valid @RequestBody OwnerSignUpDto ownerSignUpDto){
        ownerService.addOwnerWithCredentials(ownerSignUpDto);
    }

    //stats API
    @GetMapping("/stats")
    public OwnerStatResDto getOwnerStats(Principal principal){
        String username = principal.getName();
        return ownerService.getOwnerStats(username);
    }

    //top routes of owner
    @GetMapping("/top-routes")
    public List<TopRouteDto> getTopRoutes(Principal principal) {
        return ownerService.getTopRoutes(principal.getName());
    }
}