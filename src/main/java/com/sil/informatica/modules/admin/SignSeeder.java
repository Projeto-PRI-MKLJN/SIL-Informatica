package com.sil.informatica.modules.admin;

import com.sil.informatica.modules.sign.Sign;
import com.sil.informatica.modules.sign.SignRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SignSeeder implements CommandLineRunner {

    private final SignRepository signRepository;

    public SignSeeder(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (signRepository.count() == 0) {
            Sign sign1 = new Sign("Algoritmo", "Sequência finita de instruções bem definidas e não ambíguas.", "Programação", "https://example.com/libras/algoritmo");
            Sign sign2 = new Sign("Hardware", "Conjunto de componentes físicos de um computador.", "Infraestrutura", "https://example.com/libras/hardware");
            Sign sign3 = new Sign("Software", "Conjunto de componentes lógicos de um computador.", "Programação", "https://example.com/libras/software");
            Sign sign4 = new Sign("Banco de Dados", "Coleção organizada de informações ou dados estruturados.", "Dados", "https://example.com/libras/banco-de-dados");
            
            signRepository.saveAll(Arrays.asList(sign1, sign2, sign3, sign4));
        }
    }
}
