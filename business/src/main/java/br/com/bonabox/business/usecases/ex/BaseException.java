package br.com.bonabox.business.usecases.ex;


import br.com.bonabox.business.domain.Mensagem;
import br.com.bonabox.business.util.Logger;
import org.springframework.http.HttpStatus;
import software.amazon.awssdk.http.HttpStatusCode;

public class BaseException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4665508910851138519L;

	private HttpStatus httpStatus;
	private final Mensagem mensagem;

	public BaseException(Exception e) {
		super(e);
		this.mensagem = new Mensagem("C1", e.getMessage(), "");
		Logger.exceptionLogger(mensagem);
	}
	
	public BaseException(BaseException e) {
		this(e, e.getMessage(), e.getHttpStatus());
		//this.mensagem = new Mensagem("C2", e.getMessage(), "");
		//Logger.exceptionLogger(mensagem);
	}

	public BaseException(String mensagem) {
		super(mensagem);
		this.mensagem = new Mensagem("C3", mensagem, "");
		//Logger.exceptionLogger(this.mensagem);
	}

	public BaseException(Exception e, String mensagem, HttpStatus httpStatus) {
		super(mensagem, e);
		this.httpStatus = httpStatus;
		this.mensagem = new Mensagem("C4", mensagem, httpStatus.toString());
		//Logger.exceptionLogger(this.mensagem);
	}

	public BaseException(String mensagem, HttpStatus httpStatus) {
		super(mensagem);
		this.httpStatus = httpStatus;
		this.mensagem = new Mensagem("C5", mensagem, "");
		//Logger.exceptionLogger(this.mensagem);
	}

	public BaseException(Exception e, HttpStatus httpStatus) {
		super(e);
		this.httpStatus = httpStatus;
		this.mensagem = new Mensagem("C6", e.getMessage(), httpStatus.toString());
		//Logger.exceptionLogger(mensagem);
	}
	
	public BaseException(Mensagem mensagem) {
		super();
		this.mensagem = mensagem;
		//Logger.exceptionLogger(mensagem);
		//Fazer busca para traduzir mensagem
	}

	public BaseException(Exception e, HttpStatusCode statusCode) {
		super(e);
		this.httpStatus = HttpStatus.valueOf(statusCode.toString());
		this.mensagem = new Mensagem("C7", e.getMessage(), httpStatus.toString());
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	@Override
	public String getMessage(){
		return mensagem.toString();
	}

}
