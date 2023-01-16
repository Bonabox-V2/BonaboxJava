package br.com.bonabox.business.domain.webclient;

import java.time.LocalDateTime;

public class EntregaDataWebClientResponse {

	private String entregaId;
	private Integer codigoSituacaoEntrega;
	private Integer codigoBox;
	private Integer codigoEmpresaEntregdora;
	private EmpresaEntregaDataWeb empresaEntrega;
	private LocalDateTime dataHoraCriacao;
	private Integer codigoEntregador;
	private LocalDateTime dataHoraFinalizacao;
	private Integer codigoCondominio;
	private Integer codigoBloco;
	private Integer codigoUnidade;
	private Integer codigoNumeroPortaBox;

	public String getEntregaId() {
		return entregaId;
	}

	public void setEntregaId(String entregaId) {
		this.entregaId = entregaId;
	}

	public Integer getCodigoSituacaoEntrega() {
		return codigoSituacaoEntrega;
	}

	public void setCodigoSituacaoEntrega(Integer codigoSituacaoEntrega) {
		this.codigoSituacaoEntrega = codigoSituacaoEntrega;
	}

	public Integer getCodigoBox() {
		return codigoBox;
	}

	public void setCodigoBox(Integer codigoBox) {
		this.codigoBox = codigoBox;
	}

	public Integer getCodigoEmpresaEntregdora() {
		return codigoEmpresaEntregdora;
	}

	public void setCodigoEmpresaEntregdora(Integer codigoEmpresaEntregadora) {
		this.codigoEmpresaEntregdora = codigoEmpresaEntregadora;
	}

	public EmpresaEntregaDataWeb getEmpresaEntrega() {
		return empresaEntrega;
	}

	public void setEmpresaEntrega(EmpresaEntregaDataWeb empresaEntrega) {
		this.empresaEntrega = empresaEntrega;
	}

	public LocalDateTime getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}

	public Integer getCodigoEntregador() {
		return codigoEntregador;
	}

	public void setCodigoEntregador(Integer codigoEntregador) {
		this.codigoEntregador = codigoEntregador;
	}

	public LocalDateTime getDataHoraFinalizacao() {
		return dataHoraFinalizacao;
	}

	public void setDataHoraFinalizacao(LocalDateTime dataHoraFinalizacao) {
		this.dataHoraFinalizacao = dataHoraFinalizacao;
	}

	public Integer getCodigoCondominio() {
		return codigoCondominio;
	}

	public void setCodigoCondominio(Integer codigoCondominio) {
		this.codigoCondominio = codigoCondominio;
	}

	public Integer getCodigoBloco() {
		return codigoBloco;
	}

	public void setCodigoBloco(Integer codigoBloco) {
		this.codigoBloco = codigoBloco;
	}

	public Integer getCodigoUnidade() {
		return codigoUnidade;
	}

	public void setCodigoUnidade(Integer codigoUnidade) {
		this.codigoUnidade = codigoUnidade;
	}

	public Integer getCodigoNumeroPortaBox() {
		return codigoNumeroPortaBox;
	}

	public void setCodigoNumeroPortaBox(Integer codigoNumeroPortaBox) {
		this.codigoNumeroPortaBox = codigoNumeroPortaBox;
	}

}