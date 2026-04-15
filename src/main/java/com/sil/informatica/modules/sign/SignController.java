package com.sil.informatica.modules.sign;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/signs")
public class SignController {

    private final SignService signService;

    public SignController(SignService signService) {
        this.signService = signService;
    }

    @GetMapping
    public List<Sign> getAllSigns(@RequestParam(required = false) String q) {
        if (q != null) {
            return signService.searchByTermo(q);
        }
        return signService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sign> getSignById(@PathVariable Long id) {
        return signService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
