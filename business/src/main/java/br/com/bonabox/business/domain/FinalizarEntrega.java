package br.com.bonabox.business.domain;

public class FinalizarEntrega {

	private final String codigoValidacao;
	private final String data;

	public FinalizarEntrega(String codigoValidacao, String data) {
		super();
		this.codigoValidacao = codigoValidacao;
		this.data = data;
	}

	public String getCodigoValidacao() {
		return codigoValidacao;
	}

	public String getData() {
		return data;
	}

}