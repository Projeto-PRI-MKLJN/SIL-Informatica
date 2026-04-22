package com.sil.informatica.modules.favorite;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/// Repositório para abstração de persistência da entidade [Favorite].
///
/// Gerencia os vínculos de favoritos entre usuários e termos do glossário.
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    /// Lista todos os favoritos de um usuário específico.
    ///
    /// @param user O [User] proprietário dos favoritos.
    /// @return Uma lista de objetos [Favorite].
    List<Favorite> findByUser(User user);

    /// Busca um vínculo de favorito específico.
    ///
    /// @param user O usuário.
    /// @param sign O sinal favoritado.
    /// @return Um [Optional] contendo o registro se localizado.
    Optional<Favorite> findByUserAndSign(User user, Sign sign);
}
