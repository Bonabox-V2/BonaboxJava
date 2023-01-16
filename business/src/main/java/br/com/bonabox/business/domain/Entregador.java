package br.com.bonabox.business.domain;

import java.time.LocalDateTime;

public class Entregador {

	private int entregadorId;
	private String ddi;
	private String ddd;
	private String telefone;
	private String nome;
	private LocalDateTime dataHoraCadastro;
	private String numeroSerial;

	public Entregador() {

	}

	public Entregador(int entregadorId, String ddi, String ddd, String telefone, String nome,
			LocalDateTime dataHoraCadastro, String numeroSerial) {
		super();
		this.entregadorId = entregadorId;
		this.ddi = ddi;
		this.ddd = ddd;
		this.telefone = telefone;
		this.dataHoraCadastro = dataHoraCadastro;
		this.nome = nome;
		this.numeroSerial = numeroSerial;
	}

	public String getNumeroSerial() {
		return numeroSerial;
	}

	public void setNumeroSerial(String numeroSerial) {
		this.numeroSerial = numeroSerial;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getEntregadorId() {
		return entregadorId;
	}

	public void setEntregadorId(int entregadorId) {
		this.entregadorId = entregadorId;
	}

	public String getDdi() {
		return ddi;
	}

	public void setDdi(String ddi) {
		this.ddi = ddi;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDateTime getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

}
