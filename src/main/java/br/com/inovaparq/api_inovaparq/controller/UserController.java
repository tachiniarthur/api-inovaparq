package br.com.inovaparq.api_inovaparq.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inovaparq.api_inovaparq.controller.dto.UserCreateDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.UserResponseDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.DefaultResponseDTO;
import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<DefaultResponseDTO<List<UserResponseDTO>>> listarTodos() {
        List<UserModel> users = userService.findAllUsers();
        List<UserResponseDTO> userDTOs = users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCpf(),
                        user.getPhoto(),
                        user.getPhone(),
                        user.getToken(),
                        user.getActive(),
                        user.getAdmin()))
                .collect(Collectors.toList());
        
        DefaultResponseDTO<List<UserResponseDTO>> response = new DefaultResponseDTO<>(
                "Lista de usuários recuperada com sucesso!",
                userDTOs);
        return ResponseEntity.ok(response);
    }

    // Buscar um usuário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO<UserResponseDTO>> buscarPorId(@PathVariable Long id) {
        return userService.findOnyById(id)
                .map(user -> {
                    UserResponseDTO userDTO = new UserResponseDTO(
                            user.getId(),
                            user.getName(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getCpf(),
                            user.getPhoto(),
                            user.getPhone(),
                            user.getToken(),
                            user.getActive(),
                            user.getAdmin());
                    DefaultResponseDTO<UserResponseDTO> response = new DefaultResponseDTO<>(
                            "Usuário encontrado com sucesso!",
                            userDTO);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo usuário
    @PostMapping
    public ResponseEntity<DefaultResponseDTO<?>> criarUser(@RequestBody UserCreateDTO userCreateDTO) {
        try {
            UserResponseDTO createdUser = userService.newUser(userCreateDTO);
            DefaultResponseDTO<UserResponseDTO> response = new DefaultResponseDTO<>(
                    "Usuário criado com sucesso!",
                    createdUser);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            DefaultResponseDTO<String> errorResponse = new DefaultResponseDTO<>(
                    e.getMessage(),
                    null);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // Atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO<?>> atualizarUser(
            @PathVariable Long id, 
            @RequestBody UserModel userAtualizado) {
        try {
            UserModel updatedUser = userService.updateUser(id, userAtualizado);
            UserResponseDTO userDTO = new UserResponseDTO(
                    updatedUser.getId(),
                    updatedUser.getName(),
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getCpf(),
                    updatedUser.getPhoto(),
                    updatedUser.getPhone(),
                    updatedUser.getToken(),
                    updatedUser.getActive(),
                    updatedUser.getAdmin());
            
            DefaultResponseDTO<UserResponseDTO> response = new DefaultResponseDTO<>(
                    "Usuário atualizado com sucesso!",
                    userDTO);
            return ResponseEntity.ok(response);
        } catch (ObjectOptimisticLockingFailureException e) {
            DefaultResponseDTO<String> errorResponse = new DefaultResponseDTO<>(
                    "O registro foi alterado por outro usuário ou está desatualizado. Recarregue a página e tente novamente.",
                    null);
            return ResponseEntity.status(422).body(errorResponse);
        } catch (Exception e) {
            DefaultResponseDTO<String> errorResponse = new DefaultResponseDTO<>(
                    e.getMessage(),
                    null);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    // Atualizar senha usuário existente
    @PutMapping("/senha/{id}")
    public ResponseEntity<DefaultResponseDTO<String>> mudarSenhaUser(
            @PathVariable Long id, 
            @RequestBody String senha) {
        userService.updatePasswordUser(id, senha);
        DefaultResponseDTO<String> response = new DefaultResponseDTO<>(
                "Senha atualizada com sucesso!",
                null);
        return ResponseEntity.ok(response);
    }

    // Atualizar status usuário existente
    @PutMapping("/status/{id}")
    public ResponseEntity<DefaultResponseDTO<UserResponseDTO>> mudarStatusUser(@PathVariable Long id) {
        UserModel updatedUser = userService.updateStatusUser(id);
        UserResponseDTO userDTO = new UserResponseDTO(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getCpf(),
                updatedUser.getPhoto(),
                updatedUser.getPhone(),
                updatedUser.getToken(),
                updatedUser.getActive(),
                updatedUser.getAdmin());
        
        DefaultResponseDTO<UserResponseDTO> response = new DefaultResponseDTO<>(
                updatedUser.getActive() ? "Usuário ativado com sucesso!" : "Usuário desativado com sucesso!",
                userDTO);
        return ResponseEntity.ok(response);
    }

    // Deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponseDTO<String>> deletarUser(@PathVariable Long id) {
        userService.deleteUser(id);
        DefaultResponseDTO<String> response = new DefaultResponseDTO<>(
                "Usuário deletado com sucesso!",
                null);
        return ResponseEntity.ok(response);
    }
}