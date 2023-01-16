package br.com.bonabox.business.usecases.ex;

import org.springframework.http.HttpStatus;

public class RetiradaUseCaseException extends BaseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6310804626447831216L;

	public RetiradaUseCaseException(BaseException e) {
		super(e, e.getHttpStatus());
	}
	
	public RetiradaUseCaseException(String mensagem) {
		super(mensagem, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public RetiradaUseCaseException(Exception e) {
		super(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public RetiradaUseCaseException(String mensagem, HttpStatus httpStatus) {
		super(mensagem, httpStatus);
	}
	
	/*@Override
	public String getMessage() {
		return "Erro ao executar método";
	}*/

}
