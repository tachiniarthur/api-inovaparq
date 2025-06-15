package br.com.inovaparq.api_inovaparq.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inovaparq.api_inovaparq.controller.dto.UserResponseDTO;
import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.requests.LoginRequest;
import br.com.inovaparq.api_inovaparq.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService UserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody LoginRequest loginRequest) {
        Optional<UserModel> userModel = UserService.findOnyByUserName(loginRequest.getUsername());

        if (userModel.isPresent()) {
            UserModel user = userModel.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getSenha())) {
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                UserService.saveUser(user);

                UserResponseDTO responseDTO = new UserResponseDTO(
                    user.getId(),
                    user.getNome(),
                    user.getUsername(),
                    user.getEmail(),
                    String.valueOf(user.getCpf()),
                    user.getFoto(),
                    user.getTelefone(),
                    user.getToken(),
                    user.getAtivo(),
                    user.getAdmin()
                );

                return ResponseEntity.ok(responseDTO);
            }
        }
        return ResponseEntity.status(401).body("Usuário ou senha incorretos!");
    }
}
