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

	@Operation(summary = "Cadastra pedido", description = "Coleta informação do pedido, cadastrado e salva")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<PedidoDto> cadastrarPedido(@Valid @RequestBody PedidoCadastroDto pedidoCadastroDto) {
		return ResponseEntity.ok(pedidoServico.salvarPedido(pedidoCadastroDto));
	}

	@Operation(summary = "Traz todos os pedidos cadastrados", description = "Traz a lista de pedidos cadastrados")
	@GetMapping
	public ResponseEntity<List<PedidoDto>> buscarTodosPedidos() {
		List<PedidoDto> pedidosDto = pedidoServico.obterTodosPedidos();
		if (pedidosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(pedidosDto);
	}

	@Operation(summary = "Traz o relatorio", description = "Traz relatorio pelo id do pedido")
	@GetMapping("/relatorio/{idPedido}")
	public String exibirRelatorio(@PathVariable Long idPedido) {
		return pedidoServico.gerarRelatorio(idPedido);
	}
	
	@GetMapping("/{id_pedido}")
	@Operation(summary = "Retorna um pedido pelo id", description = "Dado um determinado número de id, será retornado um pedido com suas informações gerais")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "Não foi encontrado um pedido com esse id, por favor verifique!"),
		@ApiResponse(responseCode = "200", description = "Pedido encontrado!") })
	public ResponseEntity<PedidoDto> buscarPedidoPorId(@PathVariable Long id_pedido) {
		Optional<PedidoDto> pedidoDto = pedidoServico.obterPedidoPorId(id_pedido);

		if (!pedidoDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pedidoDto.get());
	}

	@Operation(summary = "Buscar pedido pelo seu status.", description = "Buscar pedidos de acordo com seu status.")
	@GetMapping("/status/{status}")
		public ResponseEntity<List<PedidoDto>> buscarPedidoPorStatus(@PathVariable String status) {
		List<PedidoDto> pedidosDto = pedidoServico.obterPedidoPorStatus(StatusEnum.valueOf(status.toUpperCase()));
		if(pedidosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(pedidosDto);
	}	
	
	@Operation(summary = "Buscar pedido pelo seu status.", description = "Buscar pedidos de acordo com seu status.")
	@GetMapping("/datas/pedido/{dataPedido}")
	public ResponseEntity<List<PedidoDto>> buscarPedidoPorDataPedido(@PathVariable LocalDate dataPedido){
		List<PedidoDto> pedidosDto = pedidoServico.obterPedidoPorDataPedido(dataPedido);
		if(pedidosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(pedidosDto);
	} 
	
	@Operation(summary = "Buscar pedido por data de entrega.", description = "Busca pedidos por com mesma data de entrega.")
	@GetMapping("/datas/entrega/{dataEntrega}")
	public ResponseEntity<List<PedidoDto>> buscarPedidoPorDataEntrega(@PathVariable LocalDate dataEntrega){
		List<PedidoDto> pedidosDto = pedidoServico.obterPedidoPorDataEntrega(dataEntrega);
		if(pedidosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(pedidosDto);
	}	
	
	@Operation(summary = "Buscar pedido por data de entrega.", description = "Busca pedidos por com mesma data de entrega.")
	@GetMapping("/datas/envio/{dataEnvio}")
	public ResponseEntity<List<PedidoDto>> buscarPedidoPorDataEnvio(@PathVariable LocalDate dataEnvio){
		List<PedidoDto> pedidosDto = pedidoServico.obterPedidoPorDataEnvio(dataEnvio);
		if(pedidosDto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(pedidosDto);
	}	
	
	@PutMapping("/{id_pedido}")
	@Operation(summary = "Altera um pedido pelo id", description = "Dado um determinado número de id, é possivel alterar um pedido com suas informações gerais")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "Não foi possivel alterar tal pedido com esse id, por favor verifique!"),
		@ApiResponse(responseCode = "200", description = "Pedido alterado!") })
	public ResponseEntity<PedidoDto> modificarPedido(@PathVariable Long id_pedido, @Valid @RequestBody PedidoCadastroDto pedidoDto) {
		Optional<PedidoDto> pedidoAlterado = pedidoServico.alterarDadosPedido(id_pedido, pedidoDto);
		if (!pedidoAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pedidoAlterado.get());
	}

	@DeleteMapping("/{id_pedido}")
	@Operation(summary = "Deleta um pedido pelo id", description = "Dado um determinado número de id, é possivel deletar tal pedido e suas informações")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "Não foi possivel deletar tal pedido por esse id, por favor verifique!"),
		@ApiResponse(responseCode = "200", description = "Pedido deletado com sucesso!") })
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
