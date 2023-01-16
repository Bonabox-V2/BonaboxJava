package br.com.bonabox.business.api.controller;


import br.com.bonabox.business.domain.Retirada;
import br.com.bonabox.business.usecases.CreateRetiradaUseCase;
import br.com.bonabox.business.usecases.ex.RetiradaUseCaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @author Leandro Marques
 *
 */
@RestController
@RequestMapping("/box/api/v1/retirada")
public class RetiradaController {
	
	private final CreateRetiradaUseCase createRetiradaUseCase;
	
	public RetiradaController(CreateRetiradaUseCase createRetiradaUseCase) {
		this.createRetiradaUseCase = createRetiradaUseCase;
	}

	@GetMapping(value = "/porta/{numero-serial}/{codigo-de-acesso}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> retirarByCodigoDeAcesso(@PathVariable(name = "numero-serial") final String numeroSerial,
			@PathVariable(name = "codigo-de-acesso") final String codigoDeAcesso) {
		try {

			Retirada retirada = createRetiradaUseCase.execute(numeroSerial, codigoDeAcesso);

			return new ResponseEntity<Object>(retirada, HttpStatus.OK);
		} catch (RetiradaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/porta/token", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> obterCodigoRetiradaByToken(@RequestParam(name = "token") final String codigoRetirada) {
		try {

			String jsonRetirada = createRetiradaUseCase.execute(codigoRetirada);

			return new ResponseEntity<Object>(jsonRetirada, HttpStatus.OK);
		} catch (RetiradaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/liberar-porta/{numero-serial}/{origem}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> liberarPorta (@RequestBody final RetiradaToken token,
			@PathVariable(name = "numero-serial") final String numeroSerial, @PathVariable(name = "origem") final String origem) {
		try {

			boolean retirada = createRetiradaUseCase.liberarPorta(numeroSerial, token.getToken(), 0, "", origem);

			if(retirada)
			return new ResponseEntity<Object>(HttpStatus.CREATED);
			throw new RetiradaUseCaseException("Não foi possível fazer atualização!");
			
		} catch (RetiradaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/processar-abertura-compartimento/{numero-serial}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> processarAberturaDeCompartimento (@RequestBody final ProcessarRetirada processarRetirada,
			@PathVariable(name = "numero-serial") final String numeroSerial) {
		try {

			boolean processadoOk = createRetiradaUseCase.processarAberturaDePorta(numeroSerial,
					processarRetirada.getToken(), processarRetirada.getType(), processarRetirada.getOrigem());

			if (processadoOk) {
				return new ResponseEntity<Object>(HttpStatus.CREATED);
			} else {
				throw new RetiradaUseCaseException(String.format(
						"Não foi possível processar a bertura de compartimento com a origem %s e tipo %s!",
						processarRetirada.getOrigem(), processarRetirada.getType()));
			}
			
		} catch (RetiradaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/porta-fechada", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> portaFechada(@RequestBody final RetiradaToken token,
			@PathVariable(name = "numero-serial") final String numeroSerial) {
		try {

			String retirada = createRetiradaUseCase.executeByToken(numeroSerial, token.getToken());

			return new ResponseEntity<Object>(retirada, HttpStatus.OK);
		} catch (RetiradaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/token/{numero-serial}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> byToken(@RequestBody final RetiradaToken token,
			@PathVariable(name = "numero-serial") final String numeroSerial) {
		try {

			String retirada = createRetiradaUseCase.executeByToken(numeroSerial, token.getToken());

			return new ResponseEntity<Object>(retirada, HttpStatus.OK);
		} catch (RetiradaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/token-pin/{numero-serial}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> byTokenPin(@RequestBody final RetiradaTokenPin tokenPin,
			@PathVariable(name = "numero-serial") final String numeroSerial) {
		try {

			String retirada = createRetiradaUseCase.executeByToken(numeroSerial, tokenPin.getTokenPin());

			return new ResponseEntity<Object>(retirada, HttpStatus.OK);
		} catch (RetiradaUseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), e.getHttpStatus());
		}catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

class RetiradaToken {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

class ProcessarRetirada {
	// codigoCompartimentoAleatorio
	private String token;
	private String type;
	private String origem;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

}

class RetiradaTokenPin {
	private String tokenPin;

	public String getTokenPin() {
		return tokenPin;
	}

	public void setTokenPin(String tokenPin) {
		this.tokenPin = tokenPin;
	}

}