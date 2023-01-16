package br.com.bonabox.business.usecases;


import br.com.bonabox.business.domain.Unidade;
import br.com.bonabox.business.usecases.ex.UnidadeUseCaseException;

import java.util.List;

public interface GetCondominioUnidadeUseCase {

	List<Unidade> execute(String numeroSerialBox, Integer alaId, Integer blocoId) throws UnidadeUseCaseException;
	
	Unidade execute(Integer unidadeId) throws UnidadeUseCaseException;
	
}
