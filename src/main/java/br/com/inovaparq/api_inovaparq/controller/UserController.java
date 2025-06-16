package br.com.inovaparq.api_inovaparq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserModel>> listarTodos() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    // Buscar um usuário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> buscarPorId(@PathVariable Long id) {
        return userService.findOnyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo usuário
    @PostMapping
    public ResponseEntity<?> criarUser(@RequestBody UserCreateDTO userCreateDTO) {
        try {
            UserResponseDTO createdUser = userService.newUser(userCreateDTO);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> atualizarUser(@PathVariable Long id, @RequestBody UserModel userAtualizado) {
        System.out.println("Atualizando usuário com ID: " + id);
        UserModel updatedUser = userService.updateUser(id, userAtualizado);
        return ResponseEntity.ok(updatedUser);
    }

    // Atualizar senha usuário existente
    @PutMapping("/senha/{id}")
    public ResponseEntity<UserModel> mudarSenhaUser(@PathVariable Long id, @RequestBody String senha) {
        UserModel updatedUser = userService.updatePasswordUser(id, senha);
        return ResponseEntity.ok(updatedUser);
    }

    // Atualizar status usuário existente
    @PutMapping("/status/{id}")
    public ResponseEntity<UserModel> mudarStatusUser(@PathVariable Long id) {
        UserModel updatedUser = userService.updateStatusUser(id);
        return ResponseEntity.ok(updatedUser);
    }

    // Deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
