package br.com.bonabox.box.api.controller.models;


public class BoxCompartimentosResponse {

	private int compartimentoBoxId;

	private int codigoCompartimento;

	private CompartimentoResponse compartimento;

	private int codigoBox;

	private String label;

	public BoxCompartimentosResponse(int compartimentoBoxId, int codigoCompartimento,
			CompartimentoResponse compartimento, int codigoBox, String label) {
		super();
		this.compartimentoBoxId = compartimentoBoxId;
		this.codigoCompartimento = codigoCompartimento;
		this.compartimento = compartimento;
		this.codigoBox = codigoBox;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getCompartimentoBoxId() {
		return compartimentoBoxId;
	}

	public void setCompartimentoBoxId(int compartimentoId) {
		this.compartimentoBoxId = compartimentoId;
	}

	public int getCodigoCompartimento() {
		return codigoCompartimento;
	}

	public void setCodigoCompartimento(int codigoCompartimento) {
		this.codigoCompartimento = codigoCompartimento;
	}

	public CompartimentoResponse getCompartimento() {
		return compartimento;
	}

	public void setCompartimento(CompartimentoResponse compartimento) {
		this.compartimento = compartimento;
	}

	public int getCodigoBox() {
		return codigoBox;
	}

	public void setCodigoBox(int codigoBox) {
		this.codigoBox = codigoBox;
	}

}
