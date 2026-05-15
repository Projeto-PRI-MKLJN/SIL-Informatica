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
        return "admin/users/index";
    }

    /// Exibe o formulário de edição para um usuário existente.
    ///
    /// @param id ID do usuário a ser editado.
    /// @param model O modelo do Spring.
    /// @return O caminho da view do formulário.
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "admin/users/form";
                })
                .orElse("redirect:/admin/users");
    }

    /// Processa a atualização de um usuário.
    ///
    /// @param id ID do usuário.
    /// @param user Dados atualizados.
    /// @return Redirecionamento após sucesso.
    @org.springframework.web.bind.annotation.PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, User user) {
        return userService.findById(id)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setRole(user.getRole());
                    userService.save(existingUser);
                    return "redirect:/admin/users";
                })
                .orElse("redirect:/admin/users");
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
