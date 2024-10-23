package org.serratec.shablau.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "produtos")
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_produto")
	private Long idProduto;

	@NotBlank(message = "O nome do produto não pode estar em branco.")
	@Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres.")
	@Column(nullable = false, unique = true)
	private String nome;

	@Size(max = 200, message = "A descrição deve ter no máximo 200 caracteres.")
	private String descricao;

	@Positive(message = "A quantidade em estoque deve ser um número positivo.")
	@Column(name = "qnt_estoque")
	private int qtdEstoque;

	@PastOrPresent(message = "A data de cadastro deve ser igual ou inferior ao dia de hoje.")
	@Column(name = "data_cadastro")
	private LocalDate dataCadastro;

	@Column(name = "valor_unitario")
	private double valorUnitario;

	@Size(max = 255, message = "O link da imagem deve ter no máximo 255 caracteres.")
	@URL(message = "O link fornecido não é uma URL válida.")
	private String imagem;

	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;

	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	private List<ItemPedido> itens;

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}