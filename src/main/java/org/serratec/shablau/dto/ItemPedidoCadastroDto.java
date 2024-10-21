package org.serratec.shablau.dto;

public record ItemPedidoCadastroDto(
	int quantidade,
	double percentualDesconto,   
	Long idProduto
		) {
}
