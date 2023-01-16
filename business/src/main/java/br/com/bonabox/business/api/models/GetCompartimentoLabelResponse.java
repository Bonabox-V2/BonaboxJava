package br.com.bonabox.business.api.models;

public class GetCompartimentoLabelResponse {

	private String labelPorta;
	private String codigo;

	public GetCompartimentoLabelResponse(String labelPorta, String codigo) {
		super();
		this.labelPorta = labelPorta;
		this.codigo = codigo;
	}

	public String getLabelPorta() {
		return labelPorta;
	}

	public void setLabelPorta(String labelPorta) {
		this.labelPorta = labelPorta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	

}
