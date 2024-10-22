package org.serratec.shablau.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.ProdutoCadastroDto;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoServico;

	@Operation(summary = "Cadastra Produto", description = "Coleta informação do Produto, cadastrado e salva")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ProdutoDto> cadastrarProduto(@Valid @RequestBody ProdutoCadastroDto produtoCadastroDto) {
		return ResponseEntity.ok(produtoServico.salvarProduto(produtoCadastroDto));
	}

	@Operation(summary = "Traz todos os Produtos Cadastrados", description = "Traz a lista de Produtos Cadastrados")
	@GetMapping
	public ResponseEntity<List<ProdutoDto>> buscarTodosProdutos() {
		List<ProdutoDto> produtosDto = produtoServico.obterTodosProdutos();
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}

	@GetMapping("/{id_produto}")
	@Operation(summary = "Retorna um Produto pelo id", description = "Dado um determinado número de id, será retornado um produto com suas informações gerais")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "Não foi encontrado um produto com esse id,por favor verifique!"),
		@ApiResponse(responseCode = "200", description = "Produto encontrado!") })
	public ResponseEntity<ProdutoDto> buscarProdutoPorId(@PathVariable Long id_produto) {
		Optional<ProdutoDto> produtoDto = produtoServico.obterProdutoPorId(id_produto);

		if (!produtoDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoDto.get());
	}

	@PutMapping("/{id_produto}")
	@Operation(summary = "Altera um Produto pelo id", description = "Dado um determinado número de id,é Possivel alterar tal produto , e suas informações")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "Não foi possivel alterar tal produto por esse id,por favor verifique!"),
		@ApiResponse(responseCode = "200", description = "Produto alterado!") })
	public ResponseEntity<ProdutoDto> modificarProduto(@PathVariable Long id_produto,
			@Valid @RequestBody ProdutoDto produtoDto) {
		Optional<ProdutoDto> produtoAlterado = produtoServico.alterarProduto(id_produto, produtoDto);
		if (!produtoAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoAlterado.get());
	}

	@DeleteMapping("/{id_produto}")
	@Operation(summary = "Deleta um Produto pelo id", description = "Dado um determinado número de id,é Possivel deletar tal produto , e suas informações")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "Não foi possivel deletar tal produto por esse id,por favor verifique!"),
		@ApiResponse(responseCode = "200", description = "Produto Deletado com sucesso!") })
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id_produto) {
		if (!produtoServico.apagarProduto(id_produto)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

//	{
//	  "nome": "Capinha Iphone 15",
//	  "descricao": "Uma capinha muito cara que faz o mesmo que todas as outras",
//	  "qnt_estoque": 50,
//	  "data_cadastro": "2024-10-18",
//	  "valor_unitario": 29.90,
//	  "imagem": "http://imagemdacapinha.jpg",
//	  "categoria": {
//	        "id_categoria": 1,
//	        "nome": "Capinhas",
//	        "descricao": "Capinhas para celulares"
//	    }
//	}

}
