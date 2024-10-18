package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Produto;


public record ProdutoDto(
		Long id_produto,
		String nome,
		String descricao,
		int qtdEstoque,
		LocalDate dataCadastro,
		double valorUnitario,
		String imagem
		) {

	public Produto toEntity() {
		Produto produto = new Produto();
		produto.setId_produto(this.id_produto);
		produto.setNome(this.nome);
		produto.setDescricao(this.descricao);
		produto.setQtdEstoque(this.qtdEstoque);
		produto.setDataCadastro(this.dataCadastro);
		produto.setValorUnitario(this.valorUnitario);
		produto.setImagem(this.imagem);
		return produto;
	}
	
	public static ProdutoDto toDto(Produto produto) {
		return new ProdutoDto(produto.getId_produto(), produto.getNome(),
				produto.getDescricao(), produto.getQtdEstoque(), produto.getDataCadastro(),
				produto.getValorUnitario(), produto.getImagem());
	}

}
