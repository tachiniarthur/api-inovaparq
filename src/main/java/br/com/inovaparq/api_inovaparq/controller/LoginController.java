package br.com.inovaparq.api_inovaparq.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService UserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String efetuarLogin(@PathVariable String username, @PathVariable String password) {
        Optional<UserModel> userModel = UserService.findOnyByUserName(username);

        if (userModel.isPresent()) {
            UserModel user = userModel.get();
            if (passwordEncoder.matches(password, user.getSenha())) {
                return "Usuário logado com sucesso!";
            } else {
                return "Senha incorreta!";
            }
        } else {
            return "Usuário não encontrado!";
        }
    }
}
