package org.serratec.shablau.dto;

import org.serratec.shablau.model.Endereco;

public record EnderecoDto(
		Long idEndereco,
		String cep,
		String rua,
		String bairro,
		String cidade,
		int numero,
		String complemento,
		String uf
		) {

	public Endereco toEntity() { 
		Endereco endereco = new Endereco();
		endereco.setIdEndereco(this.idEndereco);
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
            return new EnderecoDto(endereco.getIdEndereco(), endereco.getCep(),
            		endereco.getRua(), endereco.getBairro(), endereco.getCidade(),
                    endereco.getNumero(), endereco.getComplemento(),endereco.getUf());
    }

}
