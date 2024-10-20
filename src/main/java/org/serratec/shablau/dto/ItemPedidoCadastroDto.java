package org.serratec.shablau.dto;

public record ItemPedidoCadastroDto(
	int quantidade,
	double percentual_desconto,   
	Long id_produto
		) {
}
