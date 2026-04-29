package com.sil.informatica.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

/// Controller responsável pelo gerenciamento de conta e perfil do usuário logado.
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("favoritesCount", 0);
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        return "user/profile";
    }
}
