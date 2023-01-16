package br.com.bonabox.condominio.api.domain.repository.entity;
// Generated 17 de out de 2020 18:06:22 by Hibernate Tools 5.4.18.Final

import java.util.HashSet;
import java.util.Set;

/**
 * TipoPessoa generated by hbm2java
 */
public class TipoPessoa implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6041963671135595834L;
	private int codigo;
	private String nome;
	private Set condominioPessoses = new HashSet(0);

	public TipoPessoa() {
	}

	public TipoPessoa(int codigo) {
		this.codigo = codigo;
	}

	public TipoPessoa(int codigo, String nome, Set condominioPessoses) {
		this.codigo = codigo;
		this.nome = nome;
		this.condominioPessoses = condominioPessoses;
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set getCondominioPessoses() {
		return this.condominioPessoses;
	}

	public void setCondominioPessoses(Set condominioPessoses) {
		this.condominioPessoses = condominioPessoses;
	}

}
