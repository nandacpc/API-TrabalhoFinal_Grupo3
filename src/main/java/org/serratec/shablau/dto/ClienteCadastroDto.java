package org.serratec.shablau.dto;

import java.time.LocalDate;

public record ClienteCadastroDto( 
		String email,
		String nome_completo,
		String cpf,
		String telefone,
		LocalDate data_nascimento,
		String cep,
		int numero
		) {

}

