package br.com.bonabox.condominio.api.controller;

import br.com.bonabox.condominio.api.domain.Pessoa;
import br.com.bonabox.condominio.api.usecase.PessoaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/interno-condominio/api/v1/pessoa")
public class PessoaController {

	private final PessoaUseCase pessoaUseCase;

	public PessoaController(PessoaUseCase pessoaUseCase) {
		this.pessoaUseCase = pessoaUseCase;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarPessoa(@RequestParam(name = "condominio_id") final Integer condominioId,
			@RequestParam(name = "ala_id") final Integer alaId, @RequestParam(name = "bloco_id") final Integer blocoId,
			@RequestParam(name = "unidade_id") final Integer unidadeId,
			@RequestParam(name = "codigo_tipo_pessoa") final Integer codigoTipoPessoa) {
		try {

			Pessoa pessoa = pessoaUseCase.obterPessoaPorUnidade(condominioId, alaId, blocoId, unidadeId,
					codigoTipoPessoa);

			return new ResponseEntity<Object>(pessoa, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/morador/principal", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarPessoaMoradorPrincilapPorUnidade(
			@RequestParam(name = "condominio_id") final Integer condominioId,
			@RequestParam(name = "ala_id") final Integer alaId, @RequestParam(name = "bloco_id") final Integer blocoId,
			@RequestParam(name = "unidade_id") final Integer unidadeId) {
		try {

			Pessoa pessoa = pessoaUseCase.obterPessoaMoradorPrincipalPorUnidade(condominioId, alaId, blocoId,
					unidadeId);

			return new ResponseEntity<Object>(pessoa, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/morador/principal-list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarPessoaMoradorPorUnidade(
			@RequestParam(name = "condominio_id") final Integer condominioId,
			@RequestParam(name = "ala_id") final Integer alaId,
			@RequestParam(name = "bloco_id") final Integer blocoId,
			@RequestParam(name = "unidade_id") final Integer unidadeId) {
		try {

			List<Pessoa> pessoa = pessoaUseCase.obterPessoaMoradorPorUnidadeList(condominioId, alaId, blocoId,
					unidadeId);

			return new ResponseEntity<Object>(pessoa, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
