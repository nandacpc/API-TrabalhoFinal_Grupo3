package org.serratec.shablau.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@Column(name="id_pedido")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPedido;

	@PastOrPresent(message="A data do pedido não pode ser futura.")
	@Column(name = "data_pedido")
	private LocalDate dataPedido;
	
	@FutureOrPresent(message = "A data de envio deve ser hoje ou uma data futura.")
	@Column(name = "data_envio")
	private LocalDate dataEnvio;

	@FutureOrPresent(message = "A data de entrega deve ser hoje ou uma data futura.")
	@Column(name = "data_entrega")
	private LocalDate dataEntrega;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status_pedido")
	private StatusEnum statusPedido;
	
	@Min(value = 1, message = "O valor total deve ser maior que zero.")
	@Column(name = "valor_total")
	private double valorTotal;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ItemPedido> itens;
	
	public Long getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(Long id_pedido) {
		this.idPedido = id_pedido;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public LocalDate getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(LocalDate dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public StatusEnum getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusEnum statusPedido) {
		this.statusPedido = statusPedido;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		itens.forEach(i -> i.setPedido(this));
		this.itens = itens;
	}


}
