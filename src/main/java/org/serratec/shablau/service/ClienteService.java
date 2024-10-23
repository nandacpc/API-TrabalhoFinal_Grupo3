package org.serratec.shablau.service;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.config.ResourceNotFoundException;
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

	public ClienteDto salvarCliente(ClienteCadastroDto clienteCadastroDto) throws Exception {
		Endereco endereco = ViaCepService.preencherEnderecoViaCep(clienteCadastroDto.cep(), clienteCadastroDto.numero(),
				clienteCadastroDto.complemento());
		if (clienteRepositorio.existsByEmail(clienteCadastroDto.email())) {
			throw new IllegalArgumentException("Email já cadastrado: " + clienteCadastroDto.email());
		}
		if (clienteRepositorio.existsByCpf(clienteCadastroDto.cpf())) {
			throw new IllegalArgumentException("CPF já cadastrado: " + clienteCadastroDto.cpf());
		}
		Cliente novoCliente = new Cliente();
		novoCliente.setCpf(clienteCadastroDto.cpf());
		novoCliente.setDataNascimento(clienteCadastroDto.dataNascimento());
		novoCliente.setEmail(clienteCadastroDto.email());
		novoCliente.setNomeCompleto(clienteCadastroDto.nomeCompleto());
		novoCliente.setTelefone(clienteCadastroDto.telefone());
		novoCliente.setEndereco(endereco);
		return ClienteDto.toDto(clienteRepositorio.save(novoCliente));
	}

	public List<ClienteDto> obterTodosClientes() {
		return clienteRepositorio.findAll().stream().map(c -> ClienteDto.toDto(c)).toList();
	}

	public Optional<ClienteDto> obterClientePorId(Long id) {
		if (!clienteRepositorio.existsById(id)) {
			throw new ResourceNotFoundException("Cliente com ID " + id + " não encontrado.");
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

	public Optional<ClienteDto> alterarCliente(Long id, ClienteCadastroDto clienteCadastroDto) {
		if (!clienteRepositorio.existsById(id)) {
			throw new ResourceNotFoundException("Cliente com ID " + id + " não encontrado.");
		}
		Endereco endereco = ViaCepService.preencherEnderecoViaCep(clienteCadastroDto.cep(), clienteCadastroDto.numero(),
				clienteCadastroDto.complemento());
		Cliente cliente = clienteRepositorio.findById(id)
				.orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
		cliente.setCpf(clienteCadastroDto.cpf());
		cliente.setDataNascimento(clienteCadastroDto.dataNascimento());
		cliente.setEmail(clienteCadastroDto.email());
		cliente.setNomeCompleto(clienteCadastroDto.nomeCompleto());
		cliente.setTelefone(clienteCadastroDto.telefone());
		cliente.setEndereco(endereco);

		return Optional.of(ClienteDto.toDto(clienteRepositorio.save(cliente)));
	}

	public void apagarCliente(Long id) {
		if (!clienteRepositorio.existsById(id)) {
			throw new ResourceNotFoundException("Cliente com ID " + id + " não encontrado.");
		}
		clienteRepositorio.deleteById(id);
	}
}