package org.serratec.shablau.repository;

import java.util.List;

import org.serratec.shablau.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	List<Cliente> findByNomeContainingIgnoreCase(String nome);

	List<Cliente> findByCpf(String cpf);
}
