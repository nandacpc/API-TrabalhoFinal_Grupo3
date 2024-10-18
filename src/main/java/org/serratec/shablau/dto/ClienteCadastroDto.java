package org.serratec.shablau.dto;

import java.time.LocalDate;

public record ClienteCadastroDto( 
		String email,
		String nomeCompleto,
		String cpf,
		String telefone,
		LocalDate dataNascimento,
		String cep,
		int numero,
		String complemento
		) {

}

