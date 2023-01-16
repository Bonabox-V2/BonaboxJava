package br.com.bonabox.business.domain.webclient;

import java.time.LocalDateTime;

public class EntregadorDataWebClient {
	private int entregadorId;
	private String ddi;
	private String ddd;
	private String telefone;
	private String nome;
	private LocalDateTime dataHoraCadastro;
	private Integer boxOrigemCadastro;

	public EntregadorDataWebClient() {

	}

	public EntregadorDataWebClient(int entregadorId, String ddi, String ddd, String telefone, String nome,
			LocalDateTime dataHoraCadastro, Integer boxOrigemCadastro) {
		super();
		this.entregadorId = entregadorId;
		this.ddi = ddi;
		this.ddd = ddd;
		this.telefone = telefone;
		this.nome = nome;
		this.dataHoraCadastro = dataHoraCadastro;
		this.boxOrigemCadastro = boxOrigemCadastro;
	}

	public Integer getBoxOrigemCadastro() {
		return boxOrigemCadastro;
	}

	public void setBoxOrigemCadastro(Integer numeroSerial) {
		this.boxOrigemCadastro = numeroSerial;
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
