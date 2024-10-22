package org.serratec.shablau.repository;

import java.time.LocalDate;
import java.util.List;

import org.serratec.shablau.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByDataCadastro(LocalDate data_cadastro);
    List<Produto> findByDataCadastroBetween(LocalDate dataInicio, LocalDate dataFinal);
    List<Produto> findByQtdEstoqueIs(int qtd_estoque);
	List<Produto> findByQtdEstoqueBetween(int min, int max);
	List<Produto> findByValorUnitarioBetween(int min, int max);
	List<Produto> findByCategoriaIdCategoria(Long idCategoria);
}
