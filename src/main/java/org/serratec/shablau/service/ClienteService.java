package org.serratec.shablau.service;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.dto.ClienteDto;
import org.serratec.shablau.model.Cliente;
import org.serratec.shablau.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepositorio;
	
	@Autowired
    private ViaCepService viaCepService;
	
	//CREATE
	public ClienteDto salvarCliente(ClienteDto clienteDto) {
		Cliente clienteEntity = clienteDto.toEntity();
		viaCepService.preencherEnderecoViaCep(clienteEntity, clienteEntity.getEndereco().getNumero(), clienteEntity.getEndereco().getComplemento());
		return ClienteDto.toDto(clienteRepositorio.save(clienteEntity));
	}
	
	//READ
	public List<ClienteDto> obterTodos() {
		return clienteRepositorio.findAll().stream().map(c -> ClienteDto.toDto(c)).toList();
	}
	
	public Optional<ClienteDto> obterPorId(Long id) {
		if(!clienteRepositorio.existsById(id)) {
			return Optional.empty();
		}
		return Optional.of(ClienteDto.toDto(clienteRepositorio.findById(id).get()));
		
	}
	
	public List<ClienteDto> obterPorNome(String nome) {
		List<Cliente> clientes = clienteRepositorio.findByNomeCompletoContainingIgnoreCase(nome);
		return clientes.stream().map(c -> ClienteDto.toDto(c)).toList();
	}
	
	public Optional<ClienteDto> obterPorCpf(String cpf) {
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