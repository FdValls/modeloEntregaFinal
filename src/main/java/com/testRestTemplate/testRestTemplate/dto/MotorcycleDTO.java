package com.testRestTemplate.testRestTemplate.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MotorcycleDTO {
    private Long id;
    private String model;
    private String brand;
    private Long userId;
}