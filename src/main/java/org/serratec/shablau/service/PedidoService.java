package org.serratec.shablau.service;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.PedidoDto;
import org.serratec.shablau.model.Pedido;
import org.serratec.shablau.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepositorio;

		// CREATE
	public PedidoDto salvarPedido(PedidoDto pedidoDto) {
		return PedidoDto.toDto(pedidoRepositorio.save(pedidoDto.toEntity()));
	}

		// READ
	public List<PedidoDto> obterTodosPedidos() {
		return pedidoRepositorio.findAll().stream().map(p -> PedidoDto.toDto(p)).toList();
	}

	public Optional<PedidoDto> obterPedidoPorId(Long id) {
		if (!pedidoRepositorio.existsById(id)) {
			return Optional.empty();
		}
		return Optional.of(PedidoDto.toDto(pedidoRepositorio.findById(id).get()));
	}
	
		//UPDATE
	public Optional<PedidoDto> alterarPedido(Long id_pedido, PedidoDto pedidoDto){
		if(!pedidoRepositorio.existsById(id_pedido)) {
			return Optional.empty();
		}
		Pedido pedidoEntity = pedidoDto.toEntity();
		pedidoEntity.setId_pedido(id_pedido);
		pedidoRepositorio.save(pedidoEntity);
		return Optional.of(PedidoDto.toDto(pedidoEntity));
	}

		// DELETE
	public boolean apagarPedido(Long id_pedido) {
		if (!pedidoRepositorio.existsById(id_pedido)) {
			return false;
		}
		pedidoRepositorio.deleteById(id_pedido);
		return true;
	}

}
