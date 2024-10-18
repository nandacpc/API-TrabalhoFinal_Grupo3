package org.serratec.shablau.model;

public record EnderecoViaCep(	
    String logradouro,
    String bairro,
    String localidade,
    String uf,
    String erro
    ) {
	
	public Endereco toEntity() {
        Endereco endereco = new Endereco(); 
        
        endereco.setRua(this.logradouro);
        endereco.setBairro(this.bairro);
        endereco.setCidade(this.localidade);
        endereco.setUf(this.uf);
        
        return endereco;
    }

 }
