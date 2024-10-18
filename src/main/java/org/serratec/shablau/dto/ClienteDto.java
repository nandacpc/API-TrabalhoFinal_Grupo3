package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Cliente;
import org.serratec.shablau.model.Endereco;

public record ClienteDto(
		Long id_cliente,
		String email,
		String nomeCompleto,
		String cpf,
		String telefone,
		LocalDate dataNascimento,
		String cep,
		int numero,
		String complemento
		) {

	public Cliente toEntity() {
		Cliente cliente = new Cliente();
		cliente.setId_cliente(this.id_cliente);
		cliente.setEmail(this.email);
		cliente.setNomeCompleto(this.nomeCompleto);
		cliente.setCpf(this.cpf);
		cliente.setTelefone(this.telefone);
		cliente.setDataNascimento(this.dataNascimento);
		
		Endereco endereco = new Endereco();
		endereco.setNumero(this.numero);
		endereco.setComplemento(this.complemento);
		cliente.setEndereco(endereco);
		
		return cliente;
	}
	
	public static ClienteDto toDto(Cliente cliente) {
		return new ClienteDto(cliente.getId_cliente(), cliente.getEmail(),
				cliente.getNomeCompleto(), cliente.getCpf(), 
				cliente.getTelefone(), cliente.getDataNascimento(), cliente.getEndereco().getCep(), cliente.getEndereco().getNumero(),
				cliente.getEndereco().getComplemento());
	}
}

