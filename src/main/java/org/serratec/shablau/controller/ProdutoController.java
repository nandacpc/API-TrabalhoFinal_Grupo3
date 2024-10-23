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

	@PostMapping
	@Operation(summary = "Cadastra um novo produto", description = "Recebe as informações do produto, realiza o cadastro no sistema e armazena os dados.")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ProdutoDto> cadastrarProduto(@Valid @RequestBody ProdutoCadastroDto produtoCadastroDto) {
		return ResponseEntity.ok(produtoServico.salvarProduto(produtoCadastroDto));
	}

	@GetMapping
	@Operation(summary = "Lista todos os produtos cadastrados", description = "Retorna uma lista com todos os produtos cadastrados no sistema.")
	public ResponseEntity<List<ProdutoDto>> buscarTodosProdutos() {
		List<ProdutoDto> produtosDto = produtoServico.obterTodosProdutos();
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}

	@GetMapping("/{id_produto}")
	@Operation(summary = "Retorna um produto pelo ID", description = "Retorna as informações detalhadas de um produto específico, com base no ID fornecido.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Produto encontrado!"),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<ProdutoDto> buscarProdutoPorId(@PathVariable Long id_produto) {
		Optional<ProdutoDto> produtoDto = produtoServico.obterProdutoPorId(id_produto);

		if (!produtoDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoDto.get());
	}

	@Operation(summary = "Buscar produtos pelo nome", description = "Busca produtos que correspondem ao nome informado.")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<ProdutoDto>> buscarProdutoPorNome(@PathVariable String nome) {
		List<ProdutoDto> produtosDto = produtoServico.obterProdutoPorNome(nome);
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}

	@Operation(summary = "Buscar produtos por intervalo de datas", description = "Busca produtos com base na data de criação ou atualização entre as datas informadas.")
	@GetMapping("/data/{data_inicio}/{data_final}")
	public ResponseEntity<List<ProdutoDto>> buscarProdutoPorIntervaloData(@PathVariable LocalDate data_inicio,
			@PathVariable LocalDate data_final) {
		List<ProdutoDto> produtosDto = produtoServico.obterProdutoPorIntervaloData(data_inicio, data_final);
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}

	@Operation(summary = "Buscar produtos por intervalo de quantidade em estoque", description = "Busca produtos cujo estoque esteja entre os valores mínimo e máximo especificados.")
	@GetMapping("/estoque/{min}/{max}")
	public ResponseEntity<List<ProdutoDto>> buscarProdutoPorIntervaloEstoque(@PathVariable int min,
			@PathVariable int max) {
		List<ProdutoDto> produtosDto = produtoServico.obterProdutoPorIntervaloEstoque(min, max);
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}

	@Operation(summary = "Buscar produtos por intervalo de preço", description = "Busca produtos cujo preço esteja entre os valores mínimo e máximo especificados.")
	@GetMapping("/preco/{valor_min}/{valor_max}")
	public ResponseEntity<List<ProdutoDto>> buscarProdutoPorIntervaloValor(@PathVariable int valor_min,
			@PathVariable int valor_max) {
		List<ProdutoDto> produtosDto = produtoServico.obterProdutoPorIntervaloValor(valor_min, valor_max);
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}

	@Operation(summary = "Buscar produtos por categoria", description = "Busca produtos com base no ID da categoria informada.")
	@GetMapping("/categoria/{id_categoria}")
	public ResponseEntity<List<ProdutoDto>> buscarProdutoPorCategoria(@PathVariable Long id_categoria) {
		List<ProdutoDto> produtosDto = produtoServico.obterProdutoPorCategoria(id_categoria);
		if (produtosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(produtosDto);
	}

	@PutMapping("/{id_produto}")
	@Operation(summary = "Altera um produto pelo id", description = "Atualiza os dados de um produto existente, com base no ID fornecido.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Produto  alterado com sucesso!"),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<ProdutoDto> modificarProduto(@PathVariable Long id_produto,
			@Valid @RequestBody ProdutoCadastroDto produtoCadastroDto) {
		Optional<ProdutoDto> produtoAlterado = produtoServico.alterarProduto(id_produto, produtoCadastroDto);
		if (!produtoAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoAlterado.get());
	}

	@DeleteMapping("/{id_produto}")
	@Operation(summary = "Remove um produto pelo ID", description = "Exclui um produto específico e suas informações, com base no ID informado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Produto removido com sucesso!"),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id_produto) {
		if (!produtoServico.apagarProduto(id_produto)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

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
