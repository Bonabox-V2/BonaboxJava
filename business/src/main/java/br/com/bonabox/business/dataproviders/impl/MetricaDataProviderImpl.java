package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.dataproviders.MetricaDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.MetricaDataWebClientRequest;
import br.com.bonabox.business.domain.webclient.MetricaDataWebClientResponse;
import br.com.bonabox.business.usecases.ex.BaseException;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MetricaDataProviderImpl implements MetricaDataProvider {
	private final WebClientBonabox<MetricaDataWebClientResponse> webClientBonabox;

	public MetricaDataProviderImpl(@Qualifier("interno-bonabox") WebClient webClient,
			WebClientBonabox<MetricaDataWebClientResponse> webClientBonabox,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead, @Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter,
			@Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker) {
		this.webClientBonabox = webClientBonabox.build(webClient).transform(bulkhead).transform(timeLimiter).transform(circuitBreaker);
	}

	@Override
	public MetricaDataWebClientResponse criar(MetricaDataWebClientRequest metrica) throws DataProviderException {

		try {

			Mono<MetricaDataWebClientResponse> mono = webClientBonabox.post(metrica, MetricaDataWebClientResponse.class,
					"/metrica");

			return mono.block();
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public MetricaDataWebClientResponse consultar(int metricaId) throws DataProviderException {

		try {

			Mono<MetricaDataWebClientResponse> mono = webClientBonabox.get(
					url -> url.path("/metrica/{metrica-id}").build(metricaId),
					MetricaDataWebClientResponse.class);

			return mono.block();
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

}
