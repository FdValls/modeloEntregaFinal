package com.testRestTemplate.testRestTemplate.dto;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.testRestTemplate.testRestTemplate.mapper.UserMapper;
import com.testRestTemplate.testRestTemplate.mapper.UserMapperBuilder;
import com.testRestTemplate.testRestTemplate.model.User;
import com.testRestTemplate.testRestTemplate.repository.UserRepository;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;
    @Schema(description = "Name of the user", example = "John Doe")
    private String name;
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;
    @Schema(description = "Phone number of the user", example = "1234567890")
    private String phone;
    @Schema(description = "Website of the user", example = "www.johndoe.com")
    private String website;

    @Schema(description = "List of motorcycles associated with the user")
    private Set<Long> domicilioIds;

    @Autowired
    private UserRepository userRepository;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, String phone, String website, Set<Long> domicilioIds,
            UserRepository userRepository) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.domicilioIds = domicilioIds;
        this.userRepository = userRepository;
    }

    public Set<DomicilioDTO> getDomiciliosByUserId(Long userId) {
        // Buscar el usuario por ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Convertir los domicilios del User a DomicilioDTOs
        return user.getDomicilios()
                .stream()
                .map(UserMapperBuilder::toDomicilioDTO)
                .collect(Collectors.toSet());
    }

    // Constructors, getters, and setters
}