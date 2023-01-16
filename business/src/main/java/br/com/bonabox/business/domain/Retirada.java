package br.com.bonabox.business.domain;

public final class Retirada {

	private String labelPorta;
	private String compartimentoId;

	public Retirada() {
		super();
	}

	public Retirada(String labelPorta, String compartimentoId) {
		super();
		this.labelPorta = labelPorta;
		this.compartimentoId = compartimentoId;
	}

	public String getLabelPorta() {
		return labelPorta;
	}

	public void setLabelPorta(String labelPorta) {
		this.labelPorta = labelPorta;
	}

	public String getCompartimentoId() {
		return compartimentoId;
	}

	public void setCompartimentoId(String compartimentoId) {
		this.compartimentoId = compartimentoId;
	}

}
