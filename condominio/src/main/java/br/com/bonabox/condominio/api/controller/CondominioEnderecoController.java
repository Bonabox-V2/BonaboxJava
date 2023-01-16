package br.com.bonabox.condominio.api.controller;

import br.com.bonabox.condominio.api.domain.CondominioEnderecos;
import br.com.bonabox.condominio.api.usecase.CondominioEnderecoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interno-condominio/api/v1/condominio-endereco")
public class CondominioEnderecoController {

	private final CondominioEnderecoUseCase condominioEnderecoUseCase;

	public CondominioEnderecoController(CondominioEnderecoUseCase condominioEnderecoUseCase) {
		super();
		this.condominioEnderecoUseCase = condominioEnderecoUseCase;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultar(@RequestParam(name = "condominio_id") final Integer condominioId) {
		try {

			CondominioEnderecos condominioEndereco = condominioEnderecoUseCase.consultar(condominioId);

			return new ResponseEntity<Object>(condominioEndereco, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
