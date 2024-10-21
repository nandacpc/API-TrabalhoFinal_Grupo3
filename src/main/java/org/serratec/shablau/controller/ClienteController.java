package org.serratec.shablau.controller;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteServico;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ClienteDto> cadastrarCliente(@Valid @RequestBody ClienteCadastroDto clienteCadastroDto) throws Exception {
		return ResponseEntity.ok(clienteServico.salvarCliente(clienteCadastroDto));
	}

	@GetMapping
	public ResponseEntity<List<ClienteDto>> buscarTodosClientes() {
		List<ClienteDto> clientesDto = clienteServico.obterTodosClientes();

		if (clientesDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(clientesDto);
	}

	@GetMapping("/{id_cliente}")
	public ResponseEntity<ClienteDto> buscarClientePorId(@PathVariable Long id_cliente) {
		Optional<ClienteDto> clienteDto = clienteServico.obterClientePorId(id_cliente);

		if (!clienteDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clienteDto.get());
	}

	@PutMapping("/{id_cliente}")
	public ResponseEntity<ClienteDto> modificarCliente(@PathVariable Long id_cliente,
			@Valid @RequestBody ClienteDto clienteDto) {
		Optional<ClienteDto> clienteAlterado = clienteServico.alterarCliente(id_cliente, clienteDto);
		if (!clienteAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clienteAlterado.get());
	}

	@DeleteMapping("/{id_cliente}")
	public ResponseEntity<String> deletarCliente(@PathVariable Long id_cliente) {
		clienteServico.apagarCliente(id_cliente);
		return ResponseEntity.ok("O cliente com ID " + id_cliente + " foi excluído com sucesso.");
	}

}
