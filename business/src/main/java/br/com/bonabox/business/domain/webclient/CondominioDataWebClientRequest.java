package br.com.bonabox.business.domain.webclient;

import java.util.Set;

public class CondominioDataWebClientRequest {

	private int condominioId;
	private String nome;
	private double areaTotalMetro2;
	private boolean portariaVirtual;
	private boolean habilitadoCorreios;
	private Set<CondominioEnderecoDataWebClientResponse> enderecos;

	public CondominioDataWebClientRequest() {

	}

	public CondominioDataWebClientRequest(int condominioId, String nome, double areaTotalMetro2,
			boolean portariaVirtual, boolean habilitadoCorreios,
			Set<CondominioEnderecoDataWebClientResponse> enderecos) {
		super();
		this.condominioId = condominioId;
		this.nome = nome;
		this.areaTotalMetro2 = areaTotalMetro2;
		this.portariaVirtual = portariaVirtual;
		this.habilitadoCorreios = habilitadoCorreios;
		this.enderecos = enderecos;
	}

	public Set<CondominioEnderecoDataWebClientResponse> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<CondominioEnderecoDataWebClientResponse> enderecos) {
		this.enderecos = enderecos;
	}

	public int getCondominioId() {
		return condominioId;
	}

	public void setCondominioId(int condominioId) {
		this.condominioId = condominioId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getAreaTotalMetro2() {
		return areaTotalMetro2;
	}

	public void setAreaTotalMetro2(double areaTotalMetro2) {
		this.areaTotalMetro2 = areaTotalMetro2;
	}

	public boolean isPortariaVirtual() {
		return portariaVirtual;
	}

	public void setPortariaVirtual(boolean portariaVirtual) {
		this.portariaVirtual = portariaVirtual;
	}

	public boolean isHabilitadoCorreios() {
		return habilitadoCorreios;
	}

	public void setHabilitadoCorreios(boolean habilitadoCorreios) {
		this.habilitadoCorreios = habilitadoCorreios;
	}
}
