package br.com.bonabox.business.usecases;


import br.com.bonabox.business.domain.StatusEntrega;
import br.com.bonabox.business.usecases.ex.EntregaUseCaseException;

public interface UpdateEntregaUseCase {

	StatusEntrega execute(StatusEntrega entrega) throws EntregaUseCaseException;
}
