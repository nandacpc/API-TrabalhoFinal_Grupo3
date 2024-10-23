package org.serratec.shablau.dto;

import org.serratec.shablau.model.ItemPedido;

public record ItemPedidoDto(Long idItemPedido, int quantidade, double precoVenda, double percentualDesconto,
		double valorBruto, double valorLiquido, PedidoDto pedido, ProdutoDto produto) {

	public ItemPedido toEntity() {
		ItemPedido item_pedido = new ItemPedido();
		item_pedido.setId_item_pedido(this.idItemPedido);
		item_pedido.setQuantidade(this.quantidade);
		item_pedido.setPrecoVenda(precoVenda);
		item_pedido.setPercentual_desconto(percentualDesconto);
		item_pedido.setValorBruto(valorBruto);
		item_pedido.setValor_liquido(valorLiquido);
		item_pedido.setPedido(this.pedido.toEntity());
		item_pedido.setProduto(this.produto.toEntity());
		return item_pedido;
	}

	public static ItemPedidoDto toDto(ItemPedido item_pedido) {
		return new ItemPedidoDto(item_pedido.getIdItemPedido(), item_pedido.getQuantidade(),
				item_pedido.getPrecoVenda(), item_pedido.getPercentualDesconto(), item_pedido.getValorBruto(),
				item_pedido.getValorLiquido(), PedidoDto.toDto(item_pedido.getPedido()),
				ProdutoDto.toDto(item_pedido.getProduto()));
	}
}
