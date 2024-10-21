package org.serratec.shablau.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.PedidoCadastroDto;
import org.serratec.shablau.dto.PedidoDto;
import org.serratec.shablau.dto.PedidoRelatorioDto;
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

import jakarta.validation.Valid;


@RestController
@RequestMapping(path = "/pedidos")
public class PedidoController {
	@Autowired
	private PedidoService pedidoServico;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDto cadastrarPedido(@Valid @RequestBody PedidoCadastroDto pedidoCadastroDto) {
		return pedidoServico.salvarPedido(pedidoCadastroDto);
	}

	@GetMapping
	public List<PedidoDto> buscarTodosPedidos() {
		return pedidoServico.obterTodosPedidos();
	}
	
	@GetMapping("/relatorio/{idPedido}")
	public PedidoRelatorioDto exibirRelatorio(@PathVariable Long idPedido) {
		return pedidoServico.gerarRelatorio(idPedido);
	}
	
//	@GetMapping("/relatorio/{id_pedido}")
//    public ResponseEntity<List<PedidoRelatorioDto>> exibirRelatorio(@PathVariable Long id_pedido) {
//        List<PedidoRelatorioDto> pedidoRelatorio = pedidoServico.gerarRelatorio(id_pedido);
//		return ResponseEntity.ok(pedidoRelatorio);
//    }


	@GetMapping("/{id_pedido}")
	public ResponseEntity<PedidoDto> buscarPedidoPorId(@PathVariable Long id_pedido) {
		Optional<PedidoDto> pedidoDto = pedidoServico.obterPedidoPorId(id_pedido);

		if (!pedidoDto.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pedidoDto.get());
	}
  
  @GetMapping("/pedido/{status}")
	public List<PedidoDto> obterPorCliente(@PathVariable String status) {
		return pedidoServico.obterPorStatus(StatusEnum.valueOf(status.toUpperCase()));
	}


	@PutMapping("/{id_pedido}")
	public ResponseEntity<PedidoDto> modificarPedido(@PathVariable Long id_pedido, @RequestBody PedidoCadastroDto pedidoDto) {
		Optional<PedidoDto> pedidoAlterado = pedidoServico.alterarDadosPedido(id_pedido, pedidoDto);
		if (!pedidoAlterado.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pedidoAlterado.get());
	}

	@DeleteMapping("/{id_pedido}")
	public ResponseEntity<Void> deletarPedido(@PathVariable Long id_pedido) {
		if (!pedidoServico.apagarPedido(id_pedido)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}	
	
	/* EXEMPLO
	 * {
	 "data_pedido": "2024-10-16",
	 "data_entrega": "2024-10-20",
	 "data_envio": "2024-10-17",
	 "status_pedido": "PROCESSANDO",
	 "valor_total": 150.0,
	 "cliente": {
	"email": "cliente@email.com",
	"nome_completo": "João Silva",
	"cpf": "12345678900",
	"telefone": "11999999999",
	"data_nascimento": "1990-01-01",
	"cep": "01001000",
	"numero": 123,
	"complemento": "Apto 45"
  }
}
*/
	
//	public void adicionarProdutoAoPedido(Pedido pedido, Produto produto, int quantidade, double precoVenda) {
//	    ItemPedido item = new ItemPedido();
//	    item.setProduto(produto);
//	    item.setQuantidade(quantidade);
//	    item.setPrecoVenda(precoVenda);
//	    // Calcular valor bruto e líquido
//	    pedido.getItens().add(item);
//	    item.setPedido(pedido); 
//	}

}
