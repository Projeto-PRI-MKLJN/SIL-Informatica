package com.sil.informatica.modules.sign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignRepository extends JpaRepository<Sign, Long> {
    List<Sign> findByTermoContainingIgnoreCase(String termo);
}
