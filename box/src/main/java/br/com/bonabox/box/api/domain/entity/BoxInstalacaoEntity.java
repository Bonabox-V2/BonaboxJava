package br.com.bonabox.box.api.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "BoxInstalacaoEntity")
@Table(name = "box_install")
public class BoxInstalacaoEntity implements Serializable {

	private static final long serialVersionUID = 612753528297642640L;

	@Id
	@Column(name = "codigo_instalacao", unique = true)
	private Integer codigoInstalacao;

	@Column(name = "codigo_box", unique = true)
	private Integer codigoBox;

	@Column(name = "codigo_empresa_responsavel")
	private Integer codigoEmpresaResponsavel;

	@Column(name = "codigo_condominio")
	private Integer codigoCondominio;

	@Column(name = "localizacao")
	private String localizacao;

	@Column(name = "json_info")
	private String jsonInfo;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "ativa")
	private boolean ativa;

	@Column(name = "tipoRede")
	private String tipo_rede;

	@Column(name = "nome_rede")
	private String nomeRede;

	@Column(name = "data_instalacao")
	private LocalDateTime dataInstalacao;

	@Column(name = "data_atualizacao")
	private LocalDateTime dataAtualizacao;
	
	@Column(name = "condominio_codigo_endereco")
	private Integer condominioCodigoEndereco;

	public Integer getCodigoBox() {
		return codigoBox;
	}

	public void setCodigoBox(Integer codigoBox) {
		this.codigoBox = codigoBox;
	}

	public Integer getCodigoEmpresaResponsavel() {
		return codigoEmpresaResponsavel;
	}

	public void setCodigoEmpresaResponsavel(Integer codigoEmpresaResponsavel) {
		this.codigoEmpresaResponsavel = codigoEmpresaResponsavel;
	}

	public Integer getCodigoInstalacao() {
		return codigoInstalacao;
	}

	public void setCodigoInstalacao(Integer codigoInstalacao) {
		this.codigoInstalacao = codigoInstalacao;
	}

	public Integer getCodigoCondominio() {
		return codigoCondominio;
	}

	public void setCodigoCondominio(Integer codigoCondominio) {
		this.codigoCondominio = codigoCondominio;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getJsonInfo() {
		return jsonInfo;
	}

	public void setJsonInfo(String jsonInfo) {
		this.jsonInfo = jsonInfo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public String getTipo_rede() {
		return tipo_rede;
	}

	public void setTipo_rede(String tipo_rede) {
		this.tipo_rede = tipo_rede;
	}

	public String getNomeRede() {
		return nomeRede;
	}

	public void setNomeRede(String nomeRede) {
		this.nomeRede = nomeRede;
	}

	public LocalDateTime getDataInstalacao() {
		return dataInstalacao;
	}

	public void setDataInstalacao(LocalDateTime dataInstalacao) {
		this.dataInstalacao = dataInstalacao;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Integer getCondominioCodigoEndereco() {
		return condominioCodigoEndereco;
	}

	public void setCondominioCodigoEndereco(Integer condominioCodigoEndereco) {
		this.condominioCodigoEndereco = condominioCodigoEndereco;
	}

}
