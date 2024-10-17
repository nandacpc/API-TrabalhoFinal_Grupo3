package org.serratec.shablau.dto;

import org.serratec.shablau.model.ItemPedido;

public record ItemPedidoDto(
		Long id_item_pedido,
		int quantidade,
		double preco_venda,
		double percentual_desconto,
		double valor_bruto,
		double valor_liquido,
		PedidoDto pedido,
		ProdutoDto produto
		) {

	public ItemPedido toEntity() {
		ItemPedido item_pedido = new ItemPedido();
		item_pedido.setId_item_pedido(this.id_item_pedido);
		item_pedido.setQuantidade(this.quantidade);
		item_pedido.setPreco_venda(preco_venda);
		item_pedido.setPercentual_desconto(percentual_desconto);
		item_pedido.setValor_bruto(valor_bruto);
		item_pedido.setValor_liquido(valor_liquido);
		item_pedido.setPedido(this.pedido.toEntity());
		item_pedido.setProduto(this.produto.toEntity());
		return item_pedido;
	}
	
	public static ItemPedidoDto toDto(ItemPedido item_pedido) {
		return new ItemPedidoDto(item_pedido.getId_item_pedido(), item_pedido.getQuantidade(),
				item_pedido.getPreco_venda(), item_pedido.getPercentual_desconto(), item_pedido.getValor_bruto(),
				item_pedido.getValor_liquido(), PedidoDto.toDto(item_pedido.getPedido()), ProdutoDto.toDto(item_pedido.getProduto())
				);
	}
}
