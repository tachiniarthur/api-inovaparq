package br.com.inovaparq.api_inovaparq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.repository.UserRepository;
import br.com.inovaparq.api_inovaparq.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService UserService;

    //Listar todos os usuários
    @GetMapping
    public List<UserModel> listarTodos() {
        return UserService.findAllUsers();
    }

    //Buscar um usuário pelo ID
    @GetMapping("/{id}")
    public UserModel buscarPorId(@PathVariable Long id) {
        Optional<UserModel> UserModel = UserService.findOnyById(id);
        return UserModel.orElse(null);
    }

    //Criar um novo usuário
    @PostMapping
    public UserModel criarUser(@RequestBody UserModel UserModel) {
        return UserService.newUser(UserModel);
    }

    //Atualizar um usuário existente
    @PutMapping("/{id}")
    public UserModel atualizarUser(@PathVariable Long id, @RequestBody UserModel UserAtualizado) {
        return UserService.updateUser(id, UserAtualizado);
    }

    // Atualizar senha usuário existente
    @PutMapping("/senha/{id}")
    public UserModel mudarSenhaUser(@PathVariable Long id, @RequestBody String senha) {
        return UserService.updatePasswordUser(id, senha);
    }

    // Atualizar status usuário existente
    @PutMapping("/status/{id}")
    public UserModel mudarStatusUser(@PathVariable Long id, @RequestBody UserModel UserAtualizado) {
        return UserService.updateStatusUser(id, UserAtualizado);
    }

    // Deletar um usuário
    @DeleteMapping("/{id}")
    public void deletarUser(@PathVariable Long id) {
        UserService.deleteUser(id);
    }
}