package org.serratec.shablau.dto;

import java.time.LocalDate;

public record ProdutoCadastroDto( 
	String nome,
	String descricao,
	int qnt_estoque,
	LocalDate data_cadastro,
	double valor_unitario,
	String imagem,
	Long id_categoria
	) {
}
