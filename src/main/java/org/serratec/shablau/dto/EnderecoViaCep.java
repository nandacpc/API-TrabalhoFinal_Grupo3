package org.serratec.shablau.dto;

import org.serratec.shablau.model.Endereco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EnderecoViaCep(String cep, String logradouro, String bairro, String localidade, String uf) {

	public Endereco toEntity() {
		Endereco endereco = new Endereco();

		endereco.setCep(this.cep);
		endereco.setRua(this.logradouro);
		endereco.setBairro(this.bairro);
		endereco.setCidade(this.localidade);
		endereco.setUf(this.uf);

		return endereco;
	}

}
