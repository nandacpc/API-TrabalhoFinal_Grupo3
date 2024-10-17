package org.serratec.shablau.dto;

import java.util.List;

import org.serratec.shablau.model.ItemPedido;

public record ItemPedidoDto(
		Long id_item_pedido,
		int quantidade,
		double preco_venda,
		double percentual_desconto,
		double valor_bruto,
		double valor_liquido,
		List<PedidoDto> pedido,
		List<ProdutoDto> produto
		) {

	public ItemPedido toEntity() {
		ItemPedido item_pedido = new ItemPedido();
		item_pedido.setId_item_pedido(this.id_item_pedido);
		item_pedido.setQuantidade(this.quantidade);
		item_pedido.setPreco_venda(preco_venda);
		item_pedido.setPercentual_desconto(percentual_desconto);
		item_pedido.setValor_bruto(valor_bruto);
		item_pedido.setValor_liquido(valor_liquido);
		item_pedido.setPedido(this.pedido.stream().map(p -> p.toEntity()).toList());
		item_pedido.setProduto(this.produto.stream().map(pr -> pr.toEntity()).toList());
		return item_pedido;
	}
	
	public static ItemPedidoDto toDto(ItemPedido item_pedido) {
		return new ItemPedidoDto(item_pedido.getId_item_pedido(), item_pedido.getQuantidade(),
				item_pedido.getPreco_venda(), item_pedido.getPercentual_desconto(), item_pedido.getValor_bruto(),
				item_pedido.getValor_liquido(), item_pedido.getPedido().stream().map(p -> PedidoDto.toDto(p)).toList(), 
				item_pedido.getProduto().stream().map(pr -> ProdutoDto.toDto(pr)).toList());
	}
}
