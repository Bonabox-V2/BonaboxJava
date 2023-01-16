package br.com.bonabox.business.dataproviders.data;

import java.time.LocalDateTime;

public class BoxInstalacaoDataResponse {

	private Integer codigoInstalacao;
	private Integer codigoBox;
	private Integer codigoEmpresaResponsavel;
	private Integer codigoCondominio;
	private String localizacao;
	private String jsonInfo;
	private String descricao;
	private boolean ativa;
	private String tipo_rede;
	private String nomeRede;
	private LocalDateTime dataInstalacao;
	private LocalDateTime dataAtualizacao;
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
