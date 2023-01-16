package br.com.bonabox.box.api.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "CompartimentoEntity")
@Table(name = "compartimento")
public class CompartimentoEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2254927566123760019L;

	@Id
	@Column(name = "codigo", unique = true)
	private int codigo;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "tamanho")
	private String tamanho;
	
	@OneToMany(mappedBy = "compartimento", cascade = CascadeType.ALL)
	private Set<BoxCompartimentosEntity> compartimentos;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
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
