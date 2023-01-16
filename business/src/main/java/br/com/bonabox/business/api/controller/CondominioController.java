package br.com.bonabox.business.api.controller;

import br.com.bonabox.business.domain.Ala;
import br.com.bonabox.business.domain.Bloco;
import br.com.bonabox.business.domain.Unidade;
import br.com.bonabox.business.domain.webclient.AlaDataWebClientResponse;
import br.com.bonabox.business.domain.webclient.BlocoDataWebClientResponse;
import br.com.bonabox.business.domain.webclient.UnidadeDataWebClientResponse;
import br.com.bonabox.business.usecases.GetCondominioAlaUseCase;
import br.com.bonabox.business.usecases.GetCondominioBlocoUseCase;
import br.com.bonabox.business.usecases.GetCondominioUnidadeUseCase;
import br.com.bonabox.business.usecases.ex.AlaUseCaseException;
import br.com.bonabox.business.usecases.ex.BlocoUseCaseException;
import br.com.bonabox.business.usecases.ex.UnidadeUseCaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author Leandro Marques
 *
 */
@RestController
@RequestMapping("/box/api/v1/condominio")
public class CondominioController {

	private final GetCondominioBlocoUseCase condominioUseCase;
	private final GetCondominioAlaUseCase alaUseCase;
	private final GetCondominioUnidadeUseCase unidadeUseCase;

	public CondominioController(GetCondominioBlocoUseCase condominioUseCase, GetCondominioAlaUseCase alaUseCase,
			GetCondominioUnidadeUseCase unidadeUseCase) {
		this.condominioUseCase = condominioUseCase;
		this.alaUseCase = alaUseCase;
		this.unidadeUseCase = unidadeUseCase;
	}

	@GetMapping(value = "/unidade", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterUnidadeByCondominioIdAlaIdBlocoId(
			@RequestParam(name = "numero_serial") final String condominioId,
			@RequestParam(name = "ala_id") final Integer alaId,
			@RequestParam(name = "bloco_id") final Integer blocoId) {
		try {

			List<Unidade> unidadeLista = unidadeUseCase.execute(condominioId, alaId, blocoId);

			List<UnidadeDataWebClientResponse> retorno = unidadeLista.stream().map(m -> {
				return new UnidadeDataWebClientResponse(m.getUnidadeId(), m.getPiso(), m.getNumeroUnidade(),
						m.getLabelUnidade());
			}).collect(Collectors.toList());

			return new ResponseEntity<Object>(retorno, HttpStatus.OK);
		} catch (UnidadeUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/unidade/get-unidade", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterUnidadeByCondominioIdAlaIdBlocoId(
			@RequestParam(name = "unidadeId") final Integer unidadeId) {
		try {

			Unidade unidade = unidadeUseCase.execute(unidadeId);

			return new ResponseEntity<Object>(unidade, HttpStatus.OK);
		} catch (UnidadeUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/ala", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterAlaByCoondominioId(
			@RequestParam(name = "numero_serial") final String condominioId) {
		try {

			List<Ala> alaLista = alaUseCase.execute(condominioId);

			List<AlaDataWebClientResponse> retorno = alaLista.stream().map(m -> {
				return new AlaDataWebClientResponse(m.getAlaId(), m.getLabel(), m.getNome(), m.getDescricao());
			}).collect(Collectors.toList());

			return new ResponseEntity<Object>(retorno, HttpStatus.OK);
		} catch (AlaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/bloco", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterBlocoByCoondominioIdAlaId(
			@RequestParam(name = "numero_serial") final String condominioId,
			@RequestParam(name = "ala_id") final Integer alaId) {
		try {

			List<Bloco> blocoLista = condominioUseCase.execute(condominioId, alaId);

			List<BlocoDataWebClientResponse> retorno = blocoLista.stream().map(m -> {
				return new BlocoDataWebClientResponse(m.getBlocoId(), m.getLabel(), m.getNome(), m.getDescricao());
			}).collect(Collectors.toList());

			return new ResponseEntity<Object>(retorno, HttpStatus.OK);
		} catch (BlocoUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*@GetMapping(value = "/bloco/get-bloco", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterBlocoByBlocoId(
			@RequestParam(name = "bloco-id") final Integer blocoId) {
		try {

			Bloco bloco = condominioUseCase.execute(blocoId);

			return new ResponseEntity<Object>(bloco, HttpStatus.OK);
		} catch (BlocoUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/

}
