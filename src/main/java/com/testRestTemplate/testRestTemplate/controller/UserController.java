package com.testRestTemplate.testRestTemplate.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testRestTemplate.testRestTemplate.dto.DomicilioDTO;
import com.testRestTemplate.testRestTemplate.dto.MotorcycleDTO;
import com.testRestTemplate.testRestTemplate.dto.UserDTO;
import com.testRestTemplate.testRestTemplate.model.User;
import com.testRestTemplate.testRestTemplate.service.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
// import io.swagger.v3.oas.models.responses.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import com.testRestTemplate.testRestTemplate.utils.ApiResponseMsg;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "API Test Swagger")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    // @GetMapping("/{id}")
    // public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    // UserDTO user = userService.getUserById(id);
    // return ResponseEntity.ok(user);
    // }
    @PostMapping("/users/{userId}/motorcycles")
    public ResponseEntity<UserDTO> addMotorcycleToUser(@PathVariable Long userId,
            @RequestBody MotorcycleDTO motorcycleDTO) {
        UserDTO updatedUser = userService.addMotorcycleToUser(userId, motorcycleDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{userId}/domicilios/{domicilioId}")
    public ResponseEntity<UserDTO> addDomicilioToUser(@PathVariable Long userId, @PathVariable Long domicilioId) {
        UserDTO updatedUser = userService.addDomicilioToUser(userId, domicilioId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID", description = "Retorna el usuario por su ID")
    @ApiResponse(responseCode = "200", description = "Successful!", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "404", description = "User not found!", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    public ResponseEntity<?> encontrarUserPorId(@PathVariable Long id) {
        try {
            UserDTO user = userService.findById(id);
            return ResponseEntity.ok(user);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("User not found", e.getMessage()));
        }
    }

    @GetMapping(path = "/all")
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna todos los usuarios")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Users not found", content = @Content(schema = @Schema(implementation = ApiResponseMsg.class), examples = {
                    @ExampleObject(name = "UserNotFoundExample", value = "{\"message\": \"User not found\"}", description = "Usuarios no encontrados")
            }))
    })
    public ResponseEntity<?> getAllUsers() {
        try {
            User[] users = userService.findAll();
            return ResponseEntity.ok().body(new ApiResponseMsg("Usuarios encontrados ", users));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("Usuarios no encontrados ", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar un usuario de la DB", description = "Borra un usuario por su ID")
    @ApiResponse(responseCode = "204", description = "User deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "ID of the user to be deleted") @PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("User Delete", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Borrar un usuario de la API", description = "Borra un usuario por su ID")
    @ApiResponse(responseCode = "204", description = "User deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<?> deleteById(
            @Parameter(description = "ID of the user to be deleted") @PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseMsg("User Delete error: ", e.getMessage()));
        }
    }

    @PostMapping(value = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> postUser(@RequestBody String user) {
        // Guardar el usuario usando el servicio
        String userGuardado = userService.guardarUsuario(user);

        // Retornar la respuesta con el usuario guardado
        return ResponseEntity.created(null).body(userGuardado);
    }

    @GetMapping("/{userId}/domicilios")
    public ResponseEntity<List<DomicilioDTO>> getDomiciliosByUserId(@PathVariable Long userId) {
        // Llamar al servicio para obtener los domicilios del usuario
        List<DomicilioDTO> domicilios = userService.getDomiciliosByUserId(userId);
        return ResponseEntity.ok(domicilios);
    }
}