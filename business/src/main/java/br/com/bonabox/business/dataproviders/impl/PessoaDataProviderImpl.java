package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.PessoaDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.webclient.PessoaDataWebClient;
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
public class PessoaDataProviderImpl implements PessoaDataProvider {

	private final WebClientBonabox<PessoaDataWebClient> webClientBonaboxInternoCondominio;
	private final WebClientBonabox<PessoaDataWebClient[]> webClientBonaboxInternoCondominioPessoa;

	@Value("${interno.condominio.resource.pessoa}")
	private String pessoaResource;
	
	private DataMDC dataMDC;

	public PessoaDataProviderImpl(@Qualifier("interno-condominio") WebClient webClient,
			WebClientBonabox<PessoaDataWebClient> webClientBonaboxInternoCondominio,
			WebClientBonabox<PessoaDataWebClient[]> webClientBonaboxInternoCondominioPessoa,
			@Qualifier("retry.bonabox") Retry retry, @Qualifier("circuitBreaker.bonabox") CircuitBreaker circuitBreaker,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter,
			@Qualifier("bulkhead.bonabox") Bulkhead bulkhead) {

		this.webClientBonaboxInternoCondominio = webClientBonaboxInternoCondominio.build(webClient).transform(bulkhead)
				.transform(circuitBreaker).transform(retry).transform(timeLimiter);

		this.webClientBonaboxInternoCondominioPessoa = webClientBonaboxInternoCondominioPessoa.build(webClient)
				.transform(bulkhead).transform(circuitBreaker).transform(retry).transform(timeLimiter);
	}

	@Override
	public PessoaDataWebClient consultarPessoa(Integer condominioId, Integer alaId, Integer blocoId, Integer unidadeId,
			Integer codigoTipoPessoa) throws DataProviderException {

		try {

			Mono<PessoaDataWebClient> mono = webClientBonaboxInternoCondominio.build(dataMDC).get(uriBuilder -> uriBuilder
					.path(pessoaResource).queryParam("condominio_id", condominioId).queryParam("ala_id", alaId)
					.queryParam("bloco_id", blocoId).queryParam("unidade_id", unidadeId)
					.queryParam("codigo_tipo_pessoa", codigoTipoPessoa).build(condominioId), PessoaDataWebClient.class);

			return mono.block();
		} catch (Exception e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public PessoaDataWebClient consultarPessoaMoradorPrincipalPorUnidade(Integer condominioId, Integer alaId,
			Integer blocoId, Integer unidadeId) throws DataProviderException {
		try {

			Mono<PessoaDataWebClient> mono = webClientBonaboxInternoCondominio.build(dataMDC).get(
					uriBuilder -> uriBuilder.path("/pessoa/morador/principal").queryParam("condominio_id", condominioId)
							.queryParam("ala_id", alaId).queryParam("bloco_id", blocoId)
							.queryParam("unidade_id", unidadeId).queryParam("codigo_tipo_pessoa").build(condominioId),
					PessoaDataWebClient.class);

			return mono.block();
		} catch (Exception e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public List<PessoaDataWebClient> consultarPessoaMoradorPrincipalPorUnidadeList(Integer condominioId, Integer alaId,
			Integer blocoId, Integer unidadeId) throws DataProviderException {
		try {

			Mono<PessoaDataWebClient[]> mono = webClientBonaboxInternoCondominioPessoa.build(dataMDC).get(uriBuilder -> uriBuilder
					.path("/pessoa/morador/principal-list").queryParam("condominio_id", condominioId)
					.queryParam("ala_id", alaId).queryParam("bloco_id", blocoId).queryParam("unidade_id", unidadeId)
					.queryParam("codigo_tipo_pessoa").build(condominioId), PessoaDataWebClient[].class);

			return Arrays.asList(mono.block());
		} catch (Exception e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public PessoaDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}

}
