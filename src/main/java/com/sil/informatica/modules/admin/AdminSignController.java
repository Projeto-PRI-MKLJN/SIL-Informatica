package com.sil.informatica.modules.admin;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.sign.SignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/signs")
public class AdminSignController {

    private final SignService signService;

    public AdminSignController(SignService signService) {
        this.signService = signService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sign createSign(@RequestBody Sign sign) {
        return signService.save(sign);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sign> updateSign(@PathVariable Long id, @RequestBody Sign signDetails) {
        return signService.findById(id)
                .map(existingSign -> {
                    existingSign.setTermo(signDetails.getTermo());
                    existingSign.setDescricao(signDetails.getDescricao());
                    existingSign.setCategoria(signDetails.getCategoria());
                    existingSign.setVideoUrl(signDetails.getVideoUrl());
                    return ResponseEntity.ok(signService.save(existingSign));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSign(@PathVariable Long id) {
        return signService.findById(id)
                .map(sign -> {
                    signService.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
