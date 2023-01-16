package br.com.bonabox.business.api.controller;


import br.com.bonabox.business.api.models.CreateMetricaDataRequest;
import br.com.bonabox.business.domain.Metrica;
import br.com.bonabox.business.usecases.CreateMetricaUseCase;
import br.com.bonabox.business.usecases.ex.EntregadorUseCaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/box/api/v1/metrica")
public class MetricaController {

	private final CreateMetricaUseCase createMetricaUseCase;

	public MetricaController(CreateMetricaUseCase createMetricaUseCase) {
		super();
		this.createMetricaUseCase = createMetricaUseCase;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criar(@RequestBody final CreateMetricaDataRequest request) {
		try {

			Metrica entrega = createMetricaUseCase
					.execute(new Metrica(request.getCompartimentoId(), request.getState(), request.getSerialNumber()));

			return new ResponseEntity<Object>(entrega, HttpStatus.CREATED);
		} catch (EntregadorUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
