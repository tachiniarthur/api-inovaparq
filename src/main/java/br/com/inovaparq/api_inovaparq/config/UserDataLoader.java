package br.com.inovaparq.api_inovaparq.config;

import br.com.inovaparq.api_inovaparq.model.UserModel;
import br.com.inovaparq.api_inovaparq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
public class UserDataLoader {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public ApplicationRunner loadAdminUser() {
        return args -> {
            Optional<UserModel> adminUser = userRepository.findByUsername("admin");

            if (adminUser.isEmpty()) {
                UserModel admin = new UserModel();
                admin.setName("Administrador");
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setCpf("00000000000");
                admin.setAdmin(true);
                admin.setActive(true);

                // Criptografa a senha admin123
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                admin.setPassword(encoder.encode("123"));

                userRepository.save(admin);
                System.out.println("Usuário admin padrão criado.");
            } else {
                System.out.println("Usuário admin já existe.");
            }
        };
    }
}
