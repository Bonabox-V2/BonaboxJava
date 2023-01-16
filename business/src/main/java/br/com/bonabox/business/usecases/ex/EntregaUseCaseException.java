package br.com.bonabox.business.usecases.ex;

import org.springframework.http.HttpStatus;

public class EntregaUseCaseException extends BaseException {

	private static final long serialVersionUID = 5889772386331964798L;
	
	private static final String defaultMensagem = "Erro ao executar entrega";
	
	public EntregaUseCaseException(BaseException e) {
		super(e, e.getHttpStatus());
	}
	
	public EntregaUseCaseException(String mensagem) {
		super(mensagem, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public EntregaUseCaseException(Exception e) {
		super(e, defaultMensagem, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public EntregaUseCaseException(String mensagem, HttpStatus httpStatus) {
		super(mensagem, httpStatus);
	}

}
