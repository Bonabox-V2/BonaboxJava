package br.com.bonabox.business.usecases.impl;


import br.com.bonabox.business.dataproviders.MetricaDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.Metrica;
import br.com.bonabox.business.domain.webclient.MetricaDataWebClientRequest;
import br.com.bonabox.business.usecases.CreateMetricaUseCase;
import br.com.bonabox.business.usecases.ex.EntregadorUseCaseException;
import org.springframework.stereotype.Service;

@Service
public class CreateMetricaUseCaseImpl implements CreateMetricaUseCase {

	private final MetricaDataProvider metricaDataProvider;

	public CreateMetricaUseCaseImpl(MetricaDataProvider metricaDataProvider) {
		this.metricaDataProvider = metricaDataProvider;
	}

	@Override
	public Metrica execute(Metrica metrica) throws EntregadorUseCaseException {
		try {

			metricaDataProvider.criar(new MetricaDataWebClientRequest(metrica.getCompartimentoId(), metrica.getState(),
					metrica.getSerialNumber()));

			return null;

		} catch (DataProviderException e) {
			throw new EntregadorUseCaseException(e);
		} catch (Exception e) {
			throw new EntregadorUseCaseException(e);
		} finally {

		}
	}
}
