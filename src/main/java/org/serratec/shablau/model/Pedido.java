package org.serratec.shablau.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "pedidos")
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_pedido;
	@PastOrPresent
	private LocalDate data_pedido;
	@FutureOrPresent
	private LocalDate data_entrega;
	@FutureOrPresent
	private LocalDate data_envio;
	@Enumerated(EnumType.STRING)
	private StatusEnum status_pedido;
	private double valor_total;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens;
	
	public Long getId_pedido() {
		return id_pedido;
	}
	public void setId_pedido(Long id_pedido) {
		this.id_pedido = id_pedido;
	}
	public LocalDate getData_pedido() {
		return data_pedido;
	}
	public void setData_pedido(LocalDate data_pedido) {
		this.data_pedido = data_pedido;
	}
	public LocalDate getData_entrega() {
		return data_entrega;
	}
	public void setData_entrega(LocalDate data_entrega) {
		this.data_entrega = data_entrega;
	}
	public LocalDate getData_envio() {
		return data_envio;
	}
	public void setData_envio(LocalDate data_envio) {
		this.data_envio = data_envio;
	}	
	public StatusEnum getStatus_pedido() {
		return status_pedido;
	}
	public void setStatus_pedido(StatusEnum status_pedido) {
		this.status_pedido = status_pedido;
	}
	public double getValor_total() {
		return valor_total;
	}
	public void setValor_total(double valor_total) {
		this.valor_total = valor_total;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
