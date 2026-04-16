package com.sil.informatica.modules.favorite;

import com.sil.informatica.modules.user.User;
import com.sil.informatica.modules.sign.model.Sign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * Lista todos os favoritos de um determinado usuário.
     */
    public List<Favorite> listByUser(User user) {
        return favoriteRepository.findByUser(user);
    }

    /**
     * Deleta um favorito pelo seu ID.
     */
    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }

    /**
     * Adiciona um favorito, garantindo que o usuário não favorite o mesmo sinal duas vezes.
     */
    public Favorite addFavorite(User user, Sign sign) {
        if (favoriteRepository.existsByUserAndSign(user, sign)) {
            throw new RuntimeException("Usuário já favoritou este sinal.");
        }
        Favorite favorite = new Favorite(user, sign);
        return favoriteRepository.save(favorite);
    }
}
