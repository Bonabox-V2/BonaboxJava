package br.com.bonabox.business.usecases;


import br.com.bonabox.business.domain.Retirada;
import br.com.bonabox.business.usecases.ex.RetiradaUseCaseException;

public interface CreateRetiradaUseCase {

	Retirada execute(String numeroSerial, String codigoAcesso) throws RetiradaUseCaseException;

	String execute(String token) throws RetiradaUseCaseException;

	String executeByToken(String numeroSerial, String token) throws RetiradaUseCaseException;

	String executeByTokenPin(String numeroSerial, String tokenPin) throws RetiradaUseCaseException;

	boolean liberarPorta(String numeroSerial, String codigoCompartimentoRetirada, int boxId, String entregaId,
			String origem) throws RetiradaUseCaseException;

	boolean processarAberturaDePorta(String numeroSerial, String codigoCompartimentoRetirada, String type, String origem) throws RetiradaUseCaseException;
}
