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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteServico;

	@Operation(summary = "Cadastra Clientes", description = "Coleta informação do cliente,cadastrado e salva")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ClienteDto> cadastrarCliente(@Valid @RequestBody ClienteCadastroDto clienteCadastroDto) {
		return ResponseEntity.ok(clienteServico.salvarCliente(clienteCadastroDto));
	}

	@Operation(summary = "Traz todos os Clientes Cadastrados", description = "Traz a lista de Clientes Cadastrados")
	@GetMapping
	public ResponseEntity<List<ClienteDto>> buscarTodosClientes() {
		List<ClienteDto> clientesDto = clienteServico.obterTodosClientes();

		if (clientesDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(clientesDto);
	}

	@GetMapping("/{id_cliente}")
	@Operation(summary = "Retorna um cliente pelo id", description = "Dado um determinado número de id, será retornado um cliente com suas informações gerais")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Não foi encontrado um cliente com esse id,por favor verifique!"),
			@ApiResponse(responseCode = "200", description = "Cliente encontrado!") })
	public ResponseEntity<ClienteDto> buscarClientePorId(@PathVariable Long id_cliente) {
		Optional<ClienteDto> clienteDto = clienteServico.obterClientePorId(id_cliente);

		if (!clienteDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clienteDto.get());
	}

	@PutMapping("/{id_cliente}")
	@Operation(summary = "Altera cliente pelo id", description = "Com um determinado número de id,é possivel mudar as informações do cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "404", description = "Não foi Possivel alterar tal informação do cliente por esse id,por favor verifique!"),
			@ApiResponse(responseCode = "200", description = "Informação do Cliente foi alterado com sucesso!") })
	public ResponseEntity<ClienteDto> modificarCliente(@PathVariable Long id_cliente,
			@Valid @RequestBody ClienteDto clienteDto) {
		Optional<ClienteDto> clienteAlterado = clienteServico.alterarCliente(id_cliente, clienteDto);
		if (!clienteAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clienteAlterado.get());
	}

	@Operation(summary = "Deleta Cliente pelo id", description = "Com um Determinado número de id,é possivel deletar tal cliente e suas informações")
	@DeleteMapping("/{id_cliente}")
	public ResponseEntity<String> deletarCliente(@PathVariable Long id_cliente) {
		clienteServico.apagarCliente(id_cliente);
		return ResponseEntity.ok("Cliente com ID " + id_cliente + "foi apagado com sucesso.");
	}

}
