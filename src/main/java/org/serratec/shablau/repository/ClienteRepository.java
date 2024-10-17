package org.serratec.shablau.repository;

import org.serratec.shablau.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
}
