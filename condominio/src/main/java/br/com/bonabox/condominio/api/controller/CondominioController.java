package br.com.bonabox.condominio.api.controller;


import br.com.bonabox.condominio.api.domain.Condominio;
import br.com.bonabox.condominio.api.usecase.CondominioUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interno-condominio/api/v1/condominio")
public class CondominioController {

	private final CondominioUseCase condominioUseCase;

	public CondominioController(CondominioUseCase condominioUseCase) {
		this.condominioUseCase = condominioUseCase;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarCondominio(
			@RequestParam(name = "condominio_id") final Integer condominioId) {
		try {

			Condominio condominioLista = condominioUseCase.obterCondominio(condominioId);

			return new ResponseEntity<Object>(condominioLista, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
