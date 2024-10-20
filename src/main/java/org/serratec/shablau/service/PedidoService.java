package org.serratec.shablau.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.ClienteDto;
import org.serratec.shablau.dto.ItemPedidoCadastroDto;
import org.serratec.shablau.dto.PedidoCadastroDto;
import org.serratec.shablau.dto.PedidoDto;
import org.serratec.shablau.dto.ProdutoDto;
import org.serratec.shablau.model.ItemPedido;
import org.serratec.shablau.model.Pedido;
import org.serratec.shablau.model.StatusEnum;
import org.serratec.shablau.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepositorio;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProdutoService produtoService;

	// CREATE
	
	public PedidoDto salvarPedido(PedidoCadastroDto pedidoCadastroDto) {
		ClienteDto cliente = clienteService.obterClientePorId(pedidoCadastroDto.id_cliente())
				.orElseThrow(() -> new RuntimeException("Cliente não encontrado. ID: " + pedidoCadastroDto.id_cliente()));
				
		Pedido novoPedido = new Pedido();
		novoPedido.setDataPedido(pedidoCadastroDto.dataPedido());
		novoPedido.setDataEnvio(pedidoCadastroDto.dataPedido().plusDays(3));
		novoPedido.setDataEntrega(pedidoCadastroDto.dataPedido().plusDays(10));
		novoPedido.setStatusPedido(pedidoCadastroDto.statusPedido());
		novoPedido.setCliente(cliente.toEntity());
		
		List<ItemPedido> itensPedido = new ArrayList<>();
		
		for (ItemPedidoCadastroDto itemDto : pedidoCadastroDto.itens()) {
	        ProdutoDto produto = produtoService.obterProdutoPorId(itemDto.id_produto())
	                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
		
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(itemDto.quantidade());
			itemPedido.setPercentual_desconto(itemDto.percentual_desconto());
			itemPedido.setProduto(produto.toEntity());
			itemPedido.setPreco_venda(produto.valor_unitario());
			itemPedido.setValor_bruto(produto.valor_unitario() * itemPedido.getQuantidade());
			itemPedido.setValor_liquido(itemPedido.getValor_bruto() - (itemPedido.getValor_bruto() * itemPedido.getPercentual_desconto()/100));
			itemPedido.setPedido(novoPedido);
			
			itensPedido.add(itemPedido);
		}		
		
		double valorTotal = itensPedido.stream()
		        .mapToDouble(ItemPedido::getValor_liquido)
		        .sum();
		novoPedido.setValorTotal(valorTotal);

		novoPedido.setItens(itensPedido);
		
		
		return PedidoDto.toDto(pedidoRepositorio.save(novoPedido));		
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
  
  public List<PedidoDto> obterPorStatus(StatusEnum status){
    List<Pedido> pedido = pedidoRepositorio.findByStatusPedido(status);
    return pedido.stream().map(p -> PedidoDto.toDto(p)).toList();
	}

	// UPDATE
	public Optional<PedidoDto> alterarPedido(Long id_pedido, PedidoDto pedidoDto) {
		if (!pedidoRepositorio.existsById(id_pedido)) {
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
