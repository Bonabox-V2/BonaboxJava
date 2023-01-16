package br.com.bonabox.box.api.controller.models;

public class CompartimentoResponse {

	private int compartimentoId;

	private String nome;

	private String descricao;

	private String tamanho;

	public CompartimentoResponse(Integer compartimentoId, String nome, String descricao, String tamanho) {
		super();
		this.compartimentoId = compartimentoId;
		this.nome = nome;
		this.descricao = descricao;
		this.tamanho = tamanho;
	}

	public int getCompartimentoId() {
		return compartimentoId;
	}

	public void setCompartimentoId(int compartimentoId) {
		this.compartimentoId = compartimentoId;
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

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

}
