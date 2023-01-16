package br.com.bonabox.business.util;

import org.springframework.stereotype.Component;

@Component
public final class GenerateUIDImpl implements GenerateUID {

	private IGenerateCodigo generateCodigo;

	private int length = 6;

	public GenerateUIDImpl(int length) {
		this.length = length;
	}

	public GenerateUIDImpl() {
	}
	
	public GenerateUID build(IGenerateCodigo generateCodigo) {
		this.generateCodigo = generateCodigo;
		return this;
	}

	public final String generatingCodigo(final int length) {
		return generateCodigo.generateCodigo(length);
	}

	public final String generatingCodigo() {
		return generateCodigo.generateCodigo(length);
	}
}
