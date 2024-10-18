package org.serratec.shablau.controller;

import org.serratec.shablau.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
    private CategoriaService categoriaServico;
	
	//RENAN FAZER OS @GET, @POST, @PUT E @DELETE
}
