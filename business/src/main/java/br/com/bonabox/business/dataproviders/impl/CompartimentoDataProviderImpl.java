package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.CompartimentoDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import br.com.bonabox.business.domain.*;
import br.com.bonabox.business.usecases.ex.BaseException;
import br.com.bonabox.business.util.WebClientBonabox;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class CompartimentoDataProviderImpl implements CompartimentoDataProvider {

	private final WebClientBonabox<TipoCompartimentoDisponivel[]> webClientBonaboxTipoCompartimento;
	private final WebClientBonabox<EstadoBox[]> webClientBonaboxEstadoList;
	private final WebClientBonabox<EstadoBox> webClientBonaboxEstado;
	private final WebClientBonabox<Compartimento[]> webClientBonaboxCompartimento;
	private final WebClientBonabox<CompartimentoStatusTempResponse> webClientBonaboxCompartimentoStatusTemp;
	private final WebClientBonabox<CompartimentoStatusTempResponse[]> webClientBonaboxCompartimentoStatusTempList;

	private DataMDC dataMDC;
	
	public CompartimentoDataProviderImpl(@Qualifier("interno-box") WebClient webClient,
			@Qualifier("interno-bonabox") WebClient webClientInternoBonabox,
			WebClientBonabox<TipoCompartimentoDisponivel[]> webClientBonaboxTipoCompartimento,
			WebClientBonabox<EstadoBox[]> webClientBonaboxEstadoList,
			WebClientBonabox<Compartimento[]> webClientBonaboxCompartimento,
			WebClientBonabox<CompartimentoStatusTempResponse> webClientBonaboxCompartimentoStatusTemp,
			WebClientBonabox<CompartimentoStatusTempResponse[]> webClientBonaboxCompartimentoStatusTempList,
			WebClientBonabox<EstadoBox> webClientBonaboxEstado, @Qualifier("bulkhead.bonabox") Bulkhead bulkhead,
			@Qualifier("timeLimiter.bonabox") TimeLimiter timeLimiter) {

		this.webClientBonaboxTipoCompartimento = webClientBonaboxTipoCompartimento;
		this.webClientBonaboxTipoCompartimento.build(webClient).transform(bulkhead).transform(timeLimiter);

		this.webClientBonaboxEstadoList = webClientBonaboxEstadoList;
		this.webClientBonaboxEstadoList.build(webClientInternoBonabox).transform(bulkhead).transform(timeLimiter);

		this.webClientBonaboxCompartimento = webClientBonaboxCompartimento;
		this.webClientBonaboxCompartimento.build(webClient).transform(bulkhead).transform(timeLimiter);

		this.webClientBonaboxCompartimentoStatusTemp = webClientBonaboxCompartimentoStatusTemp;
		this.webClientBonaboxCompartimentoStatusTemp.build(webClientInternoBonabox).transform(bulkhead)
				.transform(timeLimiter);

		this.webClientBonaboxCompartimentoStatusTempList = webClientBonaboxCompartimentoStatusTempList;
		this.webClientBonaboxCompartimentoStatusTempList.build(webClientInternoBonabox).transform(bulkhead)
				.transform(timeLimiter);

		this.webClientBonaboxEstado = webClientBonaboxEstado;
		this.webClientBonaboxEstado.build(webClientInternoBonabox).transform(bulkhead).transform(timeLimiter);
	}

	@Override
	public CompartimentoStatusTempResponse consultarCompatimentoByIdAleatorio(String compartimentoIdAleatorio)
			throws DataProviderException {
		try {
			Mono<CompartimentoStatusTempResponse> mono = webClientBonaboxCompartimentoStatusTemp.build(dataMDC)
					.get(uriBuilder -> uriBuilder.path("/compartimento/support/{compartimento-id-aleatorio}")
							.build(compartimentoIdAleatorio), CompartimentoStatusTempResponse.class);
			return mono.block();
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public CompartimentoStatusTempResponse criarComIdAleatorio(
			CompartimentoStatusTempRequest compartimentoStatusTempRequest) throws DataProviderException {
		try {
			Mono<CompartimentoStatusTempResponse> mono = webClientBonaboxCompartimentoStatusTemp.build(dataMDC).post(
					compartimentoStatusTempRequest, CompartimentoStatusTempResponse.class, "/compartimento/support");
			return mono.block();
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public List<CompartimentoStatusTempResponse> criarComIdAleatorioLista(
			List<CompartimentoStatusTempRequest> compartimentoStatusTempRequest) throws DataProviderException {
		try {
			Mono<CompartimentoStatusTempResponse[]> mono = webClientBonaboxCompartimentoStatusTempList.build(dataMDC).post(
					compartimentoStatusTempRequest, CompartimentoStatusTempResponse[].class,
					"/compartimento/support/lista");
			return Arrays.asList(mono.block());
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public CompartimentoStatusTempResponse atualizarComIdAleatorio(
			CompartimentoStatusTempRequest compartimentoStatusTempRequest) throws DataProviderException {
		try {
			Mono<CompartimentoStatusTempResponse> mono = webClientBonaboxCompartimentoStatusTemp.build(dataMDC).put(
					compartimentoStatusTempRequest, CompartimentoStatusTempResponse.class, "/compartimento/support");

			return mono.block();
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public List<TipoCompartimentoDisponivel> getByOwner(Integer owner) throws DataProviderException {
		try {
			Mono<TipoCompartimentoDisponivel[]> mono = webClientBonaboxTipoCompartimento.build(dataMDC).get(
					uriBuilder -> uriBuilder.path("/box/owner/{owner}").build(owner),
					TipoCompartimentoDisponivel[].class);

			TipoCompartimentoDisponivel[] array = mono.block();

			return Arrays.asList(array);
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public List<EstadoBox> getEstadoBoxByOwner(Integer boxId) throws DataProviderException {
		try {
			Mono<EstadoBox[]> mono = webClientBonaboxEstadoList.build(dataMDC)
					.get(uriBuilder -> uriBuilder.path("/estado-box/{box_id}").build(boxId), EstadoBox[].class);

			EstadoBox[] array = mono.block();

			return Arrays.asList(array);
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public List<EstadoBox> getAllEstadoBoxByOwner(Integer boxId) throws DataProviderException {
		try {
			Mono<EstadoBox[]> mono = webClientBonaboxEstadoList.build(dataMDC)
					.get(uriBuilder -> uriBuilder.path("/estado-box/{box_id}/all").build(boxId), EstadoBox[].class);

			EstadoBox[] array = mono.block();

			return Arrays.asList(array);
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	public List<Compartimento> consultarCompartimentos(int boxId) throws DataProviderException {
		try {
			Mono<Compartimento[]> mono = webClientBonaboxCompartimento.build(dataMDC)
					.get(uriBuilder -> uriBuilder.path("/box/{box_id}").build(boxId), Compartimento[].class);

			Compartimento[] array = mono.block();

			return Arrays.asList(array);
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	//Temporário
	@Override
	public EstadoBox update(Integer compartimentoId, Integer newEstadoAtividade) throws DataProviderException {
		try {

			EstadoBox estado = new EstadoBox();
			estado.setCompartimentoId(compartimentoId);
			EstadoBoxAtividade atividade = new EstadoBoxAtividade();
			atividade.setEstadoBoxId(newEstadoAtividade);
			estado.setEstadoAtividade(atividade);

			Mono<EstadoBox> mono = webClientBonaboxEstado.build(dataMDC).put(estado, EstadoBox.class, "/estado-box");

			return mono.block();
		} catch (BaseException e) {
			throw new DataProviderException(e);
		}
	}

	@Override
	public CompartimentoDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}
}

class BoxUpdateEstadoAtividade {
	private Integer boxId;
	private Integer newEstadoAtividade;

	public BoxUpdateEstadoAtividade() {
	}

	public BoxUpdateEstadoAtividade(Integer boxId, Integer newEstadoAtividade) {
		super();
		this.boxId = boxId;
		this.newEstadoAtividade = newEstadoAtividade;
	}

	public Integer getBoxId() {
		return boxId;
	}

	public void setBoxId(Integer boxId) {
		this.boxId = boxId;
	}

	public Integer getNewEstadoAtividade() {
		return newEstadoAtividade;
	}

	public void setNewEstadoAtividade(Integer newEstadoAtividade) {
		this.newEstadoAtividade = newEstadoAtividade;
	}

}
