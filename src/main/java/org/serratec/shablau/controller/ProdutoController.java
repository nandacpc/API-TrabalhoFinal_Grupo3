package org.serratec.shablau.controller;

import java.util.List;
import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.ProdutoDto;
import org.serratec.shablau.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController {
	
	@Autowired
    private ProdutoService produtoServico;
	
	@GetMapping
	public List<ProdutoDto> obterTodos() {
		return produtoServico.obterTodosProdutos();
	}
  
  @GetMapping
	public List<ProdutoDto> buscarTodosProdutos() {
		return produtoServico.obterTodosProdutos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDto> buscarProdutoPorId(@PathVariable Long id_produto) {
		Optional<ProdutoDto> produtoDto = produtoServico.obterProdutoPorId(id_produto);

		if (!produtoDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoDto.get());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDto cadastrarProduto(@RequestBody ProdutoDto produtoDto) {
		return produtoServico.salvarProduto(produtoDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDto> modificarProduto(@PathVariable Long id_produto,
			@RequestBody ProdutoDto produtoDto) {
		Optional<ProdutoDto> produtoAlterado = produtoServico.alterarProduto(id_produto, produtoDto);
		if (!produtoAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoAlterado.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id_produto) {
		if (!produtoServico.apagarProduto(id_produto)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

}
