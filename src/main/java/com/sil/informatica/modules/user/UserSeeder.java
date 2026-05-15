package com.sil.informatica.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/// Seeder para garantir a existência de um usuário administrador inicial.
@Component
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("Administrador SIL");
            admin.setEmail("admin@sil.com");
            admin.setPassword("admin123"); // Em produção, usar BCrypt
            admin.setRole("ADMIN");

            userRepository.save(admin);
            System.out.println(">>> UserSeeder: Usuário administrador criado com sucesso (admin@sil.com / admin123)");
        }
    }
}
