package br.com.bonabox.business.api.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UpdateStatusEntregaDataRequest {

	@Size(min = 1, max = 32)
	@NotNull
	private String entregaId;
	private Integer statusEntregaId;
	private Integer situacaoId;
	private Integer codigoEntregador;
	private LocalDateTime dataHora;
	private Integer codigoAla;
	private Integer codigoBloco;
	private Integer codigoUnidade;
	private Integer codigoNumeroPortaBox;
	private String nomeMorador;

	public UpdateStatusEntregaDataRequest() {
	}

	public UpdateStatusEntregaDataRequest(@Size(min = 1, max = 32) @NotNull String entregaId, Integer statusEntregaId,
			Integer situacaoId, Integer codigoEntregador, LocalDateTime dataHora, Integer codigoAla,
			Integer codigoBloco, Integer codigoUnidade, Integer codigoNumeroPortaBox, String nomeMorador) {
		super();
		this.entregaId = entregaId;
		this.statusEntregaId = statusEntregaId;
		this.situacaoId = situacaoId;
		this.codigoEntregador = codigoEntregador;
		this.dataHora = dataHora;
		this.codigoAla = codigoAla;
		this.codigoBloco = codigoBloco;
		this.codigoUnidade = codigoUnidade;
		this.codigoNumeroPortaBox = codigoNumeroPortaBox;
		this.nomeMorador = nomeMorador;
	}

	public String getNomeMorador() {
		return nomeMorador;
	}

	public void setNomeMorador(String nomeMorador) {
		this.nomeMorador = nomeMorador;
	}

	public Integer getCodigoAla() {
		return codigoAla;
	}

	public void setCodigoAla(Integer codigoAla) {
		this.codigoAla = codigoAla;
	}

	public String getEntregaId() {
		return entregaId;
	}

	public void setEntregaId(String entregaId) {
		this.entregaId = entregaId;
	}

	public Integer getStatusEntregaId() {
		return statusEntregaId;
	}

	public void setStatusEntregaId(Integer statusEntregaId) {
		this.statusEntregaId = statusEntregaId;
	}

	public Integer getSituacaoId() {
		return situacaoId;
	}

	public void setSituacaoId(Integer situacaoId) {
		this.situacaoId = situacaoId;
	}

	public Integer getCodigoEntregador() {
		return codigoEntregador;
	}

	public void setCodigoEntregador(Integer codigoEntregador) {
		this.codigoEntregador = codigoEntregador;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
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
