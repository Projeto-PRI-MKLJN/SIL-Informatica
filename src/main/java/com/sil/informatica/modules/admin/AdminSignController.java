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
        return "admin/signs/index";
    }

    /// Exibe o formulário de criação de um novo sinal.
    ///
    /// @param model O modelo do Spring.
    /// @return O caminho da view do formulário.
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("sign", new Sign());
        return "admin/signs/form";
    }

    /// Processa a criação de um novo sinal.
    ///
    /// @param sign O objeto [Sign] validado.
    /// @param result Resultado da validação de formulário.
    /// @return Redirecionamento ou retorno ao formulário em caso de erro.
    @PostMapping
    public String createSign(@Valid Sign sign, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/signs/form";
        }
        signService.save(sign);
        return "redirect:/admin/signs";
    }

    /// Exibe o formulário de edição para um sinal existente.
    ///
    /// @param id ID do sinal a ser editado.
    /// @param model O modelo do Spring.
    /// @return O caminho da view do formulário.
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return signService.findById(id)
                .map(sign -> {
                    model.addAttribute("sign", sign);
                    return "admin/signs/form";
                })
                .orElse("redirect:/admin/signs");
    }

    /// Processa a atualização de um sinal existente.
    ///
    /// @param id ID do sinal.
    /// @param sign Dados atualizados do sinal.
    /// @param result Resultado da validação.
    /// @return Redirecionamento após sucesso ou erro.
    @PostMapping("/{id}")
    public String updateSign(@PathVariable Long id, @Valid Sign sign, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/signs/form";
        }
        return signService.findById(id)
                .map(existingSign -> {
                    existingSign.setTerm(sign.getTerm());
                    existingSign.setDescription(sign.getDescription());
                    existingSign.setCategory(sign.getCategory());
                    existingSign.setVideoUrl(sign.getVideoUrl());
                    signService.save(existingSign);
                    return "redirect:/admin/signs";
                })
                .orElse("redirect:/admin/signs");
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
