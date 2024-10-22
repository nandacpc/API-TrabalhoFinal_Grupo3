package org.serratec.shablau.service;

import java.util.List;
import java.util.Optional;

import org.serratec.shablau.config.ResourceNotFoundException;
import org.serratec.shablau.dto.CategoriaDto;
import org.serratec.shablau.model.Categoria;
import org.serratec.shablau.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

	@Autowired
<<<<<<< HEAD
    private CategoriaRepository categoriaRepositorio;
	
	 //CREATE
    public CategoriaDto salvarCategoria(CategoriaDto categoriaDto) {
    	if(categoriaDto.nome().isBlank()) {
    		return CategoriaDto.toDto(categoriaRepositorio.save(categoriaDto.toEntity()));
		}
		throw new ResourceNotFoundException("A categoria já existe.");
    }
=======
	private CategoriaRepository categoriaRepositorio;
>>>>>>> 1ac6f66da363073813d04ce2ef67947a98875b61

	// CREATE
	public CategoriaDto salvarCategoria(CategoriaDto categoriaDto) {
		return CategoriaDto.toDto(categoriaRepositorio.save(categoriaDto.toEntity()));
	}

	// READ
	public List<CategoriaDto> obterTodasCategorias() {
		return categoriaRepositorio.findAll().stream().map(c -> CategoriaDto.toDto(c)).toList();
	}

	public Optional<CategoriaDto> obterCategoriaPorId(Long id_categoria) {
		if (!categoriaRepositorio.existsById(id_categoria)) {
			throw new ResourceNotFoundException("A categoria com ID " + id_categoria + " não foi encontrado.");
		}
		return Optional.of(CategoriaDto.toDto(categoriaRepositorio.findById(id_categoria).get()));
	}
<<<<<<< HEAD
	
	public List<CategoriaDto> obterCategoriaPorNome(String nome){
		List<Categoria> categoria = categoriaRepositorio.findByNomeContainingIgnoreCase(nome);
		return categoria.stream().map(c -> CategoriaDto.toDto(c)).toList();
	}
	
	public List<CategoriaDto> obterCategoriaPorDescricao(String palavra){
		List<Categoria> categoria = categoriaRepositorio.findByDescricaoContainingIgnoreCase(palavra);
=======

	// QUERY DERIES
	public List<CategoriaDto> obterCategoriaPorNome(String nome) {
		List<Categoria> categoria = categoriaRepositorio.findByNomeIgnoreCase(nome);
>>>>>>> 1ac6f66da363073813d04ce2ef67947a98875b61
		return categoria.stream().map(c -> CategoriaDto.toDto(c)).toList();
	}

	// UPDATE
	public Optional<CategoriaDto> alterarCategoria(Long id_categoria, CategoriaDto categoriaDto) {
		if (!categoriaRepositorio.existsById(id_categoria)) {
			throw new ResourceNotFoundException("A categoria com ID " + id_categoria + " não foi encontrado.");
		}
		Categoria categoriaEntity = categoriaDto.toEntity();
		categoriaEntity.setIdCategoria(id_categoria);
		categoriaRepositorio.save(categoriaEntity);
		return Optional.of(CategoriaDto.toDto(categoriaEntity));
	}

	// DELETE
	public void apagarCategoria(Long id_categoria) {
		if (!categoriaRepositorio.existsById(id_categoria)) {
			throw new ResourceNotFoundException("A categoria com ID " + id_categoria + " não foi encontrado.");
		}
		categoriaRepositorio.deleteById(id_categoria);
	}
}
