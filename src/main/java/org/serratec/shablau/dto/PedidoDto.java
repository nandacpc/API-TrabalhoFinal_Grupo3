package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Pedido;
import org.serratec.shablau.model.StatusEnum;

public record PedidoDto(
		Long id_pedido, 
		LocalDate dataPedido, 
		LocalDate dataEntrega, 
		LocalDate dataEnvio, 
		StatusEnum statusPedido,
        double valorTotal,
        ClienteDto cliente
        ) {
	
    public Pedido toEntity() {
        Pedido pedido = new Pedido();
        
        pedido.setId_pedido(this.id_pedido);
        pedido.setDataPedido(this.dataPedido);
        pedido.setDataEntrega(this.dataEntrega);
        pedido.setDataEnvio(this.dataEnvio);
        pedido.setStatusPedido(this.statusPedido);
        pedido.setValorTotal(this.valorTotal);
        pedido.setCliente(this.cliente.toEntity());
        
        return pedido;
    }

    public static PedidoDto toDto(Pedido pedido) {
            return new PedidoDto(pedido.getId_pedido(), pedido.getDataPedido(),
                    pedido.getDataEntrega(), pedido.getDataEnvio(), pedido.getStatusPedido(),
                    pedido.getValorTotal(), ClienteDto.toDto(pedido.getCliente()));
        }

}
