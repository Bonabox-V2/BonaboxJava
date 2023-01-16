package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.api.models.GetEnderecoBoxResponse;
import br.com.bonabox.business.dataproviders.BoxDataProvider;
import br.com.bonabox.business.dataproviders.CondominioDataProvider;
import br.com.bonabox.business.dataproviders.data.BoxDataResponse;
import br.com.bonabox.business.dataproviders.data.BoxInstalacaoDataResponse;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.CondominioDataWebClientRequest;
import br.com.bonabox.business.usecases.GetBoxUseCase;
import br.com.bonabox.business.usecases.ex.CompartimentoUseCaseException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GetBoxUseCaseImpl implements GetBoxUseCase {

	private final BoxDataProvider boxDataProvider;
	private final CondominioDataProvider condominioDataProvider;

	public GetBoxUseCaseImpl(BoxDataProvider boxDataProvider, CondominioDataProvider condominioDataProvider) {
		this.boxDataProvider = boxDataProvider;
		this.condominioDataProvider = condominioDataProvider;
	}

	@Override
	public Set<GetEnderecoBoxResponse> execute(String numeroSerial) throws CompartimentoUseCaseException {

		try {
			BoxDataResponse box = boxDataProvider.getByNumeroSerialAndTipo(numeroSerial, "PRINCIPAL");

			BoxInstalacaoDataResponse boxInstalacao = boxDataProvider.getBoxInstalacao(box.getBoxId());

			CondominioDataWebClientRequest condominioDataWebClientRequest = condominioDataProvider
					.consultarCondominio(boxInstalacao.getCodigoCondominio());

			return condominioDataWebClientRequest.getEnderecos().stream().map(m -> {
				return new GetEnderecoBoxResponse(m.getCep(), m.getLogradouro(), m.getComplemento(), m.getBairro(),
						m.getCidade(), m.getUf(), m.getNumero());
			}).collect(Collectors.toSet());

		} catch (DataProviderException e) {
			e.printStackTrace();
		}

		return null;
	}

}