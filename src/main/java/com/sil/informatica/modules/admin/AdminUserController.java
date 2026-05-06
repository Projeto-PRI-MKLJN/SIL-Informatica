package com.sil.informatica.modules.admin;

import com.sil.informatica.modules.user.User;
import com.sil.informatica.modules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/// Controller administrativo para gestão de usuários.
///
/// Este controlador permite que administradores visualizem e gerenciem os usuários do sistema.
@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    /// Lista todos os usuários no painel administrativo.
    ///
    /// @param model O modelo do Spring.
    /// @return O caminho da view da listagem de usuários.
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new User());
        return "admin/users/index";
    }

    /// Processa a criação ou atualização de um usuário.
    @org.springframework.web.bind.annotation.PostMapping
    public String saveUser(@jakarta.validation.Valid User user, org.springframework.validation.BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            return "admin/users/index";
        }
        try {
            userService.save(user);
        } catch (RuntimeException e) {
            result.rejectValue("email", "error.user", e.getMessage());
            model.addAttribute("users", userService.findAll());
            return "admin/users/index";
        }
        return "redirect:/admin/users";
    }

    /// Remove um usuário do sistema.
    ///
    /// @param id ID do usuário.
    /// @return Redirecionamento para a listagem de usuários.
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
