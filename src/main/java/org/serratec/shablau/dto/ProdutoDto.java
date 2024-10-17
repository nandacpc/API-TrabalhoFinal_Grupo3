package org.serratec.shablau.dto;

import java.time.LocalDate;

import org.serratec.shablau.model.Produto;


public record ProdutoDto(
		Long id_produto,
		String nome,
		String descricao,
		int qtd_estoque,
		LocalDate data_cadastro,
		double valor_unitario,
		String imagem
		) {

	public Produto toEntity() {
		Produto produto = new Produto();
		produto.setId_produto(this.id_produto);
		produto.setNome(this.nome);
		produto.setDescricao(this.descricao);
		produto.setQtd_estoque(this.qtd_estoque);
		produto.setData_cadastro(this.data_cadastro);
		produto.setValor_unitario(this.valor_unitario);
		produto.setImagem(this.imagem);
		return produto;
	}
	
	public static ProdutoDto toDto(Produto produto) {
		return new ProdutoDto(produto.getId_produto(), produto.getNome(),
				produto.getDescricao(), produto.getQtd_estoque(), produto.getData_cadastro(),
				produto.getValor_unitario(), produto.getImagem());
	}

}
