package com.sil.informatica.modules.favorite;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/// Serviço para gestão e persistência dos favoritos dos usuários.
///
/// Este serviço permite que usuários salvem sinais do glossário como favoritos.
@Service
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /// Adiciona um [Sign] aos favoritos de um [User] caso ainda não exista.
    ///
    /// @param user O usuário que está favoritando.
    /// @param sign O sinal a ser favoritado.
    /// @return O objeto [Favorite] persistido.
    public Favorite addFavorite(User user, Sign sign) {
        Optional<Favorite> existing = favoriteRepository.findByUserAndSign(user, sign);
        if (existing.isPresent()) {
            return existing.get();
        }
        Favorite favorite = new Favorite(user, sign);
        return favoriteRepository.save(favorite);
    }

    /// Remove um favorito do sistema pelo seu identificador.
    ///
    /// @param id ID do favorito a ser removido.
    public void removeFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }

    /// Lista todos os favoritos de um usuário específico.
    ///
    /// @param user O usuário dono dos favoritos.
    /// @return Lista de objetos [Favorite].
    public List<Favorite> listFavoritesByUser(User user) {
        return favoriteRepository.findByUser(user);
    }

    /// Busca um favorito pelo seu identificador único.
    ///
    /// @param id ID do favorito.
    /// @return Um [Optional] contendo o [Favorite] se encontrado.
    public Optional<Favorite> findById(Long id) {
        return favoriteRepository.findById(id);
    }
}
