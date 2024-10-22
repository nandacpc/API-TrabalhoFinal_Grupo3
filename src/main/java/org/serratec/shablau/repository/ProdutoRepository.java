package org.serratec.shablau.repository;

import java.time.LocalDate;
import java.util.List;

import org.serratec.shablau.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByDataCadastro(LocalDate data_cadastro);
    List<Produto> findByQtdEstoqueIs(int qtd_estoque);
	boolean existsByNomeIgnoreCase(String nome);
}