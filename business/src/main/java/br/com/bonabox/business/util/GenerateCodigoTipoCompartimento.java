package br.com.bonabox.business.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GenerateCodigoTipoCompartimento implements IGenerateCodigo {

	@Override
	public String generateCodigo(final int length) {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).concat("-")
				.concat(RandomStringUtils.randomAlphanumeric(length)).toUpperCase();
	}

}
