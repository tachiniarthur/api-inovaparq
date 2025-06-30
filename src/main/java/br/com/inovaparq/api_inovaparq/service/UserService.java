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

    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserResponseDTO newUser(UserCreateDTO dto) {
        System.out.println("Iniciando criação de usuário: " + dto.getUsername());

        if (!dto.getPassword().equals(dto.getConfirm_password())) {
            System.out.println("Senhas não conferem");
            throw new IllegalArgumentException("Passwords do not match.");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            System.out.println("Username já está em uso");
            throw new IllegalArgumentException("Username já está em uso.");
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty() && userRepository.existsByEmail(dto.getEmail())) {
            System.out.println("Email já está em uso");
            throw new IllegalArgumentException("Email já está em uso.");
        }
        if (dto.getCpf() != null && !dto.getCpf().isEmpty() && userRepository.existsByCpf(dto.getCpf())) {
            System.out.println("CPF já está em uso");
            throw new IllegalArgumentException("CPF já está em uso.");
        }

        // Somente aqui salva!
        UserModel user = new UserModel();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setActive(true);
        user.setAdmin(false);

        String token = UUID.randomUUID().toString();
        user.setToken(token);

        user.setEmail(dto.getEmail());
        user.setCpf(dto.getCpf());

        UserModel saved = userRepository.save(user);

        System.out.println("Usuário criado com sucesso: " + saved.getUsername());

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
            saved.getAdmin(),
            saved.getBirthdate()
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
                // Limpa formatação do CPF
                String cpfLimpo = userAtualizado.getCpf() != null ? userAtualizado.getCpf().replaceAll("[^\\d]", "") : null;

                // Verifica se já existe outro usuário com o mesmo username
                if (userRepository.existsByUsername(userAtualizado.getUsername()) &&
                    !userModel.getUsername().equals(userAtualizado.getUsername())) {
                    throw new IllegalArgumentException("Já existe um usuário com esse username.");
                }

                // Verifica se já existe outro usuário com o mesmo email
                if (userAtualizado.getEmail() != null && !userAtualizado.getEmail().isEmpty() &&
                    userRepository.existsByEmail(userAtualizado.getEmail()) &&
                    !userModel.getEmail().equals(userAtualizado.getEmail())) {
                    throw new IllegalArgumentException("Já existe um usuário com esse e-mail.");
                }

                // Verifica se já existe outro usuário com o mesmo CPF
                if (cpfLimpo != null && !cpfLimpo.isEmpty() &&
                    userRepository.existsByCpf(cpfLimpo) &&
                    !java.util.Objects.equals(userModel.getCpf(), cpfLimpo)) {
                    throw new IllegalArgumentException("Já existe um usuário com esse CPF.");
                }

                // Atualiza os campos
                userModel.setName(userAtualizado.getName());
                userModel.setUsername(userAtualizado.getUsername());
                userModel.setEmail(userAtualizado.getEmail());
                userModel.setCpf(cpfLimpo);
                userModel.setPhoto(userAtualizado.getPhoto());
                userModel.setPhone(userAtualizado.getPhone());
                userModel.setToken(userAtualizado.getToken());
                userModel.setActive(userAtualizado.getActive());
                userModel.setAdmin(false);
                userModel.setBirthdate(userAtualizado.getBirthdate());
                userModel.setRole(userAtualizado.getRole());
                userModel.setCompany(userAtualizado.getCompany());
                userModel.setPassword(passwordEncoder.encode(userAtualizado.getPassword()));

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
