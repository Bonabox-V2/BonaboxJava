package br.com.bonabox.business.usecases.ex;

import org.springframework.http.HttpStatus;

public class BlocoUseCaseException extends BaseException {

	private static final long serialVersionUID = -9050414265808952932L;

	public BlocoUseCaseException(BaseException e) {
		super(e, e.getHttpStatus());
	}

	public BlocoUseCaseException(String mensagem) {
		super(mensagem, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public BlocoUseCaseException(Exception e) {
		super(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public BlocoUseCaseException(String mensagem, HttpStatus httpStatus) {
		super(mensagem, httpStatus);
	}

	@Override
	public String getMessage() {
		return "Erro ao executar método";
	}

}
