package org.serratec.shablau.service;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.PedidoDto;
import org.serratec.shablau.model.Cliente;
import org.serratec.shablau.model.Pedido;
import org.serratec.shablau.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepositorio;

    //CREATE
    public PedidoDto salvarPedido(PedidoDto dto) {
        Pedido pedido = dto.toEntity();

        if (dto.cliente().cep() != null) {
            preencherEnderecoViaCep(pedido.getCliente());
        }

        Pedido pedidoSalvo = pedidoRepositorio.save(pedido);
        return PedidoDto.toDto(pedidoSalvo);
    }

    private void preencherEnderecoViaCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        RestTemplate restTemplate = new RestTemplate();
        ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);

        if (response != null && response.getErro() == null) {
            cliente.getEndereco().setRua(response.getLogradouro());
            cliente.getEndereco().setBairro(response.getBairro());
            cliente.getEndereco().setCidade(response.getLocalidade());
            cliente.getEndereco().setUf(response.getUf());
        } else {
            throw new RuntimeException("CEP inválido ou não encontrado.");
        }
    }

    private static class ViaCepResponse {
        private String logradouro;
        private String bairro;
        private String localidade;
        private String uf;
        private Boolean erro;

        // Getters e setters

        public Boolean getErro() {
            return erro;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public String getBairro() {
            return bairro;
        }

        public String getLocalidade() {
            return localidade;
        }

        public String getUf() {
            return uf;
        }
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
