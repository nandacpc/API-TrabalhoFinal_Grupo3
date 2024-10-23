package org.serratec.shablau.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.CategoriaDto;
import org.serratec.shablau.service.CategoriaService;
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
@RequestMapping(path = "/categorias")
public class CategoriaController {
	@Autowired
	private CategoriaService categoriaServico;

	@Operation(summary = "Cadastra uma nova categoria", description = "Recebe as informações de uma categoria, realiza o cadastro no sistema e armazena os dados.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CategoriaDto> cadastrarCategoria(@Valid @RequestBody CategoriaDto categoriaDto) {
		return ResponseEntity.ok(categoriaServico.salvarCategoria(categoriaDto));
	}

	@Operation(summary = "Lista todas as categorias cadastradas", description = "Retorna uma lista com todas as categorias cadastradas.")
	@GetMapping
	public ResponseEntity<List<CategoriaDto>> buscarTodasCategorias() {
		List<CategoriaDto> categoriasDto = categoriaServico.obterTodasCategorias();
		if (categoriasDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(categoriasDto);
	}

	@GetMapping("/{id_categoria}")
	@Operation(summary = "Retorna uma categoria pelo id", description = "Dado um determinado número de id, será retornado uma categoria com suas informações gerais")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria encontrada!"),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<CategoriaDto> buscarCategoriaPorId(@PathVariable Long id_categoria) {
		Optional<CategoriaDto> categoriaDto = categoriaServico.obterCategoriaPorId(id_categoria);

		if (!categoriaDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoriaDto.get());
	}

	@GetMapping("/descricao/{palavra}")
	public ResponseEntity<List<CategoriaDto>> buscarCategoriaPorDescricao(@PathVariable String palavra) {
		List<CategoriaDto> categoriasDto = categoriaServico.obterCategoriaPorDescricao(palavra);
		if (categoriasDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(categoriasDto);
	}

	@Operation(summary = "Consulta categoria por nome", description = "Busca categoriaa pelo nome registrado.")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<CategoriaDto>> buscarCategoriaPorNome(@PathVariable String nome) {
		List<CategoriaDto> categoriasDto = categoriaServico.obterCategoriaPorNome(nome);
		if (categoriasDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(categoriasDto);
	}

	@PutMapping("/{id_categoria}")
	@Operation(summary = "Altera uma categoria pelo ID", description = "Atualiza os dados de uma categoria existente, com base no ID fornecido.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria alterada com sucesso!"),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<CategoriaDto> modificarCategoria(@PathVariable Long id_categoria,
			@Valid @RequestBody CategoriaDto categoriaDto) {
		Optional<CategoriaDto> categoriaAlterada = categoriaServico.alterarCategoria(id_categoria, categoriaDto);
		if (!categoriaAlterada.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoriaAlterada.get());
	}
	
	@DeleteMapping("/{id_categoria}")
	@Operation(summary = "Remove uma categoria pelo ID", description = "Exclui uma categoria específica e suas informações, com base no ID informado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria removida com sucesso!"),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<String> deletarCategoria(@PathVariable Long id_categoria) {
		categoriaServico.apagarCategoria(id_categoria);
		return ResponseEntity.ok("A categoria com ID " + id_categoria + " foi apagado com sucesso.");
	}

}