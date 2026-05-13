package com.sil.informatica.modules.auth;

import com.sil.informatica.modules.user.User;
import com.sil.informatica.modules.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/// Controller responsável pelo fluxo de autenticação e ingresso (Identity) no sistema.
///
/// Centraliza as operações de Login, Registro e recuperação de acesso seguindo o padrão 2026.
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /// Exibe a página de login.
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    /// Exibe o formulário de cadastro de novo usuário.
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    /// Processa o registro de um novo usuário.
    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, String confirmPassword, Model model) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        if (confirmPassword == null || !confirmPassword.equals(user.getPassword())) {
            model.addAttribute("errorMessage", "As senhas não coincidem.");
            return "auth/register";
        }

        try {
            // Define o papel padrão para novos registros via site
            user.setRole("USER");
            userService.save(user);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }
        return "auth/success";
    }
}
