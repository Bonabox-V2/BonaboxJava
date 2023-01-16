package br.com.bonabox.business.api.controller;

import br.com.bonabox.business.api.models.GetCompartimentoLabelResponse;
import br.com.bonabox.business.api.models.GetCompartimentosResponse;
import br.com.bonabox.business.api.models.GetEnderecoBoxResponse;
import br.com.bonabox.business.api.models.GetTipoCompartimentoDisponivelResponse;
import br.com.bonabox.business.usecases.GetBoxUseCase;
import br.com.bonabox.business.usecases.GetCompartimentosUseCase;
import br.com.bonabox.business.usecases.ex.CompartimentoUseCaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/box/api/v1/locker")
@Validated
public class BoxController {

	private final GetCompartimentosUseCase compartimentosUseCase;
	private final GetBoxUseCase boxUseCase;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public BoxController(GetCompartimentosUseCase compartimentosUseCase,
			GetBoxUseCase boxUseCase) {
		this.compartimentosUseCase = compartimentosUseCase;
		this.boxUseCase = boxUseCase;
	}

	/**
	 * Consultar compartimentos disponíveis do box
	 * 
	 * @param numeroSerial
	 * @param entregaId
	 * @return
	 */
	@GetMapping(value = "/obter-tipo-compartimentos-disponiveis", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterTipoCompartimentosDisponiveis(
			@RequestParam(name = "numero_serial", required = true) @NotNull @Size(min = 1, max = 32) final String numeroSerial,
			@RequestParam(name = "entrega_id", required = true) @NotNull @Size(min = 1, max = 32) final String entregaId) {

		// Devolve uma lista de objetos do tipo GetTipoCompartimentoDisponivelResponse
		// informando os compartimentos existentes disponíveis e seus respectivos tipos (P, M e G)
		try {

			logger.info("Iniciado obterTipoCompartimentosDisponiveis {} {}", numeroSerial, entregaId);
			
			List<GetTipoCompartimentoDisponivelResponse> disponiveisResponse = compartimentosUseCase
					.execute(numeroSerial, entregaId);

			logger.info("Finalizado obterTipoCompartimentosDisponiveis: Tamanho lista {}", disponiveisResponse.size());
			
			return new ResponseEntity<Object>(disponiveisResponse, HttpStatus.OK);
		} catch (CompartimentoUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}
	}
	
	@GetMapping(value = "/tem-compartimentos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> temCompartimentosLivres (
			@RequestParam(name = "numero_serial", required = true) @NotNull @Size(min = 1, max = 32) final String numeroSerial) {

		try {

			logger.info("Iniciado temCompartimentosLivres {}", numeroSerial);
			
			if(!compartimentosUseCase.temCompartimentosLivres(numeroSerial)) {
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
			}

			logger.info("Finalizado temCompartimentosLivres");
			
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (CompartimentoUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}
	}
	
	@GetMapping(value = "/obter-compartimentos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterTipoCompartimentos (
			@RequestParam(name = "numero_serial", required = true) @NotNull @Size(min = 1, max = 32) final String numeroSerial) {

		try {

			logger.info("Iniciando obterTipoCompartimentos com numeroSeral {}", numeroSerial);
			
			List<GetCompartimentosResponse> disponiveisResponse = compartimentosUseCase.execute(numeroSerial);

			logger.info("Finalizado obterTipoCompartimentos: Tamanho lista {}", disponiveisResponse.size());
			
			return new ResponseEntity<Object>(disponiveisResponse, HttpStatus.OK);
		} catch (CompartimentoUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}
	}
	
	@GetMapping(value = "/obter-estado-todos-compartimentos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterAllEstadoBoxByOwner(
			@RequestParam(name = "numero_serial", required = true) @NotNull @Size(min = 1, max = 32) final String numeroSerial) {

		try {

			logger.info("Iniciado obter-estado-todos-compartimentos {}", numeroSerial);

			List<GetCompartimentosResponse> disponiveisResponse = compartimentosUseCase
					.obterAllEstadoBoxByOwner(numeroSerial);

			logger.info("Finalizado obter-estado-todos-compartimentos: Tamanho lista {}", disponiveisResponse.size());

			return new ResponseEntity<Object>(disponiveisResponse, HttpStatus.OK);
		} catch (CompartimentoUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}
	}
	

	@GetMapping(value = "/obter-compartimento", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterCompartimento(
			@RequestParam(name = "numero_serial", required = true) @NotNull @Size(min = 1, max = 50) final String numeroSerial,
			@RequestParam(name = "entrega_id", required = true) @NotNull @Size(min = 1, max = 50) final String entregaId,
			@RequestParam(name = "codigo", required = true) @NotNull @Size(min = 1, max = 100) final String codigo) {

		try {

			logger.info("Iniciado obterCompartimento {} {} {}", numeroSerial, entregaId, codigo);
			
			// Devolve o número da porta que deve ser aberta
			GetCompartimentoLabelResponse compartimentoLabelResponse = compartimentosUseCase.execute(numeroSerial,
					entregaId, codigo);
			
			logger.info("Finalizado obterCompartimento codigo: {} labelPorta: {}", compartimentoLabelResponse.getCodigo(), compartimentoLabelResponse.getLabelPorta());

			return new ResponseEntity<Object>(compartimentoLabelResponse, HttpStatus.OK);
		} catch (CompartimentoUseCaseException e) {
			return new ResponseEntity<Object>("Dados incorretos", e.getHttpStatus());
		}
	}
	
	@GetMapping(value = "/endereco-instalacao", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterEnderecoInstalacao (
			@RequestParam(name = "numero_serial", required = true) @NotNull @Size(min = 1, max = 50) final String numeroSerial) {
		try {

			Set<GetEnderecoBoxResponse> response = boxUseCase.execute(numeroSerial);
			
			//throw new CompartimentoUseCaseException("Não implementado!", HttpStatus.NOT_IMPLEMENTED);
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		} catch (CompartimentoUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}
	}

}
