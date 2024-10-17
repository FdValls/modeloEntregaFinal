package com.testRestTemplate.testRestTemplate.service;

import java.util.List;

import com.testRestTemplate.testRestTemplate.dto.MotorcycleDTO;
import com.testRestTemplate.testRestTemplate.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    UserDTO createUser(UserDTO userDTO);

    void updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

    UserDTO addMotorcycleToUser(Long userId, MotorcycleDTO motorcycleDTO);

    UserDTO addDomicilioToUser(Long userId, Long domicilioId);
}
