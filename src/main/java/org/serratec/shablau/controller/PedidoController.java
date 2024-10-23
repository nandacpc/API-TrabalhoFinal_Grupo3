package org.serratec.shablau.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.PedidoCadastroDto;
import org.serratec.shablau.dto.PedidoDto;
import org.serratec.shablau.model.StatusEnum;
import org.serratec.shablau.service.PedidoService;
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
@RequestMapping(path = "/pedidos")
public class PedidoController {
	@Autowired
	private PedidoService pedidoServico;

	@Operation(summary = "Cadastra um novo pedido", description = "Recebe as informações do pedido, realiza o cadastro no sistema e armazena os dados.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<PedidoDto> cadastrarPedido(@Valid @RequestBody PedidoCadastroDto pedidoCadastroDto) {
		return ResponseEntity.ok(pedidoServico.salvarPedido(pedidoCadastroDto));
	}

	@Operation(summary = "Lista todos os pedidos cadastrados", description = "Retorna uma lista com todos os pedidos registrados no sistema.")
	@GetMapping
	public ResponseEntity<List<PedidoDto>> buscarTodosPedidos() {
		List<PedidoDto> pedidosDto = pedidoServico.obterTodosPedidos();
		if (pedidosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(pedidosDto);
	}

	@Operation(summary = "Gera o relatório de um pedido", description = "Gera e exibe um relatório detalhado com as informações do pedido, baseado no ID fornecido.")
	@GetMapping("/relatorio/{idPedido}")
	public String exibirRelatorio(@PathVariable Long idPedido) {
		return pedidoServico.gerarRelatorio(idPedido);
	}

	@GetMapping("/{id_pedido}")
	@Operation(summary = "Consulta um pedido pelo ID", description = "Retorna as informações detalhadas de um pedido específico, com base no ID fornecido.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedido encontrado!"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<PedidoDto> buscarPedidoPorId(@PathVariable Long id_pedido) {
		Optional<PedidoDto> pedidoDto = pedidoServico.obterPedidoPorId(id_pedido);

		if (!pedidoDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pedidoDto.get());
	}

	@Operation(summary = "Consulta pedidos pelo status", description = "Busca e retorna uma lista de pedidos filtrados pelo status informado.")
	@GetMapping("/status/{status}")
	public ResponseEntity<List<PedidoDto>> buscarPedidoPorStatus(@PathVariable String status) {
		List<PedidoDto> pedidosDto = pedidoServico.obterPedidoPorStatus(StatusEnum.valueOf(status.toUpperCase()));
		if (pedidosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(pedidosDto);
	}

	@Operation(summary = "Consulta pedidos pela data de criação", description = "Retorna uma lista de pedidos feitos na data especificada.")
	@GetMapping("/datas/pedidos/{dataPedido}")
	public ResponseEntity<List<PedidoDto>> buscarPedidoPorDataPedido(@PathVariable LocalDate dataPedido) {
		List<PedidoDto> pedidosDto = pedidoServico.obterPedidoPorDataPedido(dataPedido);
		if (pedidosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(pedidosDto);
	}

	@Operation(summary = "Consulta pedidos pela data de entrega", description = "Busca pedidos que possuem a mesma data de entrega.")
	@GetMapping("/datas/entrega/{dataEntrega}")
	public ResponseEntity<List<PedidoDto>> buscarPedidoPorDataEntrega(@PathVariable LocalDate dataEntrega) {
		List<PedidoDto> pedidosDto = pedidoServico.obterPedidoPorDataEntrega(dataEntrega);
		if (pedidosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(pedidosDto);
	}

	@Operation(summary = "Consulta pedidos pela data de envio", description = "Retorna pedidos de acordo com a data em que foram enviados.")
	@GetMapping("/datas/envio/{dataEnvio}")
	public ResponseEntity<List<PedidoDto>> buscarPedidoPorDataEnvio(@PathVariable LocalDate dataEnvio) {
		List<PedidoDto> pedidosDto = pedidoServico.obterPedidoPorDataEnvio(dataEnvio);
		if (pedidosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(pedidosDto);
	}

	@PutMapping("/{id_pedido}")
	@Operation(summary = "Altera um pedido pelo ID", description = "Atualiza os dados de um pedido existente, com base no ID fornecido.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedido alterado com sucesso!"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<PedidoDto> modificarPedido(@PathVariable Long id_pedido,
			@Valid @RequestBody PedidoCadastroDto pedidoDto) {
		Optional<PedidoDto> pedidoAlterado = pedidoServico.alterarDadosPedido(id_pedido, pedidoDto);
		if (!pedidoAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pedidoAlterado.get());
	}

	@DeleteMapping("/{id_pedido}")
	@Operation(summary = "Remove um pedido pelo ID", description = "Exclui um pedido específico e suas informações, com base no ID informado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedido removido com sucesso!"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado. Verifique o ID ou outros parâmetros informados."),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os parâmetros fornecidos estão corretos e no formato esperado."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	public ResponseEntity<Void> deletarPedido(@PathVariable Long id_pedido) {
		if (!pedidoServico.apagarPedido(id_pedido)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

	/*
	 * EXEMPLO { "data_pedido": "2024-10-16", "data_entrega": "2024-10-20",
	 * "data_envio": "2024-10-17", "status_pedido": "PROCESSANDO", "valor_total":
	 * 150.0, "cliente": { "email": "cliente@email.com", "nome_completo":
	 * "João Silva", "cpf": "12345678900", "telefone": "11999999999",
	 * "data_nascimento": "1990-01-01", "cep": "01001000", "numero": 123,
	 * "complemento": "Apto 45" } }
	 */

}
