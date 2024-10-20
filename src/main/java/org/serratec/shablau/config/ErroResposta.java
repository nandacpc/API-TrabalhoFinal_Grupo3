package org.serratec.shablau.config;

import java.time.LocalDateTime;
import java.util.List;

public class ErroResposta {
	private int status;
	private String titulo;
	private LocalDateTime dataHora;
	private List<String> erros;
	
	public ErroResposta(int status, String titulo, LocalDateTime dataHora, List<String> erros) {
		this.status = status;
		this.titulo = titulo;
		this.dataHora = dataHora;
		this.erros= erros;
	}

	
	public List<String> getErros() {
		return erros;
	}


	public void setErros(List<String> erros) {
		this.erros = erros;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	
	
}
