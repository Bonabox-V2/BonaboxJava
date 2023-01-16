package br.com.bonabox.business.api.models;

public class GetTipoCompartimentoDisponivelResponse {

	private String tamanho;
	private Boolean disponivel;
	private String descricao;
	private String codigo;

	public GetTipoCompartimentoDisponivelResponse() {
	}

	public GetTipoCompartimentoDisponivelResponse(String tamanho, boolean disponivel, String descricao, String codigo) {
		super();
		this.tamanho = tamanho;
		this.disponivel = disponivel;
		this.descricao = descricao;
		this.codigo = codigo;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public Boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Boolean disponivel) {
		this.disponivel = disponivel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
