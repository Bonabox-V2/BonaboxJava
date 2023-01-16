package br.com.bonabox.business.usecases;


import br.com.bonabox.business.domain.Bloco;
import br.com.bonabox.business.usecases.ex.BlocoUseCaseException;

import java.util.List;

public interface GetCondominioBlocoUseCase {

	List<Bloco> execute(String numeroSerialBox, Integer alaId) throws BlocoUseCaseException;
	
	Bloco execute(Integer blocoId) throws BlocoUseCaseException;
	
}
