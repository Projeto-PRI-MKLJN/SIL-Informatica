package com.sil.informatica.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Optional;

/// Seeder para garantir a existência de um usuário administrador inicial.
@Component
public class UserSeeder implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public UserSeeder(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> existingAdmin = userService.findByEmail("admin@sil.com");
        
        if (existingAdmin.isEmpty()) {
            User admin = new User();
            admin.setName("Administrador SIL");
            admin.setEmail("admin@sil.com");
            admin.setPassword("admin123"); 
            admin.setRole("ADMIN");
            userService.save(admin);
            System.out.println(">>> UserSeeder: Novo usuário administrador criado (admin@sil.com / admin123)");
        } else {
            // Garante que a senha está no formato correto no banco
            User admin = existingAdmin.get();
            admin.setPassword("admin123"); // O userService.save() vai encriptar
            userService.save(admin);
            System.out.println(">>> UserSeeder: Senha do administrador sincronizada.");
        }
    }
}
