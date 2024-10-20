package org.serratec.shablau.dto;

import org.serratec.shablau.model.Categoria;

public record CategoriaDto(
		Long id_categoria, 
		String nome, 
		String descricao
		) {

	public Categoria toEntity() {
		Categoria categoria = new Categoria();
		categoria.setId_categoria(id_categoria);
		categoria.setNome(this.nome);
		categoria.setDescricao(this.descricao);
		return categoria;

	}

	public static CategoriaDto toDto(Categoria categoria) {
		return new CategoriaDto(categoria.getId_categoria(), categoria.getNome(), categoria.getDescricao());
	}
}