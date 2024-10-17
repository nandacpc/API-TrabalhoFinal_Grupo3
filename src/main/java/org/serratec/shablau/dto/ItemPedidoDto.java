package org.serratec.shablau.dto;

public record ItemPedidoDto(
		Long id_item_pedido,
		int quantidade,
		double preco_venda,
		double percentual_desconto,
		double valor_bruto,
		double valor_liquido
		) {

}
