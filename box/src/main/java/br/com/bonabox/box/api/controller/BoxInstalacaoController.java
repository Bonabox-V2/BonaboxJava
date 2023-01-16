package br.com.bonabox.box.api.controller;


import br.com.bonabox.box.api.domain.entity.BoxInstalacaoEntity;
import br.com.bonabox.box.api.usecase.GetBoxInstalacaoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Leandro Marques
 *
 */
@RestController
@RequestMapping("/interno-box/api/v1/box-install")
public class BoxInstalacaoController {

	private final GetBoxInstalacaoUseCase getBoxInstalacaoUseCase;

	public BoxInstalacaoController(GetBoxInstalacaoUseCase getBoxInstalacaoUseCase) {
		this.getBoxInstalacaoUseCase = getBoxInstalacaoUseCase;
	}

	@GetMapping(value = "/{box_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarCompartimentos(@PathVariable(name = "box_id") final Integer boxId) {
		try {

			BoxInstalacaoEntity boxInstalacao = getBoxInstalacaoUseCase.execute(boxId);

			return new ResponseEntity<Object>(boxInstalacao, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
