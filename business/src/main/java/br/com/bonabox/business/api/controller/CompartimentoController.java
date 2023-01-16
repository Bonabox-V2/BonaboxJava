package br.com.bonabox.business.api.controller;


import br.com.bonabox.business.dataproviders.CompartimentoDataProvider;
import br.com.bonabox.business.domain.CompartimentoStatusTempResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/box/api/v1/compartimento/support")
public class CompartimentoController {

	private final CompartimentoDataProvider compartimentoDataProvider;

	public CompartimentoController(CompartimentoDataProvider compartimentoDataProvider) {
		this.compartimentoDataProvider = compartimentoDataProvider;
	}

	@GetMapping(value = "/{compartimento-id-aleatorio}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultar(
			@PathVariable(name = "compartimento-id-aleatorio") String compartimentoIdAleatorio) {
		try {

			CompartimentoStatusTempResponse output = compartimentoDataProvider
					.consultarCompatimentoByIdAleatorio(compartimentoIdAleatorio);

			/*CompartimentoStatusTempResponse response = new CompartimentoStatusTempResponse(
					output.getCompartimentoIdAleatorio(), output.getCompartimentoId(), output.getBoxId(),
					output.getCodigoEstadoBoxAtividade(), output.getEntregaId(), output.getCompartimentoCom1Id(),
					output.getLabelPorta());*/
			
			return new ResponseEntity<Object>(output, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
