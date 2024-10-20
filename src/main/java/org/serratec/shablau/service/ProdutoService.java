package org.serratec.shablau.service;

import java.util.List;
import java.util.Optional;

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
		CategoriaDto categoria = categoriaService.obterCategoriaPorId(produtoCadastroDto.id_categoria())
				.orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + produtoCadastroDto.id_categoria()));
		
		Produto novoProduto = new Produto();
		novoProduto.setNome(produtoCadastroDto.nome());
		novoProduto.setDescricao(produtoCadastroDto.descricao());
		novoProduto.setDataCadastro(produtoCadastroDto.data_cadastro());
		novoProduto.setQtdEstoque(produtoCadastroDto.qnt_estoque());
		novoProduto.setCategoria(categoria.toEntity());		
		return ProdutoDto.toDto(produtoRepositorio.save(novoProduto));
	}
	

	// READ
	public List<ProdutoDto> obterTodosProdutos(){
		return produtoRepositorio.findAll().stream().map(p -> ProdutoDto.toDto(p)).toList();
	}

	public Optional<ProdutoDto> obterProdutoPorId(Long id) {
		if (!produtoRepositorio.existsById(id)) {
			return Optional.empty();
		}
		return Optional.of(ProdutoDto.toDto(produtoRepositorio.findById(id).get()));
	}

	// UPDATE
	public Optional<ProdutoDto> alterarProduto(Long id_produto, ProdutoDto produtoDto) {
		if (!produtoRepositorio.existsById(id_produto)) {
			return Optional.empty();
		}
		Produto produtoEntity = produtoDto.toEntity();
		produtoEntity.setId_produto(id_produto);
		produtoRepositorio.save(produtoEntity);
		return Optional.of(ProdutoDto.toDto(produtoEntity));
	}

	// DELETE
	public boolean apagarProduto(Long id_produto) {
		if (!produtoRepositorio.existsById(id_produto)) {
			return false;
		}
		produtoRepositorio.deleteById(id_produto);
		return true;
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
