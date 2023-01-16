package br.com.bonabox.business.domain;

import java.util.Date;

public class TipoCompartimentoDisponivel {

	private int boxId;

	private String numeroSerial;

	private Date dataCadastro;

	private int codigoUserCadastro;

	private Date dataAtualizacao;

	private int codigoUserAtualizacao;

	private Date dataFabricacao;

	private String tipo;

	private int owner;
	
	public TipoCompartimentoDisponivel() {
		
	}

	public TipoCompartimentoDisponivel(int boxId, String numeroSerial, Date dataCadastro, int codigoUserCadastro, Date dataAtualizacao,
			int codigoUserAtualizacao, Date dataFabricacao, String tipo, int owner) {
		super();
		this.boxId = boxId;
		this.numeroSerial = numeroSerial;
		this.dataCadastro = dataCadastro;
		this.codigoUserCadastro = codigoUserCadastro;
		this.dataAtualizacao = dataAtualizacao;
		this.codigoUserAtualizacao = codigoUserAtualizacao;
		this.dataFabricacao = dataFabricacao;
		this.tipo = tipo;
		this.owner = owner;
	}

	public int getBoxId() {
		return boxId;
	}

	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}

	public String getNumeroSerial() {
		return numeroSerial;
	}

	public void setNumeroSerial(String numeroSerial) {
		this.numeroSerial = numeroSerial;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public int getCodigoUserCadastro() {
		return codigoUserCadastro;
	}

	public void setCodigoUserCadastro(int codigoUserCadastro) {
		this.codigoUserCadastro = codigoUserCadastro;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public int getCodigoUserAtualizacao() {
		return codigoUserAtualizacao;
	}

	public void setCodigoUserAtualizacao(int codigoUserAtualizacao) {
		this.codigoUserAtualizacao = codigoUserAtualizacao;
	}

	public Date getDataFabricacao() {
		return dataFabricacao;
	}

	public void setDataFabricacao(Date dataFabricacao) {
		this.dataFabricacao = dataFabricacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}
	
}
