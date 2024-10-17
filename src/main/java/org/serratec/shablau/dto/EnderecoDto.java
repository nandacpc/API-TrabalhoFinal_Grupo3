package org.serratec.shablau.dto;

import org.serratec.shablau.model.Endereco;

public record EnderecoDto(Long id_endereco, String cep, String rua, String bairro, String cidade, int numero,
		String complemento, String uf) {

	public Endereco toEntity() {
		Endereco endereco = new Endereco();
		endereco.setId_endereco(this.id_endereco);
		endereco.setCep(this.cep);
		endereco.setRua(this.rua);
		endereco.setBairro(this.bairro);
		endereco.setCidade(this.cidade);
		endereco.setNumero(this.numero);
		endereco.setComplemento(this.complemento);
		endereco.setUf(this.uf);
		return endereco;
	}

	public static EnderecoDto toDto(Endereco endereco) {
		return new EnderecoDto(endereco.getId_endereco(), endereco.getCep(), endereco.getRua(), endereco.getBairro(),
				endereco.getCidade(), endereco.getNumero(), endereco.getComplemento(), endereco.getUf());
	}
}