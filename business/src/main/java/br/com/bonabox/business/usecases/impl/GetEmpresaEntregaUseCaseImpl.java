package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.dataproviders.EmpresaEntregaDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.EmpresaEntrega;
import br.com.bonabox.business.domain.webclient.EmpresaEntregaDataWebClient;
import br.com.bonabox.business.usecases.GetEmpresaEntregaUseCase;
import br.com.bonabox.business.usecases.ex.EntregaUseCaseException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetEmpresaEntregaUseCaseImpl implements GetEmpresaEntregaUseCase {

	private final EmpresaEntregaDataProvider empresaEntregaDataProvider;

	public GetEmpresaEntregaUseCaseImpl(EmpresaEntregaDataProvider empresaEntregaDataProvider) {
		this.empresaEntregaDataProvider = empresaEntregaDataProvider;
	}

	@Override
	public List<EmpresaEntrega> execute(String numeroSerial) throws EntregaUseCaseException {
		try {

			List<EmpresaEntregaDataWebClient> lista = empresaEntregaDataProvider.consultarTodos();

			return lista.stream().map(m -> {
				return new EmpresaEntrega(m.getEmpresaId(), m.getNome(), m.getRazaoSocial(), m.getResponsavel(),
						m.getTelefoneResponsavel(), m.getStatusEntrega());
			}).collect(Collectors.toList());

		} catch (DataProviderException e) {
			throw new EntregaUseCaseException(e);
		} finally {

		}
	}
}
