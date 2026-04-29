package com.sil.informatica.modules.home;

import com.sil.informatica.modules.user.User;
import com.sil.informatica.modules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

/// Advice global para injetar dados comuns em todos os controllers MVC.
///
/// Este componente garante que o objeto 'user' esteja disponível em todas as views
/// para renderização consistente do menu superior (header).
/// 
/// NOTA: Esta é uma solução temporária de 2026 enquanto o Spring Security não é implementado.
@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserService userService;

    @Autowired
    public GlobalControllerAdvice(UserService userService) {
        this.userService = userService;
    }

    /// Adiciona o usuário atual ao modelo de todas as requisições.
    ///
    /// Atualmente, busca o administrador padrão para fins de demonstração da UI.
    /// @return O objeto [User] para o Thymeleaf.
    @ModelAttribute("currentUser")
    public User populateUser() {
        // Busca o administrador criado pelo Seeder para que o menu apareça completo
        Optional<User> admin = userService.findByEmail("admin@sil.com");
        return admin.orElse(null);
    }
}
