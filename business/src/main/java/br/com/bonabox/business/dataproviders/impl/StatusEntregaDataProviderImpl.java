package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.dataproviders.StatusEntregaDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.StatusEntregaDataWebClient;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatusEntregaDataProviderImpl implements StatusEntregaDataProvider {

	private final WebClientBonabox<StatusEntregaDataWebClient> webClientBonabox;
	private final WebClientBonabox<StatusEntregaDataWebClient[]> webClientBonaboxArray;

	public StatusEntregaDataProviderImpl(@Qualifier("interno-bonabox") WebClient webClient,
			@Qualifier("retry.bonabox") Retry retry, @Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter, @Qualifier("bulkhead.bonabox") Bulkhead bulkhead,
			WebClientBonabox<StatusEntregaDataWebClient> webClientBonabox,
			WebClientBonabox<StatusEntregaDataWebClient[]> webClientBonaboxArray) {

		this.webClientBonabox = webClientBonabox.build(webClient).transform(bulkhead).transform(circuitBreaker).transform(retry)
				.transform(timeLimiter);
		this.webClientBonaboxArray = webClientBonaboxArray.build(webClient).transform(bulkhead).transform(circuitBreaker).transform(retry)
				.transform(timeLimiter);
		
	}

	@Override
	public List<StatusEntregaDataWebClient> consultarStatusEntrega(String codigoEntrega) throws DataProviderException {
		try {

			Mono<StatusEntregaDataWebClient[]> mono = webClientBonaboxArray.get(
					uriBuilder -> uriBuilder.path("/status-entrega/{codigo_entrega}").build(codigoEntrega),
					StatusEntregaDataWebClient[].class);
			StatusEntregaDataWebClient[] array = mono.block();

			return Arrays.asList(array);

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}
	
	@Override
	public List<StatusEntregaDataWebClient> consultarStatusEntregaByCompartimentoLista(
			List<Integer> listaCompartimentos, Integer situacaoId) throws DataProviderException {
		try {

			List<String> lista = listaCompartimentos.stream().map(m -> String.valueOf(m)).collect(Collectors.toList());
			
			Mono<StatusEntregaDataWebClient[]> mono = webClientBonaboxArray.get(uriBuilder -> uriBuilder
					.path("/status-entrega").queryParam("compartimentos", String.join(",", lista))
					.queryParam("situacaoId", situacaoId).build(), StatusEntregaDataWebClient[].class);
			StatusEntregaDataWebClient[] array = mono.block();

			return Arrays.asList(array);

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public StatusEntregaDataWebClient atualizar(StatusEntregaDataWebClient entregaDataRequest)
			throws DataProviderException {

		try {

			StatusEntregaDataWebClient retorno = webClientBonabox
					.post(entregaDataRequest, StatusEntregaDataWebClient.class, "/status-entrega").block();

			return retorno;

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public boolean finalizar(StatusEntregaDataWebClient entrega) throws DataProviderException {

		try {

			webClientBonabox.put(entrega, StatusEntregaDataWebClient.class, "/status-entrega").block();

			return true;

		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}
}
