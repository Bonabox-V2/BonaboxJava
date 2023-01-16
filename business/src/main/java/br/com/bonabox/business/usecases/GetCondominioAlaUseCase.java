package br.com.bonabox.business.usecases;


import br.com.bonabox.business.domain.Ala;
import br.com.bonabox.business.usecases.ex.AlaUseCaseException;

import java.util.List;

public interface GetCondominioAlaUseCase {

	List<Ala> execute(String numeroSerialBox) throws AlaUseCaseException;
	
}
