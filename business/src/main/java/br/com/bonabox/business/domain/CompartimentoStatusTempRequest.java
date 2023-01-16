package br.com.bonabox.business.domain;

public class CompartimentoStatusTempRequest {
	private String compartimentoIdAleatorio;
	private int compartimentoId;
	private int boxId;
	private int codigoEstadoBoxAtividade;
	private String entregaId;
	private int compartimentoCom1Id;
	private String labelPorta;

	public CompartimentoStatusTempRequest(String compartimentoIdAleatorio, int compartimentoId, int boxId,
			int codigoEstadoBoxAtividade, String entregaId, int compartimentoCom1Id, String labelPorta) {
		super();
		this.compartimentoIdAleatorio = compartimentoIdAleatorio;
		this.compartimentoId = compartimentoId;
		this.boxId = boxId;
		this.codigoEstadoBoxAtividade = codigoEstadoBoxAtividade;
		this.entregaId = entregaId;
		this.compartimentoCom1Id = compartimentoCom1Id;
		this.labelPorta = labelPorta;
	}

	public String getLabelPorta() {
		return labelPorta;
	}

	public void setLabelPorta(String labelPorta) {
		this.labelPorta = labelPorta;
	}

	public int getCompartimentoCom1Id() {
		return compartimentoCom1Id;
	}

	public void setCompartimentoCom1Id(int compartimentoCom1Id) {
		this.compartimentoCom1Id = compartimentoCom1Id;
	}

	public String getCompartimentoIdAleatorio() {
		return compartimentoIdAleatorio;
	}

	public void setCompartimentoIdAleatorio(String compartimentoIdAleatorio) {
		this.compartimentoIdAleatorio = compartimentoIdAleatorio;
	}

	public int getCompartimentoId() {
		return compartimentoId;
	}

	public void setCompartimentoId(int compartimentoId) {
		this.compartimentoId = compartimentoId;
	}

	public int getBoxId() {
		return boxId;
	}

	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}

	public int getCodigoEstadoBoxAtividade() {
		return codigoEstadoBoxAtividade;
	}

	public void setCodigoEstadoBoxAtividade(int codigoEstadoBoxAtividade) {
		this.codigoEstadoBoxAtividade = codigoEstadoBoxAtividade;
	}

	public String getEntregaId() {
		return entregaId;
	}

	public void setEntregaId(String entregaId) {
		this.entregaId = entregaId;
	}
}
