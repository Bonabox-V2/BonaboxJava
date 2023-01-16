package br.com.bonabox.condominio.api.domain;

public class Pessoa {

	private Integer pessoaId;
	private String nome;
	private String numeroCelular;
	private String email;

	public Pessoa(Integer pessoaId, String nome, String numeroCelular, String email) {
		super();
		this.pessoaId = pessoaId;
		this.nome = nome;
		this.numeroCelular = numeroCelular;
		this.email = email;
	}

	public Integer getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Integer pessoaId) {
		this.pessoaId = pessoaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
