package br.com.bonabox.condominio.api.domain.repository.entity;

import javax.persistence.*;

@Entity(name = "CondominioEnderecosEntity")
@Table(name = "condominio_enderecos")
public class CondominioEnderecosEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3028331476518459524L;
	// private CondominioEnderecosId id;

	@Column(name = "cep")
	private String cep;

	@Column(name = "logradouro")
	private String logradouro;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "cidade")
	private String cidade;

	@Column(name = "uf")
	private String uf;

	@Column(name = "numero")
	private String numero;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "codigo_condominio")
	private CondominioEntity condominio;

	public CondominioEnderecosEntity() {
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public CondominioEntity getCondominio() {
		return condominio;
	}

	public void setCondominio(CondominioEntity condominio) {
		this.condominio = condominio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
