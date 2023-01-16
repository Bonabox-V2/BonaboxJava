package br.com.bonabox.condominio.api.controller;

import br.com.bonabox.condominio.api.domain.CondominioComposicao;
import br.com.bonabox.condominio.api.usecase.CondominioComposicaoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/interno-condominio/api/v1/composicao")
public class CondominioComposicaoController {

	private final CondominioComposicaoUseCase condominioComposicaoUseCase;

	public CondominioComposicaoController(CondominioComposicaoUseCase condominioComposicaoUseCase) {
		this.condominioComposicaoUseCase = condominioComposicaoUseCase;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarComposicao(
			@RequestParam(name = "condominio_id") final Integer condominioId,
			@RequestParam(name = "ala_id") final Integer alaId,
			@RequestParam(name = "bloco_id") final Integer blocoId,
			@RequestParam(name = "unidade_id") final Integer unidadeId) {
		try {

			List<CondominioComposicao> cComposicaoLista = condominioComposicaoUseCase.obterComposicao(condominioId, alaId, blocoId, unidadeId);

			return new ResponseEntity<Object>(cComposicaoLista, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
