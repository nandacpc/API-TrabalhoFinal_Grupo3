package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Pedido;
import org.serratec.shablau.model.StatusEnum;

public record PedidoDto(
		Long id_pedido, 
		LocalDate data_pedido, 
		LocalDate data_entrega, 
		LocalDate data_envio, 
		StatusEnum status_pedido,
        double valor_total,
        ClienteDto cliente
        ) {
	
    public Pedido toEntity() {
        Pedido pedido = new Pedido();
        pedido.setId_pedido(this.id_pedido);
        pedido.setData_pedido(this.data_pedido);
        pedido.setData_entrega(this.data_entrega);
        pedido.setData_envio(this.data_envio);
        pedido.setStatus_pedido(this.status_pedido);
        pedido.setValor_total(this.valor_total);
        pedido.setCliente(this.cliente.toEntity());
        return pedido;
    }

    public static PedidoDto toDto(Pedido pedido) {
            return new PedidoDto(pedido.getId_pedido(), pedido.getData_pedido(),
                    pedido.getData_entrega(), pedido.getData_envio(), pedido.getStatus_pedido(),
                    pedido.getValor_total(), ClienteDto.toDto(pedido.getCliente()));
        }

}
