package org.serratec.shablau.dto;

import java.time.LocalDate;
import java.util.List;

import org.serratec.shablau.model.StatusEnum;

public record PedidoCadastroDto(LocalDate dataPedido, StatusEnum statusPedido, Long idCliente,
		List<ItemPedidoCadastroDto> itens) {
}
