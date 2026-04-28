package com.sil.informatica.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

/// Controller responsável pela fluxos de cadastro e gerenciamento de conta do usuário.
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

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

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        User mockUser = new User();
        mockUser.setName("Usuário");
        mockUser.setEmail("teste@sil.com");
        
        model.addAttribute("user", mockUser);
        model.addAttribute("favoritesCount", 0);
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User mockUser = new User();
        mockUser.setName("Usuário");
        mockUser.setEmail("teste@sil.com");
        
        model.addAttribute("user", mockUser);
        return "user/profile";
    }
}
