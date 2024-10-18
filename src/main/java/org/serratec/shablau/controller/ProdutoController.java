package org.serratec.shablau.controller;

import org.serratec.shablau.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	@Autowired
    private ProdutoService produtoServico;
	
	//RENAN FAZER OS @GET, @POST, @PUT E @DELETE
}
