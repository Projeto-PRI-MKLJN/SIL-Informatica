package com.sil.informatica.modules.sign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.sil.informatica.modules.favorite.FavoriteService;
import com.sil.informatica.modules.auth.SecurityUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

/// Controller responsável pela interface pública de visualização do glossário.
///
/// Provê rotas para listagem, busca e visualização detalhada de termos técnicos.
@Controller
@RequestMapping("/signs")
public class SignController {

    private final SignService signService;
    private final FavoriteService favoriteService;

    @Autowired
    public SignController(SignService signService, FavoriteService favoriteService) {
        this.signService = signService;
        this.favoriteService = favoriteService;
    }

    /// Renderiza a página de listagem de sinais com suporte a filtragem.
    ///
    /// @param term Opcional: termo para busca e filtragem.
    /// @param model O modelo do Spring para transporte de dados para a view.
    /// @return O caminho da view de listagem.
    @GetMapping
    public String listSigns(@RequestParam(required = false) String term, @RequestParam(required = false) String letter, Model model) {
        List<Sign> signs;
        if (letter != null && !letter.isEmpty()) {
            signs = signService.searchByLetter(letter);
        } else if (term != null && !term.isEmpty()) {
            signs = signService.searchByTerm(term);
        } else {
            signs = signService.findAll();
        }
        model.addAttribute("signs", signs);
        model.addAttribute("query", term);
        model.addAttribute("currentLetter", letter);
        return "sign/index";
    }

    /// Renderiza os detalhes de um sinal específico.
    ///
    /// @param id O identificador do sinal.
    /// @param model O modelo do Spring.
    /// @return O caminho da view de detalhes ou redirecionamento em caso de erro.
    @GetMapping("/{id:[0-9]+}")
    public String getSignById(@PathVariable Long id, @AuthenticationPrincipal SecurityUser principal, Model model) {
        return signService.findById(id)
                .map(sign -> {
                    model.addAttribute("sign", sign);
                    boolean isFavorite = false;
                    if (principal != null) {
                        isFavorite = favoriteService.isFavorite(principal.getUser(), sign);
                    }
                    model.addAttribute("isFavorite", isFavorite);
                    return "sign/detail";
                })
                .orElse("redirect:/signs");
    }

    /// Exibe o índice alfabético (A-Z) dos sinais.
    @GetMapping("/glossary")
    public String showGlossaryIndex(@RequestParam(required = false) String letter, Model model) {
        List<Sign> signs;
        if (letter != null && !letter.isEmpty()) {
            signs = signService.searchByLetter(letter);
        } else {
            signs = signService.findAll();
        }
        model.addAttribute("signs", signs);
        model.addAttribute("currentLetter", letter);
        return "sign/glossary";
    }
}
