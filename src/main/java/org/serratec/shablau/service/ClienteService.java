package org.serratec.shablau.service;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.ClienteCadastroDto;
import org.serratec.shablau.dto.ClienteDto;
import org.serratec.shablau.model.Cliente;
import org.serratec.shablau.model.Endereco;
import org.serratec.shablau.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepositorio;
	
	
	//CREATE
	public ClienteDto salvarCliente(ClienteCadastroDto clienteCadastroDto) {
		Endereco endereco = ViaCepService.preencherEnderecoViaCep(clienteCadastroDto.cep(), clienteCadastroDto.numero(), clienteCadastroDto.complemento());
		Cliente clienteEntity = new Cliente();
		clienteEntity.setCpf(clienteCadastroDto.cpf());
		clienteEntity.setDataNascimento(clienteCadastroDto.dataNascimento());
		clienteEntity.setEmail(clienteCadastroDto.email());
		clienteEntity.setNomeCompleto(clienteCadastroDto.nomeCompleto());
		clienteEntity.setTelefone(clienteCadastroDto.telefone());;
		clienteEntity.setEndereco(endereco);
		return ClienteDto.toDto(clienteRepositorio.save(clienteEntity));
	}
	
	//READ
	public List<ClienteDto> obterTodosClientes() {
		return clienteRepositorio.findAll().stream().map(c -> ClienteDto.toDto(c)).toList();
	}
	
	public Optional<ClienteDto> obterClientePorId(Long id) {
		if(!clienteRepositorio.existsById(id)) {
			return Optional.empty();
		}
		return Optional.of(ClienteDto.toDto(clienteRepositorio.findById(id).get()));
		
	}
	
	public List<ClienteDto> obterClientePorNome(String nome) {
		List<Cliente> clientes = clienteRepositorio.findByNomeCompletoContainingIgnoreCase(nome);
		return clientes.stream().map(c -> ClienteDto.toDto(c)).toList();
	}
	
	public Optional<ClienteDto> obterClientePorCpf(String cpf) {
		return Optional.of(ClienteDto.toDto(clienteRepositorio.findByCpf(cpf)));
	}
	
	//UPDATE
	public Optional<ClienteDto> alterarCliente(Long id, ClienteDto clienteDto){
		if(!clienteRepositorio.existsById(id)) {
			return Optional.empty();
		}
		Cliente clienteEntity = clienteDto.toEntity();
		clienteEntity.setId_cliente(id);
		clienteRepositorio.save(clienteEntity);
		return Optional.of(ClienteDto.toDto(clienteEntity));
	}
	
	//DELETE
	public boolean apagarCliente(Long id) {
		if(!clienteRepositorio.existsById(id)) {
			return false;
		}
		clienteRepositorio.deleteById(id);
		return true;
	}
}