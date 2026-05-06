package com.sil.informatica.modules.sign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/// Serviço para gestão e busca de termos técnicos no glossário.
///
/// Este serviço gerencia a persistência e as consultas da entidade [Sign].
@Service
@Transactional
public class SignService {

    private final SignRepository signRepository;

    @Autowired
    public SignService(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    /// Retorna todos os sinais cadastrados.
    ///
    /// @return Uma lista de todos os [Sign] encontrados.
    public List<Sign> findAll() {
        return signRepository.findAll();
    }

    /// Busca um sinal específico pelo seu ID.
    ///
    /// @param id O identificador único do sinal.
    /// @return Um [Optional] contendo o [Sign] se encontrado.
    public Optional<Sign> findById(Long id) {
        return signRepository.findById(id);
    }

    /// Busca sinais que contenham o termo informado (case-insensitive).
    ///
    /// @param term O texto para pesquisa.
    /// @return Uma lista de [Sign] correspondentes.
    public List<Sign> searchByTerm(String term) {
        if (term == null || term.isEmpty()) {
            return signRepository.findAll();
        }
        return signRepository.findByTermContainingIgnoreCase(term);
    }

    /// Busca sinais que comecem com a letra informada.
    public List<Sign> searchByLetter(String letter) {
        if (letter == null || letter.isEmpty()) {
            return signRepository.findAll();
        }
        return signRepository.findByTermStartingWithIgnoreCase(letter);
    }

    /// Salva ou atualiza um sinal no sistema.
    ///
    /// @param sign O sinal a ser persistido.
    /// @return O [Sign] salvo.
    public Sign save(Sign sign) {
        return signRepository.save(sign);
    }

    /// Exclui um sinal do sistema.
    ///
    /// @param id O ID do sinal a ser removido.
    public void delete(Long id) {
        signRepository.deleteById(id);
    }
}
