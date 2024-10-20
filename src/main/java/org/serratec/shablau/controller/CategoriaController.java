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

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/categorias")
public class CategoriaController {
	@Autowired
    private CategoriaService categoriaServico;

  
  @GetMapping
	public List<CategoriaDto> buscarTodasCategorias() {
		return categoriaServico.obterTodasCategorias();
	}
  
  @GetMapping("/{id}")
	public ResponseEntity<CategoriaDto> buscarCategoriaPorId(@PathVariable Long id_categoria) {
		Optional<CategoriaDto> categoriaDto = categoriaServico.obterCategoriaPorId(id_categoria);

		if (!categoriaDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoriaDto.get());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaDto cadastrarCategoria(@Valid @RequestBody CategoriaDto categoriaDto) {
		return categoriaServico.salvarCategoria(categoriaDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDto> modificarCategoria(@Valid @PathVariable Long id_categoria,
			@RequestBody CategoriaDto categoriaDto) {
		Optional<CategoriaDto> categoriaAlterada = categoriaServico.alterarCategoria(id_categoria, categoriaDto);
		if (!categoriaAlterada.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoriaAlterada.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Long id_categoria) {
		if (!categoriaServico.apagarCategoria(id_categoria)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

}
