package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.OwnerReqDto;
import com.springboot.simplyfly.dto.OwnerResDto;
import com.springboot.simplyfly.model.Owner;

public class OwnerMapper {

    public static Owner mapToEntity(OwnerReqDto ownerReqDto){
        Owner owner = new Owner();
        owner.setEmail(ownerReqDto.email());
        owner.setAirline_name(ownerReqDto.airline_name());
        owner.setPhone(ownerReqDto.phone());
        return owner;
    }

    public static OwnerResDto mapToDto(Owner owner){
        return new OwnerResDto(
             owner.getOwner_id(),
             owner.getAppUser().getUsername(),
             owner.getAirline_name(),
             owner.getEmail(),
             owner.getPhone()
        );
    }
}
