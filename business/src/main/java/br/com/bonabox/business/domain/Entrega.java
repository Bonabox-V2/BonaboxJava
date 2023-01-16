package br.com.bonabox.business.domain;

import java.time.LocalDateTime;

public final class Entrega {

	private final String numeroSerial;
	private final String codigoEntrega;
	private final LocalDateTime dataHoraCriacao;
	private final Integer empresaEntregadora;

	public Entrega(String numeroSerial, String codigoEntrega, LocalDateTime dataHoraCriacao, Integer empresaEntregadora) {
		super();
		this.numeroSerial = numeroSerial;
		this.codigoEntrega = codigoEntrega;
		this.dataHoraCriacao = dataHoraCriacao;
		this.empresaEntregadora = empresaEntregadora;
	}

	public Integer getEmpresaEntregadora() {
		return empresaEntregadora;
	}

	public String getCodigoEntrega() {
		return codigoEntrega;
	}

	public LocalDateTime getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public String getNumeroSerial() {
		return numeroSerial;
	}

}
