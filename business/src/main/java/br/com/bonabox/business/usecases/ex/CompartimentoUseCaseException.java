package br.com.bonabox.business.usecases.ex;


import br.com.bonabox.business.domain.Mensagem;
import org.springframework.http.HttpStatus;

public class CompartimentoUseCaseException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -200507891277187166L;

	public CompartimentoUseCaseException(BaseException e) {
		super(e, e.getMessage(), e.getHttpStatus());
	}
	
	public CompartimentoUseCaseException(Exception e, String mensagem, HttpStatus httpStatus) {
		super(e, mensagem, httpStatus);
	}

	public CompartimentoUseCaseException(String mensagem, HttpStatus httpStatus) {
		super(mensagem, httpStatus);
	}
	
	public CompartimentoUseCaseException(String mensagem) {
		super(mensagem, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public CompartimentoUseCaseException(Mensagem mensagem) {
		super(mensagem);
	}
}
