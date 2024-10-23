package org.serratec.shablau.controller;

import java.time.LocalDate;
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

	@Operation(summary = "Cadastra produto", description = "Coleta informações do produto e salva")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<ProdutoDto> cadastrarProduto(@Valid @RequestBody ProdutoCadastroDto produtoCadastroDto) {
		return ResponseEntity.ok(produtoServico.salvarProduto(produtoCadastroDto));
	}

	@Operation(summary = "Traz todos os produtos cadastrados", 
			description = "Traz a lista de produtos cadastrados")
	@GetMapping
	public ResponseEntity<List<ProdutoDto>> buscarTodosProdutos() {
		List<ProdutoDto> produtosDto = produtoServico.obterTodosProdutos();
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}

	@GetMapping("/{id_produto}")
	@Operation(summary = "Retorna um produto pelo id", 
	description = "Dado um determinado número de id, será retornado um produto com suas informações gerais")
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
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<ProdutoDto>> buscarProdutoPorNome(@PathVariable String nome) {
		List<ProdutoDto> produtosDto = produtoServico.obterProdutoPorNome(nome);
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}
	
	@GetMapping("/data/{data_inicio}/{data_final}")
	public ResponseEntity<List<ProdutoDto>> buscarProdutoPorIntervaloData(@PathVariable LocalDate data_inicio, @PathVariable LocalDate data_final) {
		List<ProdutoDto> produtosDto = produtoServico.obterProdutoPorIntervaloData(data_inicio, data_final);
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}	
	
	@GetMapping("/estoque/{min}/{max}")
	public ResponseEntity<List<ProdutoDto>> buscarProdutoPorIntervaloEstoque(@PathVariable int min, @PathVariable int max) {
		List<ProdutoDto> produtosDto = produtoServico.obterProdutoPorIntervaloEstoque(min, max);
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}
	
	@GetMapping("/preco/{valor_min}/{valor_max}")
	public ResponseEntity<List<ProdutoDto>> buscarProdutoPorIntervaloValor(@PathVariable int valor_min, @PathVariable int valor_max) {
		List<ProdutoDto> produtosDto = produtoServico.obterProdutoPorIntervaloValor(valor_min, valor_max);
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}
	
	@GetMapping("/categoria/{id_categoria}")
	public ResponseEntity<List<ProdutoDto>> buscarProdutoPorCategoria(@PathVariable Long id_categoria) {
		List<ProdutoDto> produtosDto = produtoServico.obterProdutoPorCategoria(id_categoria);
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}

	@PutMapping("/{id_produto}")
	@Operation(summary = "Altera um produto pelo id", description = "Dado um determinado número de id, é possivel alterar o produto e suas informações")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "Não foi possivel alterar tal produto por esse id, por favor verifique!"),
		@ApiResponse(responseCode = "200", description = "Produto alterado!") })
	public ResponseEntity<ProdutoDto> modificarProduto(@PathVariable Long id_produto,
			@Valid @RequestBody ProdutoCadastroDto produtoCadastroDto) {
		Optional<ProdutoDto> produtoAlterado = produtoServico.alterarProduto(id_produto, produtoCadastroDto);
		if (!produtoAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoAlterado.get());
	}

	@DeleteMapping("/{id_produto}")
	@Operation(summary = "Deleta um produto pelo id", description = "Dado um determinado número de id, é possivel deletar o produto e suas informações")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "404", description = "Não foi possivel deletar tal produto por esse id, por favor verifique!"),
	@ApiResponse(responseCode = "200", description = "Produto deletado com sucesso!") })
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id_produto) {
		if (!produtoServico.apagarProduto(id_produto)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

//ESTRUTURA DE POST E PUT
//	{
//	  "nome": "Capinha Iphone 15",
//	  "descricao": "Uma capinha muito cara que faz o mesmo que todas as outras",
//	  "qntEstoque": 50,
//	  "dataCadastro": "2024-10-18",
//	  "valorUnitario": 29.90,
//	  "imagem": "http://imagemdacapinha.jpg",
//	   "idCategoria": 1
//	}

}
