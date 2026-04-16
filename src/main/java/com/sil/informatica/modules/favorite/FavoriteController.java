package com.sil.informatica.modules.favorite;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.sign.SignService;
import com.sil.informatica.modules.user.User;
import com.sil.informatica.modules.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    private final SignService signService;

    public FavoriteController(FavoriteService favoriteService, UserService userService, SignService signService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
        this.signService = signService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Favorite> addFavorite(@RequestParam Long userId, @RequestParam Long signId) {
        User user = userService.findById(userId).orElse(null);
        Sign sign = signService.findById(signId).orElse(null);

        if (user == null || sign == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteService.addFavorite(user, sign));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long id) {
        if (favoriteService.findById(id).isPresent()) {
            favoriteService.removeFavorite(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Favorite>> listFavorites(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(user -> ResponseEntity.ok(favoriteService.listFavoritesByUser(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}
