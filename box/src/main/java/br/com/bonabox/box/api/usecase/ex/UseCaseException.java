package br.com.bonabox.box.api.usecase.ex;

public class UseCaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5520791417298965346L;

	public UseCaseException() {
		
	}
	
	public UseCaseException(Exception e) {
		super(e);
	}
	
	public UseCaseException(Exception e, String mensagem) {
		super(mensagem, e);
	}
	
}
