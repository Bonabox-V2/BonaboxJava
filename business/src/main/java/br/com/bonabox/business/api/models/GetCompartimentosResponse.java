package br.com.bonabox.business.api.models;


import br.com.bonabox.business.domain.EstadoBoxAtividade;

import java.time.LocalDateTime;

public class GetCompartimentosResponse {

	private final String tipoBox;
	private final String compartimentoId;
	private final String labelPorta;
	private final String compartimentoTamanho;
	private final EstadoBoxAtividade estadoBoxAtividade;
	private final String unidade;
	private final String bloco;
	private int compartimentoCom1Id;
	private final LocalDateTime dataHora;

	public GetCompartimentosResponse(String tipoBox, String compartimentoId, String labelPorta,
			String compartimentoTamanho, EstadoBoxAtividade estadoBoxAtividade, String unidade, String bloco, LocalDateTime dataHora) {
		super();
		this.tipoBox = tipoBox;
		this.compartimentoId = compartimentoId;
		this.labelPorta = labelPorta;
		this.compartimentoTamanho = compartimentoTamanho;
		this.estadoBoxAtividade = estadoBoxAtividade;
		this.unidade = unidade;
		this.bloco = bloco;
		this.dataHora = dataHora;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public int getCompartimentoCom1Id() {
		return compartimentoCom1Id;
	}

	public void setCompartimentoCom1Id(int compartimentoCom1Id) {
		this.compartimentoCom1Id = compartimentoCom1Id;
	}

	public String getUnidade() {
		return unidade;
	}

	public String getBloco() {
		return bloco;
	}

	public String getTipoBox() {
		return tipoBox;
	}

	public String getCompartimentoId() {
		return compartimentoId;
	}

	public String getLabelPorta() {
		return labelPorta;
	}

	public String getCompartimentoTamanho() {
		return compartimentoTamanho;
	}

	public EstadoBoxAtividade getEstadoBoxAtividade() {
		return estadoBoxAtividade;
	}

}
