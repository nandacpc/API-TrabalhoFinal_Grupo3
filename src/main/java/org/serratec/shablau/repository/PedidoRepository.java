package org.serratec.shablau.repository;

import java.time.LocalDate;
import java.util.List;

import org.serratec.shablau.model.Pedido;
import org.serratec.shablau.model.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatusPedido(StatusEnum status_pedido);
    List<Pedido> findByDataPedido(LocalDate data_pedido);
    
    //RELATORIO
    @Query("SELECT p FROM Pedido p JOIN p.itens WHERE p.id_pedido = :id_pedido")
    Pedido relatorio(Long id_pedido);
    
}