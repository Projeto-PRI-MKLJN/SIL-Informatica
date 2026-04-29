package com.sil.informatica.modules.sign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/// Seeder para popular o glossário com termos técnicos iniciais de informática em Libras.
@Component
public class SignSeeder implements CommandLineRunner {

    private final SignRepository signRepository;

    @Autowired
    public SignSeeder(SignRepository signRepository) {
        this.signRepository = signRepository;
    }

    @Override
    public void run(String... args) {
        if (signRepository.count() == 0) {
            signRepository.saveAll(Arrays.asList(
                new Sign("Algoritmo", "Sequência lógica de instruções para resolver um problema ou executar uma tarefa.", "Lógica", "https://www.youtube.com/watch?v=kM9AKAnqe9E"),
                new Sign("Backend", "Parte do sistema que lida com a lógica de negócio, banco de dados e arquitetura do servidor.", "Programação", "https://www.youtube.com/watch?v=L1oMh_8fBy8"),
                new Sign("Frontend", "Interface visual e interação direta com o usuário em aplicações web ou móveis.", "Web", "https://www.youtube.com/watch?v=H7mXv2v_75Y"),
                new Sign("Banco de Dados", "Sistema organizado para armazenamento, gerenciamento e recuperação de informações estruturadas.", "Infraestrutura", "https://www.youtube.com/watch?v=Ofktsne-utM"),
                new Sign("Bug", "Erro ou falha inesperada em um programa que causa comportamento incorreto.", "Qualidade", ""),
                new Sign("Variável", "Espaço na memória do computador usado para armazenar dados que podem mudar durante a execução.", "Programação", ""),
                new Sign("Hardware", "Componentes físicos e tangíveis de um sistema de computador, como memória e processador.", "Hardware", ""),
                new Sign("Git", "Sistema de controle de versão distribuído para rastrear mudanças no código-fonte.", "Ferramentas", ""),
                new Sign("API", "Interface de programação que permite que dois sistemas se comuniquem de forma padronizada.", "Integrações", ""),
                new Sign("CSS", "Linguagem de estilo usada para descrever a apresentação de documentos HTML.", "Web", ""),
                new Sign("JavaScript", "Linguagem de programação que permite criar conteúdo dinâmico e interativo na web.", "Programação", ""),
                new Sign("Cloud Computing", "Entrega de serviços de computação pela internet, incluindo servidores e armazenamento.", "Infraestrutura", "")
            ));
            System.out.println(">>> SignSeeder: Glossário populado com 12 termos técnicos iniciais.");
        }
    }
}
