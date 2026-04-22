package com.sil.informatica.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/// Serviço responsável pelo gerenciamento de usuários e validação de regras de domínio.
///
/// Este serviço assegura a integridade dos dados de [User] e controle de acesso básico.
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /// Salva um novo usuário no sistema, validando a unicidade do e-mail.
    ///
    /// @param user O objeto [User] com os dados do novo usuário.
    /// @return O usuário persistido com sucesso.
    /// @throws RuntimeException Caso o e-mail já esteja em uso.
    public User save(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
        return userRepository.save(user);
    }

    /// Localiza um usuário no banco de dados através do e-mail informado.
    ///
    /// @param email O e-mail para busca.
    /// @return Um [Optional] contendo o [User] se localizado.
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /// Recupera um usuário pelo seu identificador primário.
    ///
    /// @param id O ID do usuário.
    /// @return Um [Optional] contendo o [User] se localizado.
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
