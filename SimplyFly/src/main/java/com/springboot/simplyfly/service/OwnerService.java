package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.OwnerPageDto;
import com.springboot.simplyfly.dto.OwnerReqDto;
import com.springboot.simplyfly.dto.OwnerResDto;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.mapper.OwnerMapper;
import com.springboot.simplyfly.model.Owner;
import com.springboot.simplyfly.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public void addOwner(OwnerReqDto ownerReqDto) {
        Owner owner = OwnerMapper.mapToEntity(ownerReqDto);
        ownerRepository.save(owner);
    }

    public OwnerPageDto getAllOwners(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Owner> owner = ownerRepository.findAll(pageable);
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
               owner.getAirline_name(),
               owner.getEmail(),
               owner.getPhone()
        );
    }
}