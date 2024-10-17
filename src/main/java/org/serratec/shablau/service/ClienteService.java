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

	// CREATE
	public ClienteDto salvarCliente(ClienteDto clienteDto) {
		return ClienteDto.toDto(clienteRepositorio.save(clienteDto.toEntity()));
	}

	// READ
	public List<ClienteDto> obterTodosClientes() {
		return clienteRepositorio.findAll().stream().map(c -> ClienteDto.toDto(c)).toList();
	}

	public Optional<ClienteDto> obterClientePorId(Long id_cliente) {
		if (!clienteRepositorio.existsById(id_cliente)) {
			return Optional.empty();
		}
		return Optional.of(ClienteDto.toDto(clienteRepositorio.findById(id_cliente).get()));
	}

	// UPDATE
	public Optional<ClienteDto> alterarCliente(Long id_cliente, ClienteDto clienteDto) {
		if (!clienteRepositorio.existsById(id_cliente)) {
			return Optional.empty();
		}
		Cliente clienteEntity = clienteDto.toEntity();
		clienteEntity.setId_cliente(id_cliente);
		clienteRepositorio.save(clienteEntity);
		return Optional.of(ClienteDto.toDto(clienteEntity));
	}

	// DELETE
	public boolean apagarCliente(Long id_cliente) {
		if (!clienteRepositorio.existsById(id_cliente)) {
			return false;
		}
		clienteRepositorio.deleteById(id_cliente);
		return true;
	}

}
