package org.serratec.shablau.dto;

public record ItemPedidoRelatorioDto(Long idProduto, String nomeProduto, double valorUnitario, int quantidade,
		double percentualDesconto, double valorLiquido) {
}
