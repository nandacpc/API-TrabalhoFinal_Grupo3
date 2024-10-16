package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Pedido;
import org.serratec.shablau.model.StatusEnum;

public record PedidoDto(Long id, LocalDate data_pedido, LocalDate data_entrega, LocalDate data_envio, StatusEnum status,
        double valor_total) {
    public Pedido toEntity() {
        Pedido pedido = new Pedido();
        pedido.setId(this.id);
        pedido.setData_pedido(this.data_pedido);
        pedido.setData_entrega(this.data_entrega);
        pedido.setData_envio(this.data_envio);
        pedido.setStatus(this.status);
        pedido.setValor_total(this.valor_total);
        return pedido;
    }

    public static PedidoDto toDto(Pedido pedido) {
            return new PedidoDto(pedido.getId(), pedido.getData_pedido(),
                    pedido.getData_entrega(), pedido.getData_envio(), pedido.getStatus(),
                    pedido.getValor_total());
        }
}

