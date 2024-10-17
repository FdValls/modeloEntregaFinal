package com.testRestTemplate.testRestTemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testRestTemplate.testRestTemplate.dto.MotorcycleDTO;
import com.testRestTemplate.testRestTemplate.service.MotorcycleService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motorcycles")
public class MotorcycleController {

    @Autowired
    private final MotorcycleService motorcycleService;

    public MotorcycleController(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @PostMapping
    public ResponseEntity<MotorcycleDTO> createMotorcycle(@RequestBody MotorcycleDTO motorcycleDTO) {
        MotorcycleDTO createdMotorcycle = motorcycleService.createMotorcycle(motorcycleDTO);
        return ResponseEntity.ok(createdMotorcycle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotorcycleDTO> getMotorcycleById(@PathVariable Long id) {
        MotorcycleDTO motorcycle = motorcycleService.getMotorcycleById(id);
        return motorcycle != null ? ResponseEntity.ok(motorcycle) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<MotorcycleDTO>> getAllMotorcycles() {
        List<MotorcycleDTO> motorcycles = motorcycleService.getAllMotorcycles();
        return ResponseEntity.ok(motorcycles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotorcycleDTO> updateMotorcycle(@PathVariable Long id,
            @RequestBody MotorcycleDTO motorcycleDTO) {
        MotorcycleDTO updatedMotorcycle = motorcycleService.updateMotorcycle(id, motorcycleDTO);
        return updatedMotorcycle != null ? ResponseEntity.ok(updatedMotorcycle) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotorcycle(@PathVariable Long id) {
        motorcycleService.deleteMotorcycle(id);
        return ResponseEntity.noContent().build();
    }
}
