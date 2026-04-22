package com.sil.informatica.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

/// Controller responsável pelas fluxos de cadastro e gerenciamento de conta do usuário.
///
/// Provê rotas para criação de novos perfis no sistema SIL-Informatica.
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /// Exibe o formulário de cadastro para novos usuários.
    ///
    /// @param model O modelo do Spring.
    /// @return O caminho da view do formulário de registro.
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    /// Processa a requisição de registro de um novo usuário.
    ///
    /// @param user O objeto [User] validado.
    /// @param result Resultado da validação do Jakarta Validation.
    /// @param model O modelo para mensagens de erro.
    /// @return View de sucesso ou retorno ao formulário em caso de falha/duplicidade.
    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/register";
        }
        
        try {
            userService.save(user);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/register";
        }
        
        return "user/success";
    }
}
