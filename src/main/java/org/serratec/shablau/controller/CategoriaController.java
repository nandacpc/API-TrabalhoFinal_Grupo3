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

	@Operation(summary = "Cadastra Categoria", description = "Coleta informação da categoria, cadastrada e salva")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CategoriaDto> cadastrarCategoria(@Valid @RequestBody CategoriaDto categoriaDto) {
		return ResponseEntity.ok(categoriaServico.salvarCategoria(categoriaDto));
	}
	
	@Operation(summary = "Traz todas as Categorias Cadastradas", description = "Traz a lista de Categorias Cadastradas")
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
	@ApiResponses(value = {
	@ApiResponse(responseCode = "404", description = "Não foi encontrado uma categoria com esse id,por favor verifique!"),
	@ApiResponse(responseCode = "200", description = "Categoria encontrada!") })
	public ResponseEntity<CategoriaDto> buscarCategoriaPorId(@PathVariable Long id_categoria) {
		Optional<CategoriaDto> categoriaDto = categoriaServico.obterCategoriaPorId(id_categoria);

		if (!categoriaDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoriaDto.get());
	}

	@PutMapping("/{id_categoria}")
	@Operation(summary = "Altera uma categoria pelo id", description = "Dado um determinado número de id,é Possivel alterar tal categoria , e suas informações")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "404", description = "Não foi possivel alterar tal categoria por esse id,por favor verifique!"),
	@ApiResponse(responseCode = "200", description = "Categoria alterada!") })
	public ResponseEntity<CategoriaDto> modificarCategoria(@PathVariable Long id_categoria,
			@Valid @RequestBody CategoriaDto categoriaDto) {
		Optional<CategoriaDto> categoriaAlterada = categoriaServico.alterarCategoria(id_categoria, categoriaDto);
		if (!categoriaAlterada.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoriaAlterada.get());
	}

	@DeleteMapping("/{id_categoria}")
	@Operation(summary = "Deleta uma categoria pelo id", description = "Dado um determinado número de id,é Possivel deletar tal categoria , e suas informações")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "404", description = "Não foi possivel deletar tal categoria por esse id,por favor verifique!") })
	public ResponseEntity<String> deletarCategoria(@PathVariable Long id_categoria) {
		categoriaServico.apagarCategoria(id_categoria);
		return ResponseEntity.ok("A categoria com ID " + id_categoria + " foi apagado com sucesso.");
	}

}
