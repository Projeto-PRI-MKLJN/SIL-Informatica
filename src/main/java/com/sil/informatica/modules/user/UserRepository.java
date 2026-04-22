package com.sil.informatica.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/// Repositório para abstração de persistência da entidade [User].
///
/// Provê acesso aos dados dos usuários cadastrados no banco de dados.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /// Localiza um usuário baseado no endereço de e-mail.
    ///
    /// @param email O e-mail cadastrado.
    /// @return Um [Optional] contendo o usuário se localizado.
    Optional<User> findByEmail(String email);
}
