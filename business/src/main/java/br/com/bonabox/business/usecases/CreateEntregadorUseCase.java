package br.com.bonabox.business.usecases;


import br.com.bonabox.business.domain.Entregador;
import br.com.bonabox.business.usecases.ex.EntregadorUseCaseException;

public interface CreateEntregadorUseCase {

	Entregador execute(Entregador entregador) throws EntregadorUseCaseException;
	
}
