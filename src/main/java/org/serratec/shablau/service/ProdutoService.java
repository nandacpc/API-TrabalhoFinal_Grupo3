package org.serratec.shablau.service;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.config.ResourceNotFoundException;
import org.serratec.shablau.dto.CategoriaDto;
import org.serratec.shablau.dto.ProdutoCadastroDto;
import org.serratec.shablau.dto.ProdutoDto;
import org.serratec.shablau.model.Produto;
import org.serratec.shablau.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepositorio;

	@Autowired
	private CategoriaService categoriaService;

	// CREATE
	public ProdutoDto salvarProduto(ProdutoCadastroDto produtoCadastroDto) {
		CategoriaDto categoria = categoriaService.obterCategoriaPorId(produtoCadastroDto.idCategoria()).orElseThrow(
				() -> new RuntimeException("Categoria não encontrada com o ID: " + produtoCadastroDto.idCategoria()));
		Produto novoProduto = new Produto();
		novoProduto.setNome(produtoCadastroDto.nome());
		novoProduto.setDescricao(produtoCadastroDto.descricao());
		novoProduto.setDataCadastro(produtoCadastroDto.dataCadastro());
		novoProduto.setQtdEstoque(produtoCadastroDto.qntEstoque());
		novoProduto.setCategoria(categoria.toEntity());
		return ProdutoDto.toDto(produtoRepositorio.save(novoProduto));
	}

	// READ
	public List<ProdutoDto> obterTodosProdutos() {
		return produtoRepositorio.findAll().stream().map(p -> ProdutoDto.toDto(p)).toList();
	}

	public Optional<ProdutoDto> obterProdutoPorId(Long id) {
		if (!produtoRepositorio.existsById(id)) {
			throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado.");
		}
		return Optional.of(ProdutoDto.toDto(produtoRepositorio.findById(id).get()));
	}

	// UPDATE
	public Optional<ProdutoDto> alterarProduto(Long id_produto, ProdutoDto produtoDto) {
		if (!produtoRepositorio.existsById(id_produto)) {
			throw new ResourceNotFoundException("Produto com ID " + id_produto + " não encontrado.");
		}
		Produto produtoEntity = produtoDto.toEntity();
		produtoEntity.setIdProduto(id_produto);
		produtoRepositorio.save(produtoEntity);
		return Optional.of(ProdutoDto.toDto(produtoEntity));
	}

	// DELETE
	public void apagarProduto(Long id_produto) {
		if (!produtoRepositorio.existsById(id_produto)) {
			throw new ResourceNotFoundException("Produto com ID " + id_produto + " não encontrado.");
		}
		produtoRepositorio.deleteById(id_produto);
	}

//	{
//	  "nome": "Capinhas personalizadas",
//	  "descricao": "Capinhas com imagens personalizadas",
//	  "qnt_estoque": 15,
//	  "data_cadastro": "2024-10-18",
//	  "valor_unitario": 49.90,
//	  "imagem": "http://imagemcapinhapersonalizada.jpg",
//	  "id_categoria": 1
//	}
}
