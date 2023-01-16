package br.com.bonabox.business.usecases;


import br.com.bonabox.business.domain.FinalizarEntrega;
import br.com.bonabox.business.usecases.ex.EntregaUseCaseException;

public interface UpdateFinalizarEntregaUseCase {

	FinalizarEntrega execute(String codigoEntrega, String codigo) throws EntregaUseCaseException;

}
