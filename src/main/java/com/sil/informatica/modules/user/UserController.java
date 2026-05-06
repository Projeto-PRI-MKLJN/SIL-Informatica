package com.sil.informatica.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import com.sil.informatica.modules.favorite.FavoriteService;
import com.sil.informatica.modules.auth.SecurityUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

/// Controller responsável pelo gerenciamento de conta e perfil do usuário logado.
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final FavoriteService favoriteService;

    @Autowired
    public UserController(UserService userService, FavoriteService favoriteService) {
        this.userService = userService;
        this.favoriteService = favoriteService;
    }


    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal SecurityUser principal, Model model) {
        if (principal == null) return "redirect:/auth/login";
        
        int count = favoriteService.listFavoritesByUser(principal.getUser()).size();
        model.addAttribute("favoritesCount", count);
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal SecurityUser principal, Model model) {
        if (principal == null) return "redirect:/auth/login";
        model.addAttribute("user", principal.getUser());
        return "user/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal SecurityUser principal, @Valid User user, BindingResult result, Model model) {
        if (principal == null) return "redirect:/auth/login";

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "user/profile";
        }

        try {
            // Garante que o usuário logado só atualize o próprio perfil
            user.setId(principal.getUser().getId());
            
            // Mantém a Role original, pois usuários comuns não podem mudar de role
            user.setRole(principal.getUser().getRole());
            
            userService.save(user);
            
            // Atualiza a entidade no objeto principal logado
            principal.getUser().setName(user.getName());
            principal.getUser().setEmail(user.getEmail());
            
            model.addAttribute("success", "Perfil atualizado com sucesso!");
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "user/profile";
    }
}