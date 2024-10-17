package com.testRestTemplate.testRestTemplate.mapper;

import org.springframework.stereotype.Component;

import com.testRestTemplate.testRestTemplate.dto.MotorcycleDTO;
import com.testRestTemplate.testRestTemplate.model.Motorcycle;

@Component
public class MotorcycleMapper {
    public MotorcycleDTO toMotorcycleDTO(Motorcycle motorcycle) {
        if (motorcycle == null) {
            return null;
        }

        return MotorcycleDTO.builder()
                .id(motorcycle.getId())
                .model(motorcycle.getModel())
                .brand(motorcycle.getBrand())
                .userId(motorcycle.getUser() != null ? motorcycle.getUser().getId() : null)
                .build();
    }

    public Motorcycle toMotorcycle(MotorcycleDTO motorcycleDTO) {
        if (motorcycleDTO == null) {
            return null;
        }

        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId(motorcycleDTO.getId());
        motorcycle.setModel(motorcycleDTO.getModel());
        motorcycle.setBrand(motorcycleDTO.getBrand());
        // Note: We're not setting the User here, as it should be handled separately
        return motorcycle;
    }
}
