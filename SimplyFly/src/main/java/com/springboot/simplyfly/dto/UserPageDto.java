package com.springboot.simplyfly.dto;

import java.util.List;

public record UserPageDto(
        List<UserResDto> list,
        long totalRecords,
        int totalPages
) {
}
