package com.sil.informatica.modules.sign;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SignService {

    private final SignRepository signRepository;

    public SignService(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    public List<Sign> findAll() {
        return signRepository.findAll();
    }

    public Optional<Sign> findById(Long id) {
        return signRepository.findById(id);
    }

    public List<Sign> searchByTermo(String termo) {
        if (termo == null || termo.isEmpty()) {
            return signRepository.findAll();
        }
        return signRepository.findByTermoContainingIgnoreCase(termo);
    }

    public Sign save(Sign sign) {
        return signRepository.save(sign);
    }

    public void delete(Long id) {
        signRepository.deleteById(id);
    }
}
