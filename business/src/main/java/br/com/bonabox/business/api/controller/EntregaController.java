package br.com.bonabox.business.api.controller;


import br.com.bonabox.business.api.mapper.EntregaMapper;
import br.com.bonabox.business.api.mapper.StatusEntregaMapper;
import br.com.bonabox.business.api.models.CreateEntregaDataRequest;
import br.com.bonabox.business.api.models.UpdateStatusEntregaDataRequest;
import br.com.bonabox.business.domain.Entrega;
import br.com.bonabox.business.domain.FinalizarEntrega;
import br.com.bonabox.business.domain.StatusEntrega;
import br.com.bonabox.business.usecases.CreateEntregaUseCase;
import br.com.bonabox.business.usecases.GetEntregaUseCase;
import br.com.bonabox.business.usecases.UpdateEntregaUseCase;
import br.com.bonabox.business.usecases.UpdateFinalizarEntregaUseCase;
import br.com.bonabox.business.usecases.ex.EntregaUseCaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * 
 * @author Leandro Marques
 *
 */
@RestController
@RequestMapping("/box/api/v1/entrega")
public class EntregaController {

	private final GetEntregaUseCase getEntregaUseCase;
	private final CreateEntregaUseCase createEntregaUseCase;
	private final UpdateEntregaUseCase updateEntregaUseCase;
	private final UpdateFinalizarEntregaUseCase finalizarEntregaUseCase;

	public EntregaController(CreateEntregaUseCase createEntregaUseCase, UpdateEntregaUseCase updateEntregaUseCase,
			UpdateFinalizarEntregaUseCase finalizarEntregaUseCase, GetEntregaUseCase getEntregaUseCase) {
		this.createEntregaUseCase = createEntregaUseCase;
		this.updateEntregaUseCase = updateEntregaUseCase;
		this.finalizarEntregaUseCase = finalizarEntregaUseCase;
		this.getEntregaUseCase = getEntregaUseCase;
	}

	/**
	 * Iniciar/Criar procedimento de entrega
	 * 
	 * @param entregaData
	 * @return
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> iniciar(@RequestBody final CreateEntregaDataRequest entregaData,
			@RequestHeader Map<String, String> headers) {
		try {

			Entrega entrega = createEntregaUseCase.execute(EntregaMapper.toEntrega(entregaData));

			return new ResponseEntity<Object>(EntregaMapper.toCreateEntregaDataReponse(entrega), HttpStatus.CREATED);
		} catch (EntregaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}
	}

	/**
	 * Consultar entrega
	 * 
	 * @param entregaData
	 * @return
	 */
	@GetMapping(value = "/{codigo_entrega}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultar(
			@PathVariable(name = "codigo_entrega") @NotNull @Size(min = 1, max = 32) final String codigoEntrega) {
		try {

			Entrega entrega = getEntregaUseCase.execute(codigoEntrega);

			return new ResponseEntity<Object>(EntregaMapper.toCreateEntregaDataReponse(entrega), HttpStatus.OK);
		} catch (EntregaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Atualizar procedimento de entrega
	 * 
	 * @param updateEntregaDataRequest
	 * @return
	 */
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> atualizar(
			@RequestBody final UpdateStatusEntregaDataRequest updateEntregaDataRequest) {
		try {

			StatusEntrega retorno = updateEntregaUseCase
					.execute(StatusEntregaMapper.toStatusEntrega(updateEntregaDataRequest));

			return new ResponseEntity<Object>(retorno, HttpStatus.CREATED);
		} catch (EntregaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Finalizar procedimento de entrega
	 * 
	 * @param codigoEntrega
	 * @param serialBox
	 * @return
	 */
	@PutMapping(value = "/{codigo_entrega}/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> finalizar(
			@PathVariable(name = "codigo_entrega") @NotNull @Size(min = 1, max = 32) final String codigoEntrega,
			@PathVariable(name = "codigo") @NotNull @Size(min = 1, max = 32) final String codigo) {
		try {

			FinalizarEntrega finalizar = finalizarEntregaUseCase.execute(codigoEntrega, codigo);
			//#### Temporário
			return new ResponseEntity<Object>(new FinalizarEntregaResponse(finalizar.getCodigoValidacao(), finalizar.getData()), HttpStatus.CREATED);
		} catch (EntregaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

class FinalizarEntregaResponse {

	private final String codigoValidacao;
	private final String data;

	public FinalizarEntregaResponse(String codigoValidacao, String data) {
		super();
		this.codigoValidacao = codigoValidacao;
		this.data = data;
	}

	public String getCodigoValidacao() {
		return codigoValidacao;
	}

	public String getData() {
		return data;
	}

}
