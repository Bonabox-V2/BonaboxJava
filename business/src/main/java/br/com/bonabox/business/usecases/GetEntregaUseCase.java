package br.com.bonabox.business.usecases;


import br.com.bonabox.business.domain.Entrega;
import br.com.bonabox.business.usecases.ex.EntregaUseCaseException;

public interface GetEntregaUseCase {

	Entrega execute(String codigoEntrega) throws EntregaUseCaseException;
	
}
