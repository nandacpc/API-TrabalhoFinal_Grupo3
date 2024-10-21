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
@RequestMapping(path = "/shablau/categorias")
public class CategoriaController {
	@Autowired
    private CategoriaService categoriaServico;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CategoriaDto> cadastrarCategoria(@Valid @RequestBody CategoriaDto categoriaDto) {
		return ResponseEntity.ok(categoriaServico.salvarCategoria(categoriaDto));
	}
	
  @GetMapping
	public ResponseEntity<List<CategoriaDto>> buscarTodasCategorias() {
	  List<CategoriaDto> categoriasDto = categoriaServico.obterTodasCategorias();
	  if(categoriasDto.isEmpty()) {
		  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	  }
		return ResponseEntity.ok(categoriasDto);
	}
  
  @GetMapping("/{id_categoria}")
	public ResponseEntity<CategoriaDto> buscarCategoriaPorId(@PathVariable Long id_categoria) {
		Optional<CategoriaDto> categoriaDto = categoriaServico.obterCategoriaPorId(id_categoria);

		if (!categoriaDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoriaDto.get());
	}

	@PutMapping("/{id_categoria}")
	public ResponseEntity<CategoriaDto> modificarCategoria(@PathVariable Long id_categoria, @Valid @RequestBody CategoriaDto categoriaDto) {
		Optional<CategoriaDto> categoriaAlterada = categoriaServico.alterarCategoria(id_categoria, categoriaDto);
		if (!categoriaAlterada.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoriaAlterada.get());
	}

	@DeleteMapping("/{id_categoria}") 
	public ResponseEntity<String> deletarCategoria(@PathVariable Long id_categoria) {
		categoriaServico.apagarCategoria(id_categoria);
		return ResponseEntity.ok("A categoria com ID " + id_categoria + " foi apagado com sucesso.");
	}

}
