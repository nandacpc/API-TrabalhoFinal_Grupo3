package org.serratec.shablau.controller;

import java.util.List;

import org.serratec.shablau.dto.ClienteDto;
import org.serratec.shablau.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
    private ClienteService clienteServico;

	@GetMapping
	public List<ClienteDto> obterTodos(){
		return clienteServico.obterTodos();
	}	
	
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDto cadastrarCliente(@RequestBody @Valid ClienteDto clienteDto) {
    	return clienteServico.salvarCliente(clienteDto);
    }
}

