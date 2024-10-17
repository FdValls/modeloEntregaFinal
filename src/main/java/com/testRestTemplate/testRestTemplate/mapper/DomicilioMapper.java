package com.testRestTemplate.testRestTemplate.mapper;

import org.springframework.stereotype.Component;

import com.testRestTemplate.testRestTemplate.dto.DomicilioDTO;
import com.testRestTemplate.testRestTemplate.model.Domicilio;

@Component
public class DomicilioMapper {
    public DomicilioDTO toDomicilioDTO(Domicilio domicilio) {
        if (domicilio == null) {
            return null;
        }

        return DomicilioDTO.builder()
                .id(domicilio.getId())
                .direccion(domicilio.getDireccion())
                .descripcion(domicilio.getDescripcion())
                .build();
    }

    public Domicilio toDomicilio(DomicilioDTO domicilioDTO) {
        if (domicilioDTO == null) {
            return null;
        }

        Domicilio domicilio = new Domicilio();
        domicilio.setId(domicilioDTO.getId());
        domicilio.setDireccion(domicilioDTO.getDireccion());
        domicilio.setDescripcion(domicilioDTO.getDescripcion());
        return domicilio;
    }
}
