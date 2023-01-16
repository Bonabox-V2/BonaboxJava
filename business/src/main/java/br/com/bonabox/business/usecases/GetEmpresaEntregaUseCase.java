package br.com.bonabox.business.usecases;


import br.com.bonabox.business.domain.EmpresaEntrega;
import br.com.bonabox.business.usecases.ex.EntregaUseCaseException;

import java.util.List;

public interface GetEmpresaEntregaUseCase {

	List<EmpresaEntrega> execute(String numeroSerial) throws EntregaUseCaseException;
	
}
