package br.com.bonabox.business.api.models;

import java.time.LocalDateTime;

public class CreateEntregaDataResponse {

	private String codigoEntrega;
	private LocalDateTime dataHoraCriacao;

	public CreateEntregaDataResponse(String codigoEntrega, LocalDateTime dataHoraCriacao) {
		super();
		this.codigoEntrega = codigoEntrega;
		this.dataHoraCriacao = dataHoraCriacao;
	}

	public String getCodigoEntrega() {
		return codigoEntrega;
	}

	public void setCodigoEntrega(String codigoEntrega) {
		this.codigoEntrega = codigoEntrega;
	}

	public LocalDateTime getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}

}
