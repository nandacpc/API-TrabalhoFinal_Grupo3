package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Cliente;

public record ClienteDto (
		Long id_cliente,
		String email,		
		String nomeCompleto,
		String cpf,
		String telefone,
		LocalDate dataNascimento,		
		EnderecoDto endereco		
		){

	public Cliente toEntity() {
		Cliente cliente = new Cliente();
		cliente.setId_cliente(this.id_cliente);
		cliente.setEmail(this.email);
		cliente.setNomeCompleto(this.nomeCompleto);
		cliente.setCpf(this.cpf);
		cliente.setTelefone(this.telefone);
		cliente.setDataNascimento(this.dataNascimento);
		cliente.setEndereco(this.endereco.toEntity());
		
//		Endereco endereco = new Endereco();
//		endereco.setCep(cliente.getEndereco().getCep());
//		endereco.setBairro(cliente.getEndereco().getBairro());
//		endereco.setCidade(cliente.getEndereco().getCidade());
//		endereco
		return cliente;

	}

	public static ClienteDto toDto(Cliente cliente) {
		return new ClienteDto(cliente.getId_cliente(), cliente.getEmail(), cliente.getNomeCompleto(), cliente.getCpf(), cliente.getTelefone(),
				cliente.getDataNascimento(), EnderecoDto.toDto(cliente.getEndereco()));
	}
	

}
