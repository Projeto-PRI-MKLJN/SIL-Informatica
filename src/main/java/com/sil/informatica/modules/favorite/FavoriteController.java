package com.sil.informatica.modules.favorite;

import com.sil.informatica.modules.user.User;
import com.sil.informatica.modules.sign.model.Sign;
import com.sil.informatica.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserRepository userRepository;

    @Autowired
    public FavoriteController(FavoriteService favoriteService, UserRepository userRepository) {
        this.favoriteService = favoriteService;
        this.userRepository = userRepository;
    }

    /**
     * Lista os favoritos do usuário mockado (ID 1).
     */
    @GetMapping
    public String listFavorites(Model model) {
        User user = getMockUser();
        List<Favorite> favorites = favoriteService.listByUser(user);
        model.addAttribute("favorites", favorites);
        return "favorite/list";
    }

    /**
     * Adiciona um favorito para o usuário mockado (ID 1).
     * O sinal é recebido como parâmetro.
     */
    @PostMapping("/add")
    public String addFavorite(@RequestParam("id_sinal") Long idSinal) {
        User user = getMockUser();
        
        // Mocking the Sign object (assuming Sign has a simple constructor with ID)
        Sign sign = new Sign();
        sign.setId(idSinal);
        
        favoriteService.addFavorite(user, sign);
        return "redirect:/favorites";
    }

    /**
     * Remove um favorito por ID.
     */
    @PostMapping("/remove/{id}")
    public String removeFavorite(@PathVariable("id") Long id) {
        favoriteService.deleteFavorite(id);
        return "redirect:/favorites";
    }

    /**
     * Busca um usuário Mockado de ID 1 para simular a ausência de autenticação nesta etapa.
     */
    private User getMockUser() {
        return userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Usuário mock de ID 1 não encontrado. Favor criar um usuário no banco para testes."));
    }
}
