package com.sil.informatica.modules.favorite;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Favorite addFavorite(User user, Sign sign) {
        Optional<Favorite> existing = favoriteRepository.findByUserAndSign(user, sign);
        if (existing.isPresent()) {
            return existing.get();
        }
        Favorite favorite = new Favorite(user, sign);
        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }

    public List<Favorite> listFavoritesByUser(User user) {
        return favoriteRepository.findByUser(user);
    }

    public Optional<Favorite> findById(Long id) {
        return favoriteRepository.findById(id);
    }
}
