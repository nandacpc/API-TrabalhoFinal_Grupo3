package org.serratec.shablau.controller;

import org.serratec.shablau.model.Pedido;
import org.serratec.shablau.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PedidoService {
	@Autowired
	PedidoRepository repositorio;
	
	public Pedido salvarPedido(Pedido pedido) {
		return repositorio.save(pedido);
	}
}
