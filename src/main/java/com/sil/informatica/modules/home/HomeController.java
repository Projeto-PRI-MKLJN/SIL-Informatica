package com.sil.informatica.modules.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/signs";
    }

    @GetMapping("/home/about")
    public String about() {
        return "home/about";
    }
}
