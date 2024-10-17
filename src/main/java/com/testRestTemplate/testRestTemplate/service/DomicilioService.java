package com.testRestTemplate.testRestTemplate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testRestTemplate.testRestTemplate.dto.DomicilioDTO;
import com.testRestTemplate.testRestTemplate.mapper.DomicilioMapper;
import com.testRestTemplate.testRestTemplate.model.Domicilio;
import com.testRestTemplate.testRestTemplate.repository.DomicilioRepository;

@Service
public class DomicilioService {
    @Autowired
    private final DomicilioMapper domicilioMapper;
    @Autowired
    private final DomicilioRepository domicilioRepository;

    public DomicilioService(DomicilioMapper domicilioMapper, DomicilioRepository domicilioRepository) {
        this.domicilioMapper = domicilioMapper;
        this.domicilioRepository = domicilioRepository;
    }

    public DomicilioDTO createDomicilio(DomicilioDTO domicilioDTO) {
        Domicilio domicilio = domicilioMapper.toDomicilio(domicilioDTO);
        Domicilio savedDomicilio = domicilioRepository.save(domicilio);
        return domicilioMapper.toDomicilioDTO(savedDomicilio);
    }

    public DomicilioDTO getDomicilioById(Long id) {
        return domicilioRepository.findById(id)
                .map(domicilioMapper::toDomicilioDTO)
                .orElse(null);
    }

    public List<DomicilioDTO> getAllDomicilios() {
        return domicilioRepository.findAll().stream()
                .map(domicilioMapper::toDomicilioDTO)
                .collect(Collectors.toList());
    }

    public DomicilioDTO updateDomicilio(Long id, DomicilioDTO domicilioDTO) {
        return domicilioRepository.findById(id)
                .map(domicilio -> {
                    domicilio.setDireccion(domicilioDTO.getDireccion());
                    domicilio.setDescripcion(domicilioDTO.getDescripcion());
                    return domicilioMapper.toDomicilioDTO(domicilioRepository.save(domicilio));
                })
                .orElse(null);
    }

    public void deleteDomicilio(Long id) {
        domicilioRepository.deleteById(id);
    }
}
