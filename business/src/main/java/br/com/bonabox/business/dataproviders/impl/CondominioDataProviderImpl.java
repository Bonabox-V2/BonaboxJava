package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.CondominioDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.CondominioDataWebClientRequest;
import br.com.bonabox.business.domain.webclient.CondominioEnderecoDataWebClientResponse;
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
public class CondominioDataProviderImpl implements CondominioDataProvider {

	private final WebClientBonabox<CondominioDataWebClientRequest> webClientBonabox;
	private final WebClientBonabox<CondominioEnderecoDataWebClientResponse> webClientCondominio;

	@Value("${interno.condominio.resource.condiminio}")
	private String condominioResource;
	
	private DataMDC dataMDC;

	public CondominioDataProviderImpl(@Qualifier("interno-condominio") WebClient webClient,
			WebClientBonabox<CondominioDataWebClientRequest> webClientBonabox,
			WebClientBonabox<CondominioEnderecoDataWebClientResponse> webClientCondominio,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead, @Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter,
			@Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker) {
		this.webClientBonabox = webClientBonabox.build(webClient).transform(bulkhead).transform(timeLimiter).transform(circuitBreaker);
        this.webClientCondominio = webClientCondominio.build(webClient).transform(bulkhead).transform(timeLimiter).transform(circuitBreaker);
    }

	@Override
	public CondominioDataWebClientRequest consultarCondominio(Integer condominioId) throws DataProviderException {

		try {

			Mono<CondominioDataWebClientRequest> mono = webClientBonabox.build(dataMDC).get(uriBuilder -> uriBuilder
					.path(condominioResource).queryParam("condominio_id", condominioId).build(condominioId),
					CondominioDataWebClientRequest.class);

			return mono.block();
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public CondominioEnderecoDataWebClientResponse consultarCondominioEndereco(Integer condominioId)
			throws DataProviderException {

		try {

			Mono<CondominioEnderecoDataWebClientResponse> mono = webClientCondominio.build(dataMDC).get(uriBuilder -> uriBuilder
					.path("/condominio-endereco").queryParam("condominio_id", condominioId).build(condominioId),
					CondominioEnderecoDataWebClientResponse.class);

			return mono.block();
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public CondominioDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}
}
