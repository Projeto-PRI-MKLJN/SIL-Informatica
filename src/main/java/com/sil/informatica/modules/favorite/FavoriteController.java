package com.sil.informatica.modules.favorite;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.sign.SignService;
import com.sil.informatica.modules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.sil.informatica.modules.auth.SecurityUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

/// Controller para gerenciamento das preferências de favoritos dos usuários.
///
/// Provê rotas para adição, remoção e listagem de favoritos vinculados a um [User].
@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    private final SignService signService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService, UserService userService, SignService signService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
        this.signService = signService;
    }

    /// Rota padrão de favoritos (redireciona para o usuário logado).
    @GetMapping
    public String index(@AuthenticationPrincipal SecurityUser principal) {
        if (principal == null) return "redirect:/auth/login";
        return "redirect:/favorites/user/" + principal.getUser().getId();
    }

    /// Alterna o estado de favorito de um sinal para o usuário logado.
    @PostMapping("/toggle")
    public String toggleFavorite(@AuthenticationPrincipal SecurityUser principal, @RequestParam Long signId, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        if (principal == null) return "redirect:/auth/login";
        
        Sign sign = signService.findById(signId).orElse(null);
        if (sign != null) {
            if (favoriteService.isFavorite(principal.getUser(), sign)) {
                favoriteService.removeByUserAndSign(principal.getUser(), sign);
            } else {
                favoriteService.addFavorite(principal.getUser(), sign);
            }
        }

        if ("XMLHttpRequest".equals(requestedWith)) {
            return null; // Retorna resposta vazia para AJAX (200 OK implícito)
        }
        
        return "redirect:/signs/" + signId;
    }

    /// Adiciona um novo sinal aos favoritos do usuário logado (legado, agora usa toggle).
    @PostMapping("/add")
    public String addFavorite(@AuthenticationPrincipal SecurityUser principal, @RequestParam Long signId) {
        return toggleFavorite(principal, signId, null);
    }

    /// Remove um sinal da lista de favoritos do usuário logado.
    @PostMapping("/remove/{id}")
    public String removeFavorite(@AuthenticationPrincipal SecurityUser principal, @PathVariable Long id) {
        if (principal == null) return "redirect:/auth/login";
        
        favoriteService.removeFavorite(id);
        return "redirect:/favorites";
    }

    /// Lista todos os sinais favoritos vinculados a um usuário específico.
    ///
    /// @param userId Identificador do [User].
    /// @param model O modelo para transporte de dados.
    /// @return O caminho da view de lista de favoritos.
    @GetMapping("/user/{userId}")
    public String listFavorites(@PathVariable Long userId, Model model) {
        return userService.findById(userId)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("favorites", favoriteService.listFavoritesByUser(user));
                    return "favorite/index";
                })
                .orElse("redirect:/");
    }
}
