package br.com.bonabox.business.domain.webclient;

public class UnidadeDataWebClientResponse {

	private Integer unidadeId;
	private Integer piso;
	private Integer numeroUnidade;
	private String labelUnidade;

	public UnidadeDataWebClientResponse() {

	}

	public UnidadeDataWebClientResponse(Integer unidadeId, Integer piso, Integer numeroUnidade, String labelUnidade) {
		super();
		this.unidadeId = unidadeId;
		this.piso = piso;
		this.numeroUnidade = numeroUnidade;
		this.labelUnidade = labelUnidade;
	}

	public Integer getUnidadeId() {
		return unidadeId;
	}

	public void setUnidadeId(Integer unidadeId) {
		this.unidadeId = unidadeId;
	}

	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public Integer getNumeroUnidade() {
		return numeroUnidade;
	}

	public void setNumeroUnidade(Integer numeroUnidade) {
		this.numeroUnidade = numeroUnidade;
	}

	public String getLabelUnidade() {
		return labelUnidade;
	}

	public void setLabelUnidade(String labelUnidade) {
		this.labelUnidade = labelUnidade;
	}

}
