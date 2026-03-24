package com.sil.informatica.modules.favorite;

import com.sil.informatica.modules.user.User;
import com.sil.informatica.modules.sign.model.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser(User user);

    /**
     * Verifica se já existe um favorito para o mesmo usuário e sinal.
     */
    boolean existsByUserAndSign(User user, Sign sign);
}
