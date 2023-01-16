package br.com.bonabox.business.usecases;


import br.com.bonabox.business.api.models.GetEnderecoBoxResponse;
import br.com.bonabox.business.usecases.ex.CompartimentoUseCaseException;

import java.util.Set;

public interface GetBoxUseCase {

	Set<GetEnderecoBoxResponse> execute(String numeroSerial) throws CompartimentoUseCaseException;
	
}
