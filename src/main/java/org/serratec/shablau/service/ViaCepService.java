package org.serratec.shablau.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.serratec.shablau.model.Cliente;
import org.serratec.shablau.model.Endereco;
import org.serratec.shablau.model.EnderecoViaCep;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ViaCepService {
	
	public void preencherEnderecoViaCep(Cliente cliente, int numero, String complemento) { 
        String cep = cliente.getEndereco().getCep();
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
			HttpClient httpClient = HttpClient.newHttpClient();
			
			HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if(response.statusCode() == 200) {
				ObjectMapper objectMapper = new ObjectMapper();
				EnderecoViaCep enderecoViaCep = objectMapper.readValue(response.body(), EnderecoViaCep.class);
				
				if (enderecoViaCep != null) {
					Endereco endereco = enderecoViaCep.toEntity();
					cliente.setEndereco(endereco);
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
}
