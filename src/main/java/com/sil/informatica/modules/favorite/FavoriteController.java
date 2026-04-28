package com.sil.informatica.modules.favorite;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.sign.SignService;
import com.sil.informatica.modules.user.User;
import com.sil.informatica.modules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String index() {
        return "redirect:/favorites/user/1";
    }

    /// Adiciona um novo sinal aos favoritos de um usuário.
    ///
    /// @param userId Identificador do [User].
    /// @param signId Identificador do [Sign].
    /// @return Redirecionamento para a página de detalhes do sinal.
    @PostMapping("/add")
    public String addFavorite(@RequestParam Long userId, @RequestParam Long signId) {
        User user = userService.findById(userId).orElse(null);
        Sign sign = signService.findById(signId).orElse(null);

        if (user != null && sign != null) {
            favoriteService.addFavorite(user, sign);
        }
        return "redirect:/signs/" + signId;
    }

    /// Remove um sinal da lista de favoritos do usuário.
    ///
    /// @param id Identificador da entidade [Favorite].
    /// @param userId Identificador do usuário (para redirecionamento).
    /// @return Redirecionamento para a lista de favoritos do usuário.
    @PostMapping("/remove/{id}")
    public String removeFavorite(@PathVariable Long id, @RequestParam Long userId) {
        favoriteService.removeFavorite(id);
        return "redirect:/favorites/user/" + userId;
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
                    model.addAttribute("favorites", favoriteService.listFavoritesByUser(user));
                    model.addAttribute("user", user);
                    return "favorite/index";
                })
                .orElse("redirect:/");
    }
}
