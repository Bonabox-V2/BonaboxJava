package br.com.bonabox.condominio.api.domain.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Pessoa generated by hbm2java
 */
@Entity(name = "CondominioPessoasEntity")
@Table(name = "condominio_admin")
public class CondominioPessoasEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5225839288175193226L;

	@Id
	@Column(name = "codigo_composicao_condominio", unique = true)
	private Integer codigoComposicaoCondominio;

	@Column(name = "codigo_pessoa", unique = true)
	private Integer codigoPessoa;

	@Column(name = "codigo_tipo_pessoa", unique = true)
	private Integer codigoTipoPessoa;

	public Integer getCodigoComposicaoCondominio() {
		return codigoComposicaoCondominio;
	}

	public void setCodigoComposicaoCondominio(Integer codigoComposicaoCondominio) {
		this.codigoComposicaoCondominio = codigoComposicaoCondominio;
	}

	public Integer getCodigoPessoa() {
		return codigoPessoa;
	}

	public void setCodigoPessoa(Integer codigoPessoa) {
		this.codigoPessoa = codigoPessoa;
	}

	public Integer getCodigoTipoPessoa() {
		return codigoTipoPessoa;
	}

	public void setCodigoTipoPessoa(Integer codigoTipoPessoa) {
		this.codigoTipoPessoa = codigoTipoPessoa;
	}

}
