package br.com.inovaparq.api_inovaparq.service;

import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UserModel> findOnyByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> findOnyById(Long id) {
        return userRepository.findById(id);
    }

    public UserModel newUser(UserModel user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return userRepository.save(user);
    }

    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserModel updateUser(Long id, UserModel userAtualizado) {
        return userRepository.findById(id)
                .map(userModel -> {
                    userModel.setUsername(userAtualizado.getUsername());
                    userModel.setNome(userAtualizado.getNome());
                    userModel.setEmail(userAtualizado.getEmail());
                    userModel.setAtivo(userAtualizado.getAtivo());
                    return userRepository.save(userModel);
                })
                .orElseGet(() -> {
                    userAtualizado.setId(id);
                    return userRepository.save(userAtualizado);
                });
    }

    public UserModel updatePasswordUser(Long id, String senha) {
        return userRepository.findById(id)
                .map(userModel -> {
                    userModel.setSenha(passwordEncoder.encode(senha));
                    return userRepository.save(userModel);
                })
                .orElse(null);
    }

    public UserModel updateStatusUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setAtivo(!user.getAtivo());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

}
