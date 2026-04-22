package com.sil.informatica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/// Classe principal de inicialização do sistema SIL-Informatica.
///
/// Configura o contexto do Spring Boot e inicializa todos os módulos da aplicação.
@SpringBootApplication
public class SilInformaticaApplication {

	/// Ponto de entrada (entry point) da aplicação.
	///
	/// @param args Argumentos de execução via linha de comando.
	public static void main(String[] args) {
		SpringApplication.run(SilInformaticaApplication.class, args);
	}

}