package br.com.bonabox.business.usecases.ex;

import org.springframework.http.HttpStatus;

public class EntregadorUseCaseException extends BaseException {

	private static final long serialVersionUID = 5889772386331964798L;
	
	private static final String defaultMensagem = "Erro ao executar entregador";
	
	public EntregadorUseCaseException() {
		super(defaultMensagem, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public EntregadorUseCaseException(BaseException e) {
		super(e, e.getMessage(), e.getHttpStatus());
	}
	
	public EntregadorUseCaseException(String mensagem) {
		super(mensagem, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public EntregadorUseCaseException(Exception e) {
		super(e, defaultMensagem, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public EntregadorUseCaseException(String mensagem, HttpStatus httpStatus) {
		super(mensagem, httpStatus);
	}

}
