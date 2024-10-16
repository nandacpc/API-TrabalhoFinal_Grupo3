package org.serratec.shablau.dto;

import java.time.LocalDate;

public record ClienteDto(
		Long id,
		String email,
		String nome_completo,
		String cpf,
		String telefone,
		LocalDate data_nascimento
		) {

}
