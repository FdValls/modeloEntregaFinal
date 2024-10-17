package com.testRestTemplate.testRestTemplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testRestTemplate.testRestTemplate.model.Domicilio;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {

}
