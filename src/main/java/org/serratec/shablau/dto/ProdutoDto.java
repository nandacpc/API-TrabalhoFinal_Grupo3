package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Produto;

public record ProdutoDto(Long idProduto, String nome, String descricao, int qntEstoque, LocalDate dataCadastro,
		double valorUnitario, String imagem, CategoriaDto categoria) {

	public Produto toEntity() {
		Produto produto = new Produto();
		produto.setIdProduto(this.idProduto);
		produto.setNome(this.nome);
		produto.setDescricao(this.descricao);
		produto.setQtdEstoque(this.qntEstoque);
		produto.setDataCadastro(this.dataCadastro);
		produto.setValorUnitario(this.valorUnitario);
		produto.setImagem(this.imagem);
		produto.setCategoria(this.categoria.toEntity());
		return produto;
	}

	public static ProdutoDto toDto(Produto produto) {
		return new ProdutoDto(produto.getIdProduto(), produto.getNome(), produto.getDescricao(),
				produto.getQtdEstoque(), produto.getDataCadastro(), produto.getValorUnitario(), produto.getImagem(),
				CategoriaDto.toDto(produto.getCategoria()));
	}

}
