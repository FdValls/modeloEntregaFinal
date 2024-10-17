package com.testRestTemplate.testRestTemplate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testRestTemplate.testRestTemplate.dto.DomicilioDTO;
import com.testRestTemplate.testRestTemplate.service.DomicilioService;

@RestController
@RequestMapping("/api/domicilios")
public class DomicilioController {

    @Autowired
    private final DomicilioService domicilioService;

    public DomicilioController(DomicilioService domicilioService) {
        this.domicilioService = domicilioService;
    }

    @PostMapping
    public ResponseEntity<DomicilioDTO> createDomicilio(@RequestBody DomicilioDTO domicilioDTO) {
        DomicilioDTO createdDomicilio = domicilioService.createDomicilio(domicilioDTO);
        return ResponseEntity.ok(createdDomicilio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomicilioDTO> getDomicilioById(@PathVariable Long id) {
        DomicilioDTO domicilio = domicilioService.getDomicilioById(id);
        return domicilio != null ? ResponseEntity.ok(domicilio) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<DomicilioDTO>> getAllDomicilios() {
        List<DomicilioDTO> domicilios = domicilioService.getAllDomicilios();
        return ResponseEntity.ok(domicilios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DomicilioDTO> updateDomicilio(@PathVariable Long id, @RequestBody DomicilioDTO domicilioDTO) {
        DomicilioDTO updatedDomicilio = domicilioService.updateDomicilio(id, domicilioDTO);
        return updatedDomicilio != null ? ResponseEntity.ok(updatedDomicilio) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomicilio(@PathVariable Long id) {
        domicilioService.deleteDomicilio(id);
        return ResponseEntity.noContent().build();
    }
}
