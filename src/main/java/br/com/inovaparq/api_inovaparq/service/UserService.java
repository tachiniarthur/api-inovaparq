package br.com.inovaparq.api_inovaparq.service;

import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Service
@CrossOrigin(origins = "http://localhost:5173")
public class UserService {

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UserModel> findOnyByUserName(String username) {
        return UserRepository.findByUsername(username);
    }

    public List<UserModel> findAllUsers() {
        return UserRepository.findAll();
    }

    public Optional<UserModel> findOnyById(Long id) {
        return UserRepository.findById(id);
    }

    public UserModel newUser(UserModel User) {
        User.setSenha(passwordEncoder.encode(User.getSenha()));
        return UserRepository.save(User);
    }

    public void deleteUser(Long id) {
        UserRepository.deleteById(id);
    }

    public UserModel updateUser(Long id, UserModel UserAtualizado) {
        return UserRepository.findById(id)
                .map(UserModel -> {
                    UserModel.setUsername(UserAtualizado.getUsername());
                    UserModel.setNome(UserAtualizado.getNome());
                    UserModel.setEmail(UserAtualizado.getEmail());
                    UserModel.setAtivo(UserAtualizado.getAtivo());
                    return UserRepository.save(UserModel);
                })
                .orElseGet(() -> {
                    UserAtualizado.setId(id);
                    return UserRepository.save(UserAtualizado);
                });
    }

    public UserModel updatePasswordUser(Long id, String senha) {
        return UserRepository.findById(id)
                .map(UserModel -> {
                    UserModel.setSenha(senha);
                    return UserRepository.save(UserModel);
                })
                .orElse(null);
    }

    public UserModel updateStatusUser(Long id, UserModel UserAtualizado) {
        return UserRepository.findById(id)
                .map(UserModel -> {
                    UserModel.setAtivo(UserAtualizado.getAtivo());
                    return UserRepository.save(UserModel);
                })
                .orElseGet(() -> {
                    UserAtualizado.setId(id);
                    return UserRepository.save(UserAtualizado);
                });
    }
}