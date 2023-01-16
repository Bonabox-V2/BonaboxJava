package br.com.bonabox.business.domain;

public class Ala {
	private Integer alaId;
	private String label;
	private String nome;
	private String descricao;

	public Ala() {

	}

	public Ala(Integer alaId, String label, String nome, String descricao) {
		super();
		this.alaId = alaId;
		this.label = label;
		this.nome = nome;
		this.descricao = descricao;
	}

	public Integer getAlaId() {
		return alaId;
	}

	public void setAlaId(Integer alaId) {
		this.alaId = alaId;
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
