package org.serratec.shablau.repository;

import java.util.List;

import org.serratec.shablau.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	List<Cliente> findByNomeCompletoContainingIgnoreCase(String nome);
	Cliente findByCpf(String cpf);
	boolean existsByCpf(String cpf);
}
