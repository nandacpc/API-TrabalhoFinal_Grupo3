package org.serratec.shablau.repository;

import java.util.List;

import org.serratec.shablau.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	List<Produto> findByNomeContainingIgnoreCase(String nome);
	List<Produto> findByDataCadastro(int data_cadastro);
	
}
