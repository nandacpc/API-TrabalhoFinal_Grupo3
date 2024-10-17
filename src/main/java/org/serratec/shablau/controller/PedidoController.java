package org.serratec.shablau.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.PedidoDto;
import org.serratec.shablau.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path = "/pedidos")
public class PedidoController {
	@Autowired
	private PedidoService pedidoServico;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDto cadastrarPedido(@RequestBody PedidoDto pedidoDto) {
		return pedidoServico.salvarPedido(pedidoDto);
	}

	@GetMapping
	public List<PedidoDto> buscarTodosPedidos() {
		return pedidoServico.obterTodosPedidos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PedidoDto> buscarPedidoPorId(@PathVariable Long id_pedido) {
		Optional<PedidoDto> pedidoDto = pedidoServico.obterPedidoPorId(id_pedido);

		if (!pedidoDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pedidoDto.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<PedidoDto> modificarPedido(@PathVariable Long id_pedido, @RequestBody PedidoDto pedidoDto) {
		Optional<PedidoDto> pedidoAlterado = pedidoServico.alterarPedido(id_pedido, pedidoDto);
		if (!pedidoAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pedidoAlterado.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarPedido(@PathVariable Long id_pedido) {
		if (!pedidoServico.apagarPedido(id_pedido)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

}
