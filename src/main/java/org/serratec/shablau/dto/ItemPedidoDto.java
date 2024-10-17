package org.serratec.shablau.dto;

import org.serratec.shablau.model.ItemPedido;

public record ItemPedidoDto(Long id_item_pedido, int quantidade, double preco_venda, double percentual_desconto,
		double valor_bruto, double valor_liquido) {

	public ItemPedido toEntity() {
		ItemPedido itempedido = new ItemPedido();
		itempedido.setId_item_pedido(this.id_item_pedido);
		itempedido.setQuantidade(this.quantidade);
		itempedido.setPreco_venda(this.preco_venda);
		itempedido.setPercentual_desconto(this.percentual_desconto);
		itempedido.setValor_bruto(this.valor_bruto);
		itempedido.setValor_liquido(this.valor_liquido);
		return itempedido;
	}

	public static ItemPedidoDto toDto(ItemPedido itempedido) {
		return new ItemPedidoDto(itempedido.getId_item_pedido(), itempedido.getQuantidade(),
				itempedido.getPreco_venda(), itempedido.getPercentual_desconto(), itempedido.getValor_bruto(),
				itempedido.getValor_liquido());
	}
}
