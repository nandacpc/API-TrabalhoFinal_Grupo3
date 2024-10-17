package org.serratec.shablau.repository;

import org.serratec.shablau.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
}
