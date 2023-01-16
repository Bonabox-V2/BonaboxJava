package br.com.bonabox.business.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GenerateCodigoEntrega implements IGenerateCodigo {

	@Override
	public String generateCodigo(final int length) {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")).concat("-")
				.concat(RandomStringUtils.randomAlphanumeric(length)).concat("-")
				.concat(RandomStringUtils.randomNumeric(1)).toUpperCase();
	}

}
