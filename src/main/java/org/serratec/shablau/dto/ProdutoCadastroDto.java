package org.serratec.shablau.dto;

import java.time.LocalDate;

public record ProdutoCadastroDto(String nome, String descricao, int qntEstoque, LocalDate dataCadastro,
		double valorUnitario, String imagem, Long idCategoria) {
}
