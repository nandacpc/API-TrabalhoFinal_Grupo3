package org.serratec.shablau.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_item_pedido;
	private int quantidade;
	private double preco_venda;
	private double percentual_desconto;
	private double valor_bruto;
	private double valor_liquido;
	
	@ManyToOne
	@JoinColumn(name = "id_pedido")
	Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	Produto produto;
	
	
	public Long getId_item_pedido() {
		return id_item_pedido;
	}
	public void setId_item_pedido(Long id_item_pedido) {
		this.id_item_pedido = id_item_pedido;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public double getPreco_venda() {
		return preco_venda;
	}
	public void setPreco_venda(double preco_venda) {
		this.preco_venda = preco_venda;
	}
	public double getPercentual_desconto() {
		return percentual_desconto;
	}
	public void setPercentual_desconto(double percentual_desconto) {
		this.percentual_desconto = percentual_desconto;
	}
	public double getValor_bruto() {
		return valor_bruto;
	}
	public void setValor_bruto(double valor_bruto) {
		this.valor_bruto = valor_bruto;
	}
	public double getValor_liquido() {
		return valor_liquido;
	}
	public void setValor_liquido(double valor_liquido) {
		this.valor_liquido = valor_liquido;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}
