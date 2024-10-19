package org.serratec.shablau.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.ClienteCadastroDto;
import org.serratec.shablau.dto.ClienteDto;
import org.serratec.shablau.service.ClienteService;
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

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteServico;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDto cadastrarCliente(@RequestBody ClienteCadastroDto clienteCadastroDto) throws IOException, InterruptedException, URISyntaxException {
		return clienteServico.salvarCliente(clienteCadastroDto);
	}

	@GetMapping
	public List<ClienteDto> buscarTodosClientes() {
		return clienteServico.obterTodosClientes();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> buscarClientePorId(@PathVariable Long id_cliente) {
		Optional<ClienteDto> clienteDto = clienteServico.obterClientePorId(id_cliente);

		if (!clienteDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clienteDto.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteDto> modificarCliente(@PathVariable Long id_cliente,
			@RequestBody ClienteDto clienteDto) {
		Optional<ClienteDto> clienteAlterado = clienteServico.alterarCliente(id_cliente, clienteDto);
		if (!clienteAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clienteAlterado.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCliente(@PathVariable Long id_cliente) {
		if (!clienteServico.apagarCliente(id_cliente)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

}
