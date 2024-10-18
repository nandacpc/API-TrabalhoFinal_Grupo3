package org.serratec.shablau.controller;

import java.util.List;

import org.serratec.shablau.dto.CategoriaDto;
import org.serratec.shablau.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
    private CategoriaService categoriaServico;
	
	@GetMapping
	public List<CategoriaDto> obterTodos() {
		return categoriaServico.obterTodasCategorias();
	}
}
