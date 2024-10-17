package org.serratec.shablau.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.PedidoDto;
import org.serratec.shablau.model.Cliente;
import org.serratec.shablau.model.Pedido;
import org.serratec.shablau.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

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

        try {
			HttpClient httpClient = HttpClient.newHttpClient();
			
			HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if(response.statusCode() == 200) {
				ObjectMapper objectMapper = new ObjectMapper();
				ViaCepResponse cepResponse = objectMapper.readValue(response.body(), ViaCepResponse.class);
				
				if (cepResponse.getErro() == null) {
					cliente.getEndereco().setRua(cepResponse.getLogradouro());
					cliente.getEndereco().setBairro(cepResponse.getBairro());
					cliente.getEndereco().setCidade(cepResponse.getLocalidade());
					cliente.getEndereco().setUf(cepResponse.getUf());
				} else {
					throw new RuntimeException("CEP inválido ou não encontrado.");
				}
			} else {
	            throw new RuntimeException("Falha na comunicação com o serviço ViaCep. Status: " + response.statusCode());
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Erro ao consultar o serviço ViaCep: " + e.getMessage(), e);
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
