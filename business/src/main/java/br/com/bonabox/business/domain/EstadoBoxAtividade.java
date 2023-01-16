package br.com.bonabox.business.domain;

public class EstadoBoxAtividade {

	private int estadoBoxId;
	private String nome;
	private String descricao;
	
	public EstadoBoxAtividade() {
		
	}

	public EstadoBoxAtividade(int estadoBoxId, String nome, String descricao) {
		super();
		this.estadoBoxId = estadoBoxId;
		this.nome = nome;
		this.descricao = descricao;
	}

	public int getEstadoBoxId() {
		return estadoBoxId;
	}

	public void setEstadoBoxId(int estadoBoxId) {
		this.estadoBoxId = estadoBoxId;
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
