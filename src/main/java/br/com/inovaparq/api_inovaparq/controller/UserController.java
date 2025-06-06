package br.com.inovaparq.api_inovaparq.controller;

import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<UserModel> criarUser(@RequestBody UserModel userModel) {
        UserModel createdUser = userService.newUser(userModel);
        return ResponseEntity.ok(createdUser);
    }

    // Atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> atualizarUser(@PathVariable Long id, @RequestBody UserModel userAtualizado) {
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
    public ResponseEntity<UserModel> mudarStatusUser(@PathVariable Long id, @RequestBody UserModel userAtualizado) {
        UserModel updatedUser = userService.updateStatusUser(id, userAtualizado);
        return ResponseEntity.ok(updatedUser);
    }

    // Deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
