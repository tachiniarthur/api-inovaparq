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
import java.util.UUID;

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

        // Verificações de unicidade
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username já está em uso.");
        }
        if (dto.getEmail() != null && userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso.");
        }
        if (dto.getCpf() != null && userRepository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("CPF já está em uso.");
        }

        UserModel user = new UserModel();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setActive(true);
        user.setAdmin(false);

        String token = UUID.randomUUID().toString();
        user.setToken(token);

        // Adicione estes campos se existirem no DTO
        user.setEmail(dto.getEmail());
        user.setCpf(dto.getCpf());

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
                    if (userAtualizado.getUsername() != null)
                        userModel.setUsername(userAtualizado.getUsername());
                    if (userAtualizado.getName() != null)
                        userModel.setName(userAtualizado.getName());
                    if (userAtualizado.getEmail() != null)
                        userModel.setEmail(userAtualizado.getEmail());
                    if (userAtualizado.getCpf() != null)
                        userModel.setCpf(userAtualizado.getCpf());
                    if (userAtualizado.getPhoto() != null)
                        userModel.setPhoto(userAtualizado.getPhoto());
                    if (userAtualizado.getPhone() != null && !userAtualizado.getPhone().isEmpty())
                        userModel.setPhone(userAtualizado.getPhone());
                    if (userAtualizado.getToken() != null)
                        userModel.setToken(userAtualizado.getToken());
                    if (userAtualizado.getActive() != null)
                        userModel.setActive(userAtualizado.getActive());
                    if (userAtualizado.getAdmin() != null)
                        userModel.setAdmin(userAtualizado.getAdmin());
                    if (userAtualizado.getRole() != null)
                        userModel.setRole(userAtualizado.getRole());
                    if (userAtualizado.getbirthdate() != null)
                        userModel.setbirthdate(userAtualizado.getbirthdate());
                    if (userAtualizado.getCompany() != null)
                        userModel.setCompany(userAtualizado.getCompany());
                    return userRepository.save(userModel);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
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
