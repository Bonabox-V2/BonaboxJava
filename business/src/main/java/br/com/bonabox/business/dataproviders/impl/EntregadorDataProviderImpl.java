package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.EntregadorDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.EntregadorDataWebClient;
import br.com.bonabox.business.domain.webclient.EntregadorDataWebClientResponse;
import br.com.bonabox.business.usecases.ex.BaseException;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EntregadorDataProviderImpl implements EntregadorDataProvider {

	@Value("${interno.bonabox.resource.entregador}")
	private String entregadorResource;

	private final WebClientBonabox<EntregadorDataWebClientResponse> webClientBonabox;

	private DataMDC dataMDC;
	
	public EntregadorDataProviderImpl(@Qualifier("interno-bonabox") WebClient webClient,
			WebClientBonabox<EntregadorDataWebClientResponse> webClientBonabox,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter,
			@Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker) {
		this.webClientBonabox = webClientBonabox;

		this.webClientBonabox.build(webClient).transform(bulkhead).transform(timeLimiter).transform(circuitBreaker);
	}

	@Override
	public EntregadorDataWebClientResponse criar(EntregadorDataWebClient entrada) throws DataProviderException {

		try {
			
			Mono<EntregadorDataWebClientResponse> mono = webClientBonabox.build(dataMDC).post(entrada,
					EntregadorDataWebClientResponse.class, entregadorResource);

			return mono.block();
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}
	
	@Override
	public EntregadorDataWebClientResponse consultar(int entregadorId) throws DataProviderException {

		try {
			
			Mono<EntregadorDataWebClientResponse> mono = webClientBonabox.build(dataMDC).get(url -> url.path("/entregador/{entregador-id}").build(entregadorId), EntregadorDataWebClientResponse.class);

			return mono.block();
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public EntregadorDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}

}
