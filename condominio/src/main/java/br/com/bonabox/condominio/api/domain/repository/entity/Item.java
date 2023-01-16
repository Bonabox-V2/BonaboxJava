package br.com.bonabox.condominio.api.domain.repository.entity;
// Generated 17 de out de 2020 18:06:22 by Hibernate Tools 5.4.18.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Item generated by hbm2java
 */
public class Item implements java.io.Serializable {

	private int codigo;
	private String nome;
	private Set ambienteItems = new HashSet(0);

	public Item() {
	}

	public Item(int codigo) {
		this.codigo = codigo;
	}

	public Item(int codigo, String nome, Set ambienteItems) {
		this.codigo = codigo;
		this.nome = nome;
		this.ambienteItems = ambienteItems;
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

	public Set getAmbienteItems() {
		return this.ambienteItems;
	}

	public void setAmbienteItems(Set ambienteItems) {
		this.ambienteItems = ambienteItems;
	}

}
