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
<<<<<<< HEAD
	List<Produto> findByQtdEstoqueBetween(int min, int max);
	List<Produto> findByValorUnitarioBetween(int min, int max);
	List<Produto> findByCategoriaIdCategoria(Long idCategoria);
=======
	boolean existsByNomeIgnoreCase(String nome);
>>>>>>> 1ac6f66da363073813d04ce2ef67947a98875b61
}