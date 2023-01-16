package br.com.bonabox.box.api.domain.entity;
// Generated 17 de out de 2020 18:05:14 by Hibernate Tools 5.4.18.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Fabricante generated by hbm2java
 */
@Entity(name = "FabricanteEntity")
@Table(name = "fabricante")
public class FabricanteEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6603137445168478188L;
	
	@Id
	@Column(name = "codigo", unique = true)
	private int codigo;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "nome_representante")
	private String nomeRepresentante;
	
	@Column(name = "contato")
	private String contato;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "documento")
	private String documento;
	
	//private Set boxes = new HashSet(0);
	//private Set devices = new HashSet(0);

	public FabricanteEntity() {
	}

	public FabricanteEntity(int codigo) {
		this.codigo = codigo;
	}

	public FabricanteEntity(int codigo, String nome, String nomeRepresentante, String contato, String tipo, String documento
			//,Set boxes, Set devices
			) {
		this.codigo = codigo;
		this.nome = nome;
		this.nomeRepresentante = nomeRepresentante;
		this.contato = contato;
		this.tipo = tipo;
		this.documento = documento;
		//this.boxes = boxes;
		//this.devices = devices;
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

	public String getNomeRepresentante() {
		return this.nomeRepresentante;
	}

	public void setNomeRepresentante(String nomeRepresentante) {
		this.nomeRepresentante = nomeRepresentante;
	}

	public String getContato() {
		return this.contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDocumento() {
		return this.documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/*public Set getBoxes() {
		return this.boxes;
	}

	public void setBoxes(Set boxes) {
		this.boxes = boxes;
	}

	public Set getDevices() {
		return this.devices;
	}

	public void setDevices(Set devices) {
		this.devices = devices;
	}*/

}
