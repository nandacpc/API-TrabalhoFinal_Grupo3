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
    private CategoriaRepository categoriaRepositorio;
	
	 //CREATE
    public CategoriaDto salvarCategoria(CategoriaDto categoriaDto) {
		return CategoriaDto.toDto(categoriaRepositorio.save(categoriaDto.toEntity()));
    }

    // READ
	public List<CategoriaDto> obterTodasCategorias() {
		return categoriaRepositorio.findAll().stream().map(c -> CategoriaDto.toDto(c)).toList();
	}

	public Optional<CategoriaDto> obterCategoriaPorId(Long id_categoria) {
		if (!categoriaRepositorio.existsById(id_categoria)) {
			return Optional.empty();
		}
		return Optional.of(CategoriaDto.toDto(categoriaRepositorio.findById(id_categoria).get()));
	}
	
	public List<CategoriaDto> obterCategoriaPorNome(String nome){
		List<Categoria> categoria = categoriaRepositorio.findByNomeIgnoreCase(nome);
		return categoria.stream().map(c -> CategoriaDto.toDto(c)).toList();
	}
	
	//UPDATE
	public Optional<CategoriaDto> alterarCategoria(Long id_categoria, CategoriaDto categoriaDto){
		if(!categoriaRepositorio.existsById(id_categoria)) {
			throw new ResourceNotFoundException("A categoria com id " + id_categoria + " não foi encontrado.");
		}
		Categoria categoriaEntity = categoriaDto.toEntity();
		categoriaEntity.setIdCategoria(id_categoria);
		categoriaRepositorio.save(categoriaEntity);
		return Optional.of(CategoriaDto.toDto(categoriaEntity));
	}

	// DELETE
	public void apagarCategoria(Long id_categoria) {
		if (!categoriaRepositorio.existsById(id_categoria)) {
			throw new ResourceNotFoundException("A categoria com id " + id_categoria + " não foi encontrado.");
		}
		categoriaRepositorio.deleteById(id_categoria);
	}
}
