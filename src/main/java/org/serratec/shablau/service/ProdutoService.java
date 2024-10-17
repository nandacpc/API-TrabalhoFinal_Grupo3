package org.serratec.shablau.service;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.ProdutoDto;
import org.serratec.shablau.model.Produto;
import org.serratec.shablau.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository produtoRepositorio;

	// CREATE
	public ProdutoDto salvarProduto(ProdutoDto produtoDto) {
		return ProdutoDto.toDto(produtoRepositorio.save(produtoDto.toEntity()));
	}

	// READ
	public List<ProdutoDto> obterTodosProdutos() {
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
}
