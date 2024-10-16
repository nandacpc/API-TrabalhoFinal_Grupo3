package org.serratec.shablau.dto;

import java.time.LocalDate;

public record ProdutoDto(
		Long id,
		String nome,
		String descricao,
		int qtd_estoque,
		LocalDate data_cadastro,
		double valor_unitario,
		String imagem
		) {

}
