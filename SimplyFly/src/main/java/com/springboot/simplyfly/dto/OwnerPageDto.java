package com.springboot.simplyfly.dto;

import java.util.List;

public record OwnerPageDto(
    List<OwnerResDto> list,
    long totalRecords,
    int totalPages
) {
}