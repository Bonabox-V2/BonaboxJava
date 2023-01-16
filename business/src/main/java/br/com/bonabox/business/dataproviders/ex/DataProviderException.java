package br.com.bonabox.business.dataproviders.ex;


import br.com.bonabox.business.usecases.ex.BaseException;
import org.springframework.http.HttpStatus;

public class DataProviderException extends BaseException {

	private static final long serialVersionUID = -6348372615256959272L;
	
	public DataProviderException(Exception e) {
		super(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public DataProviderException(BaseException e) {
		super(e);
	}
	
	public DataProviderException(Exception e, HttpStatus httpStatus) {
		super(e, httpStatus);
	}
	
	public DataProviderException(String mensagem, HttpStatus httpStatus) {
		super(mensagem, httpStatus);
	}
	
	public DataProviderException(String mensagem, Exception e, HttpStatus httpStatus) {
		super(e, mensagem, httpStatus);
	}

}
