package com.testRestTemplate.testRestTemplate.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.testRestTemplate.testRestTemplate.dto.DomicilioDTO;
import com.testRestTemplate.testRestTemplate.dto.MotorcycleDTO;
import com.testRestTemplate.testRestTemplate.dto.UserDTO;
import com.testRestTemplate.testRestTemplate.mapper.MotorcycleMapper;
import com.testRestTemplate.testRestTemplate.mapper.UserMapper;
import com.testRestTemplate.testRestTemplate.mapper.UserMapperBuilder;
import com.testRestTemplate.testRestTemplate.model.Domicilio;
import com.testRestTemplate.testRestTemplate.model.Motorcycle;
import com.testRestTemplate.testRestTemplate.model.User;
import com.testRestTemplate.testRestTemplate.repository.DomicilioRepository;
import com.testRestTemplate.testRestTemplate.repository.MotorcycleRepository;
import com.testRestTemplate.testRestTemplate.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MotorcycleRepository motorcycleRepository;
    @Autowired
    private final MotorcycleMapper motorcycleMapper;
    @Autowired
    private DomicilioRepository domicilioRepository;
    @Autowired
    private UserMapperBuilder userMapperBuilder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RestTemplate restTemplate,
            MotorcycleRepository motorcycleRepository, MotorcycleMapper motorcycleMapper,
            DomicilioRepository domicilioRepository, UserMapperBuilder userMapperBuilder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.restTemplate = restTemplate;
        this.motorcycleRepository = motorcycleRepository;
        this.motorcycleMapper = motorcycleMapper;
        this.domicilioRepository = domicilioRepository;
        this.userMapperBuilder = userMapperBuilder;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<DomicilioDTO> getDomiciliosByUserId(Long userId) {
        // Paso 1: Obtener los ids de los domicilios del usuario
        List<Long> domicilioIds = userRepository.findDomicilioIdsByUserId(userId);

        // Paso 2: Buscar todos los domicilios por sus ids
        List<Domicilio> domicilios = domicilioRepository.findAllById(domicilioIds);

        // Paso 3: Mapear los Domicilio a DomicilioDTO
        return domicilios.stream()
                .map(UserMapperBuilder::toDomicilioDTO) // Convertir cada Domicilio en DomicilioDTO
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            throw new RuntimeException("El usuario ya existe en la base de datos");
        }

        UserDTO userDTO = restTemplate.getForObject(BASE_URL + "/{id}", UserDTO.class, id);

        User userWithSameName = userRepository.findByName(userDTO.getName());
        if (userWithSameName != null) {
            throw new RuntimeException("Ya existe un usuario con el mismo nombre en la base de datos");
        }

        User newUser = userMapperBuilder.toEntity(userDTO);
        User savedUser = userRepository.save(newUser);
        return userMapperBuilder.toDTO(savedUser);
    }

    public User[] findAll() {
        // for (User u : restTemplate.getForObject(BASE_URL, User[].class)) {
        // System.out.println(u.getName());
        // }
        return restTemplate.getForObject(BASE_URL, User[].class);
    }
    // public List<User> findAll() {
    // return restTemplate.getForObject(BASE_URL, List.class);
    // }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // User user = userMapper.toEntity(userDTO);
        User user = userMapperBuilder.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public void updateUser(Long id, UserDTO userDTO) {
        User user = userMapperBuilder.toEntity(userDTO);
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteById(Long id) {
        restTemplate.delete(BASE_URL + "/{id}", id);
    }

    public String guardarUsuario(String user) {
        // RestTemplate restTemplate = new RestTemplate();
        final String url = "https://jsonplaceholder.typicode.com/users";

        // Realizar la solicitud POST a la API externa
        return restTemplate.postForObject(url, user, String.class);
    }

    @Transactional
    public UserDTO addMotorcycleToUser(Long userId, MotorcycleDTO motorcycleDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Motorcycle motorcycle = motorcycleMapper.toMotorcycle(motorcycleDTO);
        motorcycle.setUser(user);
        user.getMotorcycles().add(motorcycle);

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO addDomicilioToUser(Long userId, Long domicilioId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Domicilio domicilio = domicilioRepository.findById(domicilioId)
                .orElseThrow(() -> new RuntimeException("Domicilio not found"));

        // Insertar en la tabla intermedia directamente
        userRepository.insertUserDomicilio(user.getId(), domicilio.getId());

        // Devolver el DTO del usuario actualizado
        return this.userMapperBuilder.toDTO(user);
    }

    public UserDTO getUserDTO(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return this.userMapperBuilder.toDTO(user);
    }

    // public Set<DomicilioDTO> getDomiciliosByUserId(Long userId) {
    // // Buscar el usuario por ID
    // User user = userRepository.findById(userId)
    // .orElseThrow(() -> new RuntimeException("User not found"));

    // // Convertir los domicilios del User a DomicilioDTOs
    // return user.getDomicilios()
    // .stream()
    // .map(UserMapperBuilder::toDomicilioDTO)
    // .collect(Collectors.toSet());
    // }

}
