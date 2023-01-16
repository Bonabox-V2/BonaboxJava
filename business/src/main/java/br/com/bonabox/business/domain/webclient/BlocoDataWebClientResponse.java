package br.com.bonabox.business.domain.webclient;

public class BlocoDataWebClientResponse {

	private Integer blocoId;
	private String label;
	private String nome;
	private String descricao;

	public BlocoDataWebClientResponse() {

	}

	public BlocoDataWebClientResponse(Integer blocoId, String label, String nome, String descricao) {
		super();
		this.blocoId = blocoId;
		this.label = label;
		this.nome = nome;
		this.descricao = descricao;
	}

	public Integer getBlocoId() {
		return blocoId;
	}

	public void setBlocoId(Integer blocoId) {
		this.blocoId = blocoId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}