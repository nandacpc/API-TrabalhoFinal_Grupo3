package org.serratec.shablau.repository;

import java.time.LocalDate;
import java.util.List;

import org.serratec.shablau.dto.ItemPedidoRelatorioDto;
import org.serratec.shablau.model.Pedido;
import org.serratec.shablau.model.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	List<Pedido> findByStatusPedido(StatusEnum statusPedido);

	List<Pedido> findByDataPedido(LocalDate dataPedido);

	List<Pedido> findByDataEntrega(LocalDate dataEntrega);

	List<Pedido> findByDataEnvio(LocalDate dataEnvio);

	@Query("SELECT new org.serratec.shablau.dto.ItemPedidoRelatorioDto(pr.idProduto, pr.nome, pr.valorUnitario, ip.quantidade, ip.percentualDesconto, ip.valorLiquido) "
			+ "FROM Pedido p " + "JOIN p.itens ip " + "JOIN ip.produto pr " + "WHERE p.id = :idPedido")
	List<ItemPedidoRelatorioDto> findItensByPedidoId(Long idPedido);

}