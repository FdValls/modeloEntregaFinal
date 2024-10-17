package com.testRestTemplate.testRestTemplate.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DomicilioDTO {
    private Long id;
    private String direccion;
    private String descripcion;

    public DomicilioDTO() {
    }

    public DomicilioDTO(Long id, String direccion, String descripcion) {
        this.id = id;
        this.direccion = direccion;
        this.descripcion = descripcion;
    }

}