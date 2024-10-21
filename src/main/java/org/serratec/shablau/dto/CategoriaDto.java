package org.serratec.shablau.dto;

import org.serratec.shablau.model.Categoria;

public record CategoriaDto(
		Long idCategoria, 
		String nome, 
		String descricao
		) {

	public Categoria toEntity() {
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(idCategoria);
		categoria.setNome(this.nome);
		categoria.setDescricao(this.descricao);
		return categoria;

	}

	public static CategoriaDto toDto(Categoria categoria) {
		return new CategoriaDto(categoria.getIdCategoria(), categoria.getNome(), categoria.getDescricao());
	}
}