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

	@Operation(summary = "Cadastra um novo cliente", description = "Recebe as informações do cliente, realiza o cadastro no sistema e armazena os dados.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ClienteDto> cadastrarCliente(@Valid @RequestBody ClienteCadastroDto clienteCadastroDto)
			throws Exception {
		return ResponseEntity.ok(clienteServico.salvarCliente(clienteCadastroDto));
	}

	@Operation(summary = "Lista todos os clientes cadastrados", description = "Retorna uma lista com todos os clientes registrados no sistema.")
	@GetMapping
	public ResponseEntity<List<ClienteDto>> buscarTodosClientes() {
		List<ClienteDto> clientesDto = clienteServico.obterTodosClientes();
		if (clientesDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(clientesDto);
	}

	@GetMapping("/{id_cliente}")
	@Operation(summary = "Consulta um cliente pelo ID", description = "Retorna as informações detalhadas de um cliente específico, com base no ID fornecido.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado!"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<ClienteDto> buscarClientePorId(@PathVariable Long id_cliente) {
		Optional<ClienteDto> clienteDto = clienteServico.obterClientePorId(id_cliente);
		if (!clienteDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clienteDto.get());
	}

	@Operation(summary = "Consulta um cliente por nome", description = "Retorna um cliente de acordo com o nome registrado.")
	@GetMapping("/{nome}")
	public ResponseEntity<List<ClienteDto>> buscarClientePorNome(@PathVariable String nome) {
		List<ClienteDto> clientesDto = clienteServico.obterClientePorNome(nome);
		if (!clientesDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(clientesDto);
	}

	@Operation(summary = "Consulta um cliente por CPF", description = "Retorna um cliente de acordo com o CPF previamente registrado.")
	@GetMapping("/{cpf}")
	public ResponseEntity<ClienteDto> buscarClientePorCpf(@PathVariable String cpf) {
		Optional<ClienteDto> clienteDto = clienteServico.obterClientePorCpf(cpf);
		if (!clienteDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clienteDto.get());
	}

	@PutMapping("/{id_cliente}")
	@Operation(summary = "Altera um cliente pelo ID", description = "Atualiza os dados de um cliente existente, com base no ID fornecido.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente alterado com sucesso!"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<ClienteDto> modificarCliente(@PathVariable Long id_cliente,
			@Valid @RequestBody ClienteCadastroDto clienteDto) {

		Optional<ClienteDto> clienteAlterado = clienteServico.alterarCliente(id_cliente, clienteDto);
		if (!clienteAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clienteAlterado.get());
	}

	@Operation(summary = "Remove um cliente pelo ID", description = "Exclui um cliente específico e suas informações, com base no ID informado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente removido com sucesso!"),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@DeleteMapping("/{id_cliente}")
	public ResponseEntity<String> deletarCliente(@PathVariable Long id_cliente) {
		clienteServico.apagarCliente(id_cliente);
		return ResponseEntity.ok("O cliente com ID " + id_cliente + " foi excluído com sucesso.");
	}

}
