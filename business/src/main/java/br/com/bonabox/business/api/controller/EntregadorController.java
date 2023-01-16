package br.com.bonabox.business.api.controller;

import br.com.bonabox.business.api.mapper.EntregadorMapper;
import br.com.bonabox.business.api.models.CreateEntregadorDataRequest;
import br.com.bonabox.business.domain.Entregador;
import br.com.bonabox.business.usecases.CreateEntregadorUseCase;
import br.com.bonabox.business.usecases.ex.EntregadorUseCaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Leandro Marques
 *
 */
@RestController
@RequestMapping("/box/api/v1/entregador")
public class EntregadorController {

	private final CreateEntregadorUseCase createEntregadorUseCase;

	public EntregadorController(CreateEntregadorUseCase createEntregadorUseCase) {
		this.createEntregadorUseCase = createEntregadorUseCase;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody final CreateEntregadorDataRequest request) {
		try {

			Entregador entrega = createEntregadorUseCase.execute(EntregadorMapper.toEntregador(request));

			return new ResponseEntity<Object>(EntregadorMapper.toCreateEntregadorDataReponse(entrega), HttpStatus.CREATED);
		} catch (EntregadorUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
