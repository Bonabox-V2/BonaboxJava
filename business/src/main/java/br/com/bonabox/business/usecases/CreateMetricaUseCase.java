package br.com.bonabox.business.usecases;


import br.com.bonabox.business.domain.Metrica;
import br.com.bonabox.business.usecases.ex.EntregadorUseCaseException;

public interface CreateMetricaUseCase {

	Metrica execute(Metrica metrica) throws EntregadorUseCaseException;
	
}
