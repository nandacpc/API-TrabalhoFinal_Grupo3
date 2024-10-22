package org.serratec.shablau.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.shablau.config.ResourceNotFoundException;
import org.serratec.shablau.dto.ClienteDto;
import org.serratec.shablau.dto.ItemPedidoCadastroDto;
import org.serratec.shablau.dto.ItemPedidoRelatorioDto;
import org.serratec.shablau.dto.PedidoCadastroDto;
import org.serratec.shablau.dto.PedidoDto;
import org.serratec.shablau.dto.PedidoRelatorioDto;
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
	
	@Autowired
	private EmailService emailService;

	// CREATE
	public PedidoDto salvarPedido(PedidoCadastroDto pedidoCadastroDto) {
		ClienteDto cliente = clienteService.obterClientePorId(pedidoCadastroDto.idCliente())
				.orElseThrow(() -> new RuntimeException("Cliente não encontrado. ID: " + pedidoCadastroDto.idCliente()));
				
		Pedido novoPedido = new Pedido();
		novoPedido.setDataPedido(pedidoCadastroDto.dataPedido());
		novoPedido.setDataEnvio(pedidoCadastroDto.dataPedido().plusDays(3));
		novoPedido.setDataEntrega(pedidoCadastroDto.dataPedido().plusDays(10));
		novoPedido.setStatusPedido(pedidoCadastroDto.statusPedido());
		novoPedido.setCliente(cliente.toEntity());
		
		List<ItemPedido> itensPedido = new ArrayList<>();
		
		for (ItemPedidoCadastroDto itemDto : pedidoCadastroDto.itens()) {
	        ProdutoDto produto = produtoService.obterProdutoPorId(itemDto.idProduto())
	                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
		
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(itemDto.quantidade());
			itemPedido.setPercentual_desconto(itemDto.percentualDesconto());
			itemPedido.setProduto(produto.toEntity());
			itemPedido.setPrecoVenda(produto.valorUnitario());
			itemPedido.setValorBruto(produto.valorUnitario() * itemPedido.getQuantidade());
			itemPedido.setValor_liquido(itemPedido.getValorBruto() - (itemPedido.getValorBruto() * itemPedido.getPercentualDesconto()/100));
			itemPedido.setPedido(novoPedido);
			
			itensPedido.add(itemPedido);
		}		
		
		double valorTotal = itensPedido.stream()
		        .mapToDouble(i -> i.getValorLiquido())
		        .sum();
		novoPedido.setValorTotal(valorTotal);

		novoPedido.setItens(itensPedido);
		
		pedidoRepositorio.save(novoPedido);
		String relatorio = gerarRelatorio(novoPedido.getIdPedido());
		
		emailService.enviarEmail(cliente.email(), "Novo pedido gerado", relatorio);
		
		return PedidoDto.toDto(novoPedido);		
	}
	public String gerarRelatorio(Long idPedido) { //retornar string
		List<ItemPedidoRelatorioDto> itensRelatorio = pedidoRepositorio.findItensByPedidoId(idPedido);
		Pedido pedido = pedidoRepositorio.findById(idPedido).get();
		PedidoRelatorioDto relatorio = new PedidoRelatorioDto(pedido.getIdPedido(), pedido.getDataPedido(), pedido.getValorTotal(), itensRelatorio);
		return relatorio.toString();
	}

	
	
//public static String gerarHtmlRelatorio(RelatorioPedidoDTO relatorio) {
//        
//		StringBuilder html = new StringBuilder();
//
//        // Cabeçalho do HTML
//        html.append("<html><head><title>Relatório de Pedido</title></head><body>");
//
//        // Título do relatório
//        html.append("<h1>Relatório de Pedido</h1>");

	// READ
	public List<PedidoDto> obterTodosPedidos() {
		return pedidoRepositorio.findAll().stream().map(p -> PedidoDto.toDto(p)).toList();
	}

	public Optional<PedidoDto> obterPedidoPorId(Long id) {
		if (!pedidoRepositorio.existsById(id)) {
			throw new ResourceNotFoundException("Pedido com ID " + id + " não encontrado.");
		}
		return Optional.of(PedidoDto.toDto(pedidoRepositorio.findById(id).get()));
	}

	// DERIVED QUERIES
	public List<PedidoDto> obterPorStatus(StatusEnum status) {
		List<Pedido> pedido = pedidoRepositorio.findByStatusPedido(status);
		return pedido.stream().map(p -> PedidoDto.toDto(p)).toList();
	}

	// UPDATE
<<<<<<< HEAD
	public Optional<PedidoDto> alterarDadosPedido(Long id_pedido, PedidoCadastroDto pedidoCadastroDto) {
		if (!pedidoRepositorio.existsById(id_pedido)) {
			return Optional.empty();
		}
		Pedido pedidoEntity = pedidoRepositorio.findById(id_pedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
		ClienteDto cliente = clienteService.obterClientePorId(pedidoCadastroDto.idCliente())
				.orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
		
		pedidoEntity.setDataPedido(pedidoCadastroDto.dataPedido());
		pedidoEntity.setStatusPedido(pedidoCadastroDto.statusPedido());
		pedidoEntity.setCliente(cliente.toEntity());
		
		List<ItemPedido> listaItens = new ArrayList<ItemPedido>();
		for (ItemPedidoCadastroDto itemDto : pedidoCadastroDto.itens()) {
	        ProdutoDto produto = produtoService.obterProdutoPorId(itemDto.idProduto())
	                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
	        
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setQuantidade(itemDto.quantidade());
			itemPedido.setPercentual_desconto(itemDto.percentualDesconto());
			itemPedido.setProduto(produto.toEntity());
			itemPedido.setPrecoVenda(produto.valorUnitario());
			itemPedido.setValorBruto(produto.valorUnitario() * itemPedido.getQuantidade());
			itemPedido.setValor_liquido(itemPedido.getValorBruto() - (itemPedido.getValorBruto() * itemPedido.getPercentualDesconto()/100));
			itemPedido.setPedido(pedidoEntity);
=======
		public Optional<PedidoDto> alterarDadosPedido(Long id_pedido, PedidoCadastroDto pedidoCadastroDto) {
			if (!pedidoRepositorio.existsById(id_pedido)) {
				return Optional.empty();
			}
			Pedido pedidoEntity = pedidoRepositorio.findById(id_pedido)
	                .orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
			ClienteDto cliente = clienteService.obterClientePorId(pedidoCadastroDto.idCliente())
					.orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
			pedidoEntity.setDataPedido(pedidoCadastroDto.dataPedido());
			pedidoEntity.setStatusPedido(pedidoCadastroDto.statusPedido());
			pedidoEntity.setCliente(cliente.toEntity());
>>>>>>> 1ac6f66da363073813d04ce2ef67947a98875b61
			
			List<ItemPedido> listaItens = new ArrayList<ItemPedido>();
			for (ItemPedidoCadastroDto itemDto : pedidoCadastroDto.itens()) {
		        ProdutoDto produto = produtoService.obterProdutoPorId(itemDto.idProduto())
		                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
		        
				ItemPedido itemPedido = new ItemPedido();
				itemPedido.setQuantidade(itemDto.quantidade());
				itemPedido.setPercentual_desconto(itemDto.percentualDesconto());
				itemPedido.setProduto(produto.toEntity());
				itemPedido.setPrecoVenda(produto.valorUnitario());
				itemPedido.setValorBruto(produto.valorUnitario() * itemPedido.getQuantidade());
				itemPedido.setValor_liquido(itemPedido.getValorBruto() - (itemPedido.getValorBruto() * itemPedido.getPercentualDesconto()/100));
				itemPedido.setPedido(pedidoEntity);
				
				listaItens.add(itemPedido);
			}		
			
			pedidoEntity.setItens(listaItens);
			double valorTotal = listaItens.stream()
			        .mapToDouble(ItemPedido::getValorLiquido)
			        .sum();
			pedidoEntity.setValorTotal(valorTotal);
			
			pedidoRepositorio.save(pedidoEntity);
			return Optional.of(PedidoDto.toDto(pedidoEntity));
		}


	// DELETE
	public void apagarPedido(Long id_pedido) {
		if (!pedidoRepositorio.existsById(id_pedido)) {
			throw new ResourceNotFoundException("Cliente com ID " + id_pedido + " não encontrado.");
		}
		pedidoRepositorio.deleteById(id_pedido);
	}

	// json cadastro pedido
//	{
//		"dataPedido": "2024-10-19",
//		"statusPedido": "EM_PROCESSAMENTO",
//		"idCliente": 4,
//		"itens":[{
//			"quantidade": 2,
//			"percentualDesconto": 0,
//			"idProduto": 5	
//			},
//			{
//			"quantidade": 5,
//			"percentualDesconto": 10,
//			"idProduto": 10	
//			}]
//		}

}
