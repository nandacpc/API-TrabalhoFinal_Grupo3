package org.serratec.shablau.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.serratec.shablau.config.ResourceNotFoundException;
import org.serratec.shablau.dto.ProdutoCadastroDto;
import org.serratec.shablau.dto.ProdutoDto;
import org.serratec.shablau.model.Categoria;
import org.serratec.shablau.model.Produto;
import org.serratec.shablau.repository.CategoriaRepository;
import org.serratec.shablau.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepositorio;

	@Autowired
	CategoriaRepository categoriaRepositorio;

	public ProdutoDto salvarProduto(ProdutoCadastroDto produtoCadastroDto) {
		Categoria categoria = categoriaRepositorio.findById(produtoCadastroDto.idCategoria()).orElseThrow(
				() -> new RuntimeException("Categoria não encontrada com o ID: " + produtoCadastroDto.idCategoria()));
		Produto novoProduto = new Produto();
		novoProduto.setNome(produtoCadastroDto.nome());
		novoProduto.setDescricao(produtoCadastroDto.descricao());
		novoProduto.setDataCadastro(produtoCadastroDto.dataCadastro());
		novoProduto.setQtdEstoque(produtoCadastroDto.qntEstoque());
		novoProduto.setCategoria(categoria);
		return ProdutoDto.toDto(produtoRepositorio.save(novoProduto));
	}

	public List<ProdutoDto> obterTodosProdutos() {
		return produtoRepositorio.findAll().stream().map(p -> ProdutoDto.toDto(p)).toList();
	}

	public Optional<ProdutoDto> obterProdutoPorId(Long idProduto) {
		if (!produtoRepositorio.existsById(idProduto)) {
			throw new ResourceNotFoundException("Produto com ID " + idProduto + " não encontrado.");
		}
		return Optional.of(ProdutoDto.toDto(produtoRepositorio.findById(idProduto).get()));
	}

	public List<ProdutoDto> obterProdutoPorNome(String nome) {
		List<Produto> produtos = produtoRepositorio.findByNomeContainingIgnoreCase(nome);
		return produtos.stream().map(p -> ProdutoDto.toDto(p)).toList();
	}

	public List<ProdutoDto> obterProdutoPorIntervaloData(LocalDate dataInicio, LocalDate dataFinal) {
		List<Produto> produtos = produtoRepositorio.findByDataCadastroBetween(dataInicio, dataFinal);
		return produtos.stream().map(p -> ProdutoDto.toDto(p)).toList();
	}

	public List<ProdutoDto> obterProdutoPorIntervaloEstoque(int min, int max) {
		List<Produto> produtos = produtoRepositorio.findByQtdEstoqueBetween(min, max);
		return produtos.stream().map(p -> ProdutoDto.toDto(p)).toList();
	}

	public List<ProdutoDto> obterProdutoPorIntervaloValor(int min, int max) {
		List<Produto> produtos = produtoRepositorio.findByValorUnitarioBetween(min, max);
		return produtos.stream().map(p -> ProdutoDto.toDto(p)).toList();
	}

	public List<ProdutoDto> obterProdutoPorCategoria(Long idCategoria) {
		List<Produto> produtos = produtoRepositorio.findByCategoriaIdCategoria(idCategoria);
		return produtos.stream().map(p -> ProdutoDto.toDto(p)).toList();
	}

	public Optional<ProdutoDto> alterarProduto(Long id_produto, ProdutoCadastroDto produtoCadastroDto) {
		if (!produtoRepositorio.existsById(id_produto)) {
			throw new ResourceNotFoundException("Produto com ID " + id_produto + " não encontrado.");
		}

		Categoria categoria = categoriaRepositorio.findById(produtoCadastroDto.idCategoria())
				.orElseThrow(() -> new RuntimeException("Categoria não encontrada."));

		Produto produtoEntity = produtoRepositorio.findById(id_produto)
				.orElseThrow(() -> new RuntimeException("Produto não encontrado."));

		produtoEntity.setDataCadastro(produtoCadastroDto.dataCadastro());
		produtoEntity.setDescricao(produtoCadastroDto.descricao());
		produtoEntity.setImagem(produtoCadastroDto.imagem());
		produtoEntity.setNome(produtoCadastroDto.nome());
		produtoEntity.setQtdEstoque(produtoCadastroDto.qntEstoque());
		produtoEntity.setValorUnitario(produtoCadastroDto.valorUnitario());
		produtoEntity.setCategoria(categoria);
		produtoRepositorio.save(produtoEntity);
		return Optional.of(ProdutoDto.toDto(produtoEntity));
	}

	public boolean apagarProduto(Long id_produto) {
		if (!produtoRepositorio.existsById(id_produto)) {
			throw new ResourceNotFoundException("Produto com ID " + id_produto + " não encontrado.");
		}
		produtoRepositorio.deleteById(id_produto);
		return true;
	}

//	{
//	  "nome": "Capinhas personalizadas",
//	  "descricao": "Capinhas com imagens personalizadas",
//	  "qntEstoque": 15,
//	  "dataCadastro": "2024-10-18",
//	  "valorUnitario": 49.90,
//	  "imagem": "http://imagemcapinhapersonalizada.jpg",
//	  "idCategoria": 1
//	}
}