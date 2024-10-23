package org.serratec.shablau.service;

import java.time.LocalDate;
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

	public PedidoDto salvarPedido(PedidoCadastroDto pedidoCadastroDto) {
		ClienteDto cliente = clienteService.obterClientePorId(pedidoCadastroDto.idCliente()).orElseThrow(
				() -> new RuntimeException("Cliente não encontrado. ID: " + pedidoCadastroDto.idCliente()));

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
			itemPedido.setValor_liquido(itemPedido.getValorBruto()
					- (itemPedido.getValorBruto() * itemPedido.getPercentualDesconto() / 100));
			itemPedido.setPedido(novoPedido);

			itensPedido.add(itemPedido);
		}

		double valorTotal = itensPedido.stream().mapToDouble(i -> i.getValorLiquido()).sum();
		novoPedido.setValorTotal(valorTotal);

		novoPedido.setItens(itensPedido);

		pedidoRepositorio.save(novoPedido);

		String relatorioHtml = gerarHtmlRelatorio(novoPedido.getIdPedido());

		emailService.enviarEmail(cliente.email(), "Novo pedido gerado", relatorioHtml);

		return PedidoDto.toDto(novoPedido);
	}

	public String gerarRelatorio(Long idPedido) { 
		List<ItemPedidoRelatorioDto> itensRelatorio = pedidoRepositorio.findItensByPedidoId(idPedido);
		Pedido pedido = pedidoRepositorio.findById(idPedido).get();
		PedidoRelatorioDto relatorio = new PedidoRelatorioDto(pedido.getIdPedido(), pedido.getDataPedido(),
				pedido.getValorTotal(), itensRelatorio);
		return relatorio.toString();
	}

	public String gerarHtmlRelatorio(Long idPedido) {
	    StringBuilder html = new StringBuilder();
	    List<ItemPedidoRelatorioDto> itensRelatorio = pedidoRepositorio.findItensByPedidoId(idPedido);
		Pedido pedido = pedidoRepositorio.findById(idPedido).get();
		PedidoRelatorioDto relatorio = new PedidoRelatorioDto(pedido.getIdPedido(), pedido.getDataPedido(), pedido.getValorTotal(), itensRelatorio);

	    html.append("<html><head><title>Relatório de Pedido</title></head><body>");

	    html.append("<h1>Relatório do Pedido</h1>");
	    
	    html.append("<p><strong>ID do Pedido:</strong> ").append(relatorio.idPedido()).append("</p>");
	    html.append("<p><strong>Data do Pedido:</strong> ").append(relatorio.dataPedido()).append("</p>");
	    html.append("<p><strong>Valor Total:</strong> R$ ").append(String.format("%.2f", relatorio.valorTotal())).append("</p>");
	    
	    html.append("<h2>Itens do Pedido</h2>");
	    html.append("<table border='1' cellpadding='5' cellspacing='0'>");
	    html.append("<tr><th>Produto</th><th>Quantidade</th><th>Preço Unitário</th><th>Desconto</th><th>Valor Total</th></tr>");
	    
	    for (ItemPedidoRelatorioDto item : relatorio.itens()) {
	        html.append("<tr style='text-align:center;'>")
	            .append("<td>").append(item.nomeProduto()).append("</td>")
	            .append("<td>").append(item.quantidade()).append("</td>")
	            .append("<td>R$ ").append(String.format("%.2f", item.valorUnitario())).append("</td>")
	            .append("<td>").append((int)item.percentualDesconto()).append("%</td>")
	            .append("<td>R$ ").append(String.format("%.2f", item.valorLiquido())).append("</td>")
	            .append("</tr>");
	    }
	    
	    html.append("</table>");
	    html.append("</body></html>");

	    return html.toString();
	}
	
	public List<PedidoDto> obterTodosPedidos() {
		return pedidoRepositorio.findAll().stream().map(p -> PedidoDto.toDto(p)).toList();
	}

	public Optional<PedidoDto> obterPedidoPorId(Long id) {
		if (!pedidoRepositorio.existsById(id)) {
			throw new ResourceNotFoundException("Pedido com ID " + id + " não encontrado.");
		}
		return Optional.of(PedidoDto.toDto(pedidoRepositorio.findById(id).get()));
	}

	public List<PedidoDto> obterPedidoPorStatus(StatusEnum status) {
		List<Pedido> pedido = pedidoRepositorio.findByStatusPedido(status);
		return pedido.stream().map(p -> PedidoDto.toDto(p)).toList();
	}

	public List<PedidoDto> obterPedidoPorDataPedido(LocalDate dataPedido) {
		List<Pedido> pedido = pedidoRepositorio.findByDataPedido(dataPedido);
		return pedido.stream().map(p -> PedidoDto.toDto(p)).toList();
	}

	public List<PedidoDto> obterPedidoPorDataEntrega(LocalDate dataEntrega) {
		List<Pedido> pedido = pedidoRepositorio.findByDataEntrega(dataEntrega);
		return pedido.stream().map(p -> PedidoDto.toDto(p)).toList();
	}

	public List<PedidoDto> obterPedidoPorDataEnvio(LocalDate dataEnvio) {
		List<Pedido> pedido = pedidoRepositorio.findByDataEnvio(dataEnvio);
		return pedido.stream().map(p -> PedidoDto.toDto(p)).toList();
	}

	public Optional<PedidoDto> alterarDadosPedido(Long idPedido, PedidoCadastroDto pedidoCadastroDto) {
		if (!pedidoRepositorio.existsById(idPedido)) {
			return Optional.empty();
		}
		Pedido pedidoEntity = pedidoRepositorio.findById(idPedido)
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
			itemPedido.setValor_liquido(itemPedido.getValorBruto()
					- (itemPedido.getValorBruto() * itemPedido.getPercentualDesconto() / 100));
			itemPedido.setPedido(pedidoEntity);
			listaItens.add(itemPedido);
		}
		pedidoEntity.setItens(listaItens);
		double valorTotal = listaItens.stream().mapToDouble(ItemPedido::getValorLiquido).sum();
		pedidoEntity.setValorTotal(valorTotal);
		pedidoRepositorio.save(pedidoEntity);
		return Optional.of(PedidoDto.toDto(pedidoEntity));
	}

	public boolean apagarPedido(Long idPedido) {
		if (!pedidoRepositorio.existsById(idPedido)) {
			throw new ResourceNotFoundException("Pedido com ID " + idPedido + " não encontrado.");
		}
		pedidoRepositorio.deleteById(idPedido);
		return true; 
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