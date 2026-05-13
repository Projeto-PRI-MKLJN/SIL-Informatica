package com.sil.informatica.modules.admin;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.sign.SignService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/// Controller administrativo para gestão do glossário de sinais técnicos.
///
/// Este controlador permite que administradores criem, editem e excluam termos do sistema SIL-Informatica.
@Controller
@RequestMapping("/admin/signs")
public class AdminSignController {

    private final SignService signService;

    @Autowired
    public AdminSignController(SignService signService) {
        this.signService = signService;
    }

    /// Lista todos os sinais no painel administrativo.
    ///
    /// @param model O modelo do Spring.
    /// @return O caminho da view do índice administrativo.
    @GetMapping
    public String listSigns(Model model) {
        model.addAttribute("signs", signService.findAll());
        model.addAttribute("sign", new Sign());
        return "admin/signs/index";
    }

    /// Processa a criação ou atualização de um sinal.
    @PostMapping
    public String saveSign(@Valid Sign sign, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("signs", signService.findAll());
            model.addAttribute("sign", sign);
            return "admin/signs/index";
        }
        signService.save(sign);
        return "redirect:/admin/signs";
    }

    /// Remove um sinal do sistema.
    ///
    /// @param id ID do sinal.
    /// @return Redirecionamento para a página inicial administrativa.
    @GetMapping("/delete/{id}")
    public String deleteSign(@PathVariable Long id) {
        signService.delete(id);
        return "redirect:/admin/signs";
    }
}
