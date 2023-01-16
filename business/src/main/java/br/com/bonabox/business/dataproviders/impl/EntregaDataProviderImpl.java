package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.EntregaDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.EntregaDataWebClient;
import br.com.bonabox.business.domain.webclient.EntregaDataWebClientResponse;
import br.com.bonabox.business.usecases.ex.BaseException;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EntregaDataProviderImpl implements EntregaDataProvider {
	
	private final WebClientBonabox<EntregaDataWebClientResponse> webClientBonabox;

	private DataMDC dataMDC;
	
	public EntregaDataProviderImpl(@Qualifier("interno-bonabox") WebClient webClient,
			@Qualifier("retry.bonabox") Retry retry,
			@Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead, 
			WebClientBonabox<EntregaDataWebClientResponse> webClientBonabox) {
		
		this.webClientBonabox = webClientBonabox.build(webClient)
				.transform(bulkhead)
				.transform(circuitBreaker)
				.transform(retry)
				.transform(timeLimiter);
			
	}

	@Override
	public EntregaDataWebClientResponse criar(EntregaDataWebClient entregaDataRequest) throws DataProviderException {
		
		try {
			
			Mono<EntregaDataWebClientResponse> mono = webClientBonabox.build(dataMDC)
					.post(entregaDataRequest,
							EntregaDataWebClientResponse.class,
							"/entrega");
			
			/*Mono<EntregaDataWebClientResponse> mono = webClientInternoBonabox.post().uri("/entrega")
					.body(Mono.just(entregaDataRequest), EntregaDataWebClient.class).retrieve()
					.bodyToMono(EntregaDataWebClientResponse.class);*/

			return mono.block();

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public EntregaDataWebClientResponse consultar(String codigoEntrega) throws DataProviderException {
		
		try {
			
			Mono<EntregaDataWebClientResponse> mono = webClientBonabox.build(dataMDC)
				.get(uriBuilder -> uriBuilder.path("/entrega/{codigo_entrega}").build(codigoEntrega),
						EntregaDataWebClientResponse.class);
			
			/*Mono<EntregaDataWebClient> mono = webClientInternoBonabox.get()
					.uri(uriBuilder -> uriBuilder.path("/entrega/{codigo_entrega}").build(codigoEntrega)).retrieve()
					.bodyToMono(EntregaDataWebClient.class)
					.transform(CircuitBreakerOperator.of(circuitBreaker))
					.transform(RetryOperator.of(retry))
					.transform(BulkheadOperator.of(bulkhead))
					.transform(TimeLimiterOperator.of(timeLimiter));*/

			return mono.block();
			
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public EntregaDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}
}
