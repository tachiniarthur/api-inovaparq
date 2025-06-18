package br.com.inovaparq.api_inovaparq.service;

import br.com.inovaparq.api_inovaparq.controller.dto.UserCreateDTO;
import br.com.inovaparq.api_inovaparq.controller.dto.UserResponseDTO;
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


    public UserResponseDTO newUser(UserCreateDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirm_password())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        UserModel user = new UserModel();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setActive(true);
        user.setAdmin(false);

        UserModel saved = userRepository.save(user);

        return new UserResponseDTO(
            saved.getId(),
            saved.getName(),
            saved.getUsername(),
            saved.getEmail(),
            saved.getCpf(),
            saved.getPhoto(),
            saved.getPhone(),
            saved.getToken(),
            saved.getActive(),
            saved.getAdmin()
        );
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
                    userModel.setName(userAtualizado.getName());
                    userModel.setEmail(userAtualizado.getEmail());
                    userModel.setActive(userAtualizado.getActive());
                    return userRepository.save(userModel);
                })
                .orElseGet(() -> {
                    userAtualizado.setId(id);
                    return userRepository.save(userAtualizado);
                });
    }

    public UserModel updatePasswordUser(Long id, String password) {
        return userRepository.findById(id)
                .map(userModel -> {
                    userModel.setPassword(passwordEncoder.encode(password));
                    return userRepository.save(userModel);
                })
                .orElse(null);
    }

    public UserModel updateStatusUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setActive(!user.getActive());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

}
