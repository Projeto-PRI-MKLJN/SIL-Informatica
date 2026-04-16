package com.sil.informatica.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Salva um novo usuário no banco de dados, garantindo que o e-mail seja único.
     * @param user Usuário a ser salvo.
     * @return O usuário salvo.
     * @throws RuntimeException se o e-mail já estiver cadastrado.
     */
    @Transactional
    public User save(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
        return userRepository.save(user);
    }
}
