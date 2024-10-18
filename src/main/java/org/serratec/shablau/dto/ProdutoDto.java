package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Produto;


public record ProdutoDto(
		Long id_produto,
		String nome,
		String descricao,
		int qnt_estoque,
		LocalDate data_cadastro,
		double valor_unitario,
		String imagem,
		CategoriaDto categoria
		) {

	public Produto toEntity() {
		Produto produto = new Produto();
		produto.setId_produto(this.id_produto);
		produto.setNome(this.nome);
		produto.setDescricao(this.descricao);
		produto.setQtdEstoque(this.qnt_estoque);
		produto.setDataCadastro(this.data_cadastro);
		produto.setValorUnitario(this.valor_unitario);
		produto.setImagem(this.imagem);
		produto.setCategoria(this.categoria.toEntity());
		return produto;
	}
	
	public static ProdutoDto toDto(Produto produto) {
		return new ProdutoDto(produto.getId_produto(), produto.getNome(),
				produto.getDescricao(), produto.getQtdEstoque(), produto.getDataCadastro(),
				produto.getValorUnitario(), produto.getImagem(), CategoriaDto.toDto(produto.getCategoria()));
	}

}
