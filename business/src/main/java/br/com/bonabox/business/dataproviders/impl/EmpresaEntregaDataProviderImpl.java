package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.EmpresaEntregaDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.EmpresaEntregaDataWebClient;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class EmpresaEntregaDataProviderImpl implements EmpresaEntregaDataProvider {

	
	private final WebClientBonabox<EmpresaEntregaDataWebClient[]> webClientInternoBonaboxList;
	
	@Value("${interno.bonabox.get.resource}")
	private String empresaEntregaResource;

	private DataMDC dataMDC;
	
	public EmpresaEntregaDataProviderImpl(@Qualifier("interno-bonabox") WebClient webClient,
			WebClientBonabox<EmpresaEntregaDataWebClient[]> webClientInternoBonaboxList,
			@Qualifier("retry.bonabox") Retry retry,
			@Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead) {

		this.webClientInternoBonaboxList = webClientInternoBonaboxList.build(webClient);
	}

	@Override
	public List<EmpresaEntregaDataWebClient> consultarTodos() throws DataProviderException {

		try {
			
			Mono<EmpresaEntregaDataWebClient[]> mono = webClientInternoBonaboxList.build(dataMDC).get(empresaEntregaResource, EmpresaEntregaDataWebClient[].class);

			/*Mono<EmpresaEntregaDataWebClient[]> mono = webClientInternoBonabox.get().uri(empresaEntregaResource)
					.retrieve().bodyToMono(EmpresaEntregaDataWebClient[].class);*/

			EmpresaEntregaDataWebClient[] array = mono.block();

			return Arrays.asList(array);
		} catch (Exception e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public EmpresaEntregaDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}

}
