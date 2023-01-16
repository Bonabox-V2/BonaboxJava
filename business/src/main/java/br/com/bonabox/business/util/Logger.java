package br.com.bonabox.business.util;


import br.com.bonabox.business.domain.Mensagem;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class Logger {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

	public static void exceptionLogger(Exception ex) {
		try {
			logger.info("Erro informado " + ex.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void exceptionLogger(Exception ex, HttpStatus status) {
		try {
			logger.info("Erro informado " + ex.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void exceptionLogger(HttpStatus status, String mensagem) {
		try {
			logger.info("Erro informado " + mensagem);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void exceptionLogger(Exception ex, HttpStatus status, String mensagem) {
		try {
			logger.info("Erro informado " + ex.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void exceptionLogger(Mensagem mensagem) {
		try {
			logger.info(mensagem.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
