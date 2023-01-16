package br.com.bonabox.business.domain;

import org.apache.commons.lang3.RandomStringUtils;

public class Mensagem {

	private final String codigo;
	private final String mensagem;
	private final String descricao;
	private final String identificador;

	public Mensagem(String codigo, String mensagem, String descricao) {
		super();
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.descricao = descricao;
		this.identificador = RandomStringUtils.randomAlphanumeric(20);
	}

	public String getCodigo() {
		return codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return "{\"codigo\":\"" + codigo + "\", \"identificador\":\"" + identificador + "\", \"mensagem\":\"" + mensagem + "\", \"descricao\":\"" + descricao + "\"}";
	}
}
