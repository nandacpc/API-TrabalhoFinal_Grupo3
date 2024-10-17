package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Cliente;

public record ClienteDto(
		Long id_cliente,
		String email,
		String nome_completo,
		String cpf,
		String telefone,
		LocalDate data_nascimento,
		EnderecoDto endereco
		) {

	public Cliente toEntity() {
		Cliente cliente = new Cliente();
		cliente.setId_cliente(this.id_cliente);
		cliente.setEmail(this.email);
		cliente.setNome_completo(this.nome_completo);
		cliente.setCpf(this.cpf);
		cliente.setTelefone(this.telefone);
		cliente.setData_nascimento(this.data_nascimento);
		cliente.setEndereco(this.endereco.toEntity());
		return cliente;
	}
	
	public static ClienteDto toDto(Cliente cliente) {
		return new ClienteDto(cliente.getId_cliente(), cliente.getEmail(),
				cliente.getNome_completo(), cliente.getCpf(), 
				cliente.getTelefone(), cliente.getData_nascimento(), EnderecoDto.toDto(cliente.getEndereco()));
	}
}
