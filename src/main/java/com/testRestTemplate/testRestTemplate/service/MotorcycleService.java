package com.testRestTemplate.testRestTemplate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testRestTemplate.testRestTemplate.dto.MotorcycleDTO;
import com.testRestTemplate.testRestTemplate.mapper.MotorcycleMapper;
import com.testRestTemplate.testRestTemplate.model.Motorcycle;
import com.testRestTemplate.testRestTemplate.repository.MotorcycleRepository;

@Service
public class MotorcycleService {
    @Autowired
    private final MotorcycleMapper motorcycleMapper;
    @Autowired
    private final MotorcycleRepository motorcycleRepository;

    public MotorcycleService(MotorcycleMapper motorcycleMapper, MotorcycleRepository motorcycleRepository) {
        this.motorcycleMapper = motorcycleMapper;
        this.motorcycleRepository = motorcycleRepository;
    }

    public MotorcycleDTO createMotorcycle(MotorcycleDTO motorcycleDTO) {
        Motorcycle motorcycle = motorcycleMapper.toMotorcycle(motorcycleDTO);
        Motorcycle savedMotorcycle = motorcycleRepository.save(motorcycle);
        return motorcycleMapper.toMotorcycleDTO(savedMotorcycle);
    }

    public MotorcycleDTO getMotorcycleById(Long id) {
        return motorcycleRepository.findById(id)
                .map(motorcycleMapper::toMotorcycleDTO)
                .orElse(null);
    }

    public List<MotorcycleDTO> getAllMotorcycles() {
        return motorcycleRepository.findAll().stream()
                .map(motorcycleMapper::toMotorcycleDTO)
                .collect(Collectors.toList());
    }

    public MotorcycleDTO updateMotorcycle(Long id, MotorcycleDTO motorcycleDTO) {
        return motorcycleRepository.findById(id)
                .map(motorcycle -> {
                    motorcycle.setModel(motorcycleDTO.getModel());
                    motorcycle.setBrand(motorcycleDTO.getBrand());
                    return motorcycleMapper.toMotorcycleDTO(motorcycleRepository.save(motorcycle));
                })
                .orElse(null);
    }

    public void deleteMotorcycle(Long id) {
        motorcycleRepository.deleteById(id);
    }
}
