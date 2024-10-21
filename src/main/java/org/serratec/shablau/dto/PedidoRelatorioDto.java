package org.serratec.shablau.dto;

import java.time.LocalDate;
import java.util.List;

public record PedidoRelatorioDto(
	Long idPedido,
	LocalDate dataPedido,
	double valorTotal,
	List<ItemPedidoRelatorioDto> itens
	) {
}
