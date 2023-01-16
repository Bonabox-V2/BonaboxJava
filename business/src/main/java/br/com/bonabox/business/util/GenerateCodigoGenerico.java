package br.com.bonabox.business.util;

import org.apache.commons.lang3.RandomStringUtils;

public class GenerateCodigoGenerico implements IGenerateCodigo {

	@Override
	public String generateCodigo(final int length) {
		return RandomStringUtils.randomNumeric(length);
	}

}
