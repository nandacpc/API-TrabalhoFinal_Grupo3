package org.serratec.shablau.service;

import org.serratec.shablau.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
	@Autowired
	ProdutoRepository repositorio;
}
