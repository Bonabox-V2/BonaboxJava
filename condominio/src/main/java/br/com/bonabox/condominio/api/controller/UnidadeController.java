package br.com.bonabox.condominio.api.controller;

import br.com.bonabox.condominio.api.domain.Unidade;
import br.com.bonabox.condominio.api.usecase.UnidadeUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/interno-condominio/api/v1/unidade")
public class UnidadeController {

	private final UnidadeUseCase unidadeUseCase;

	public UnidadeController(UnidadeUseCase unidadeUseCase) {
		this.unidadeUseCase = unidadeUseCase;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarUnidadeByCondominioId(
			@RequestParam(name = "condominio_id") final Integer condominioId,
			@RequestParam(name = "ala_id") final Integer alaId,
			@RequestParam(name = "bloco_id") final Integer blocoId) {
		try {

			List<Unidade> unidadeLista = unidadeUseCase.obterUnidadePorCondominio(condominioId, alaId, blocoId);

			return new ResponseEntity<Object>(unidadeLista, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Método temporário
	@GetMapping(value = "/get-unidade",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarUnidade(
			@RequestParam(name = "unidade_id") final Integer unidadeId) {
		try {

			Unidade unidade = unidadeUseCase.obterUnidadePorCodigo(unidadeId);

			return new ResponseEntity<Object>(unidade, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 

}
