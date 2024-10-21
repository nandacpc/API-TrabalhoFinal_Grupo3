package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Cliente;

public record ClienteDto (
		Long idCliente,
		String email,		
		String nomeCompleto,
		String cpf,
		String telefone,
		LocalDate dataNascimento,		
		EnderecoDto endereco		
		){

	public Cliente toEntity() {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(this.idCliente);
		cliente.setEmail(this.email);
		cliente.setNomeCompleto(this.nomeCompleto);
		cliente.setCpf(this.cpf);
		cliente.setTelefone(this.telefone);
		cliente.setDataNascimento(this.dataNascimento);
		cliente.setEndereco(this.endereco.toEntity());

		return cliente;

	}

	public static ClienteDto toDto(Cliente cliente) {
		return new ClienteDto(cliente.getIdCliente(), cliente.getEmail(), cliente.getNomeCompleto(), cliente.getCpf(), cliente.getTelefone(),
				cliente.getDataNascimento(), EnderecoDto.toDto(cliente.getEndereco()));
	}
	

}
