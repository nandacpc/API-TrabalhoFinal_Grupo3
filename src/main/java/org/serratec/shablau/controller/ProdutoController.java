package org.serratec.shablau.controller;

import java.util.List;

import org.serratec.shablau.dto.ProdutoDto;
import org.serratec.shablau.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
    private ProdutoService produtoServico;
	
	@GetMapping
	public List<ProdutoDto> obterTodos() {
		return produtoServico.obterTodosProdutos();
	}
}
