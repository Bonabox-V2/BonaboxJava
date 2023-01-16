package br.com.bonabox.box.api.controller;

import br.com.bonabox.box.api.controller.models.BoxCompartimentosResponse;
import br.com.bonabox.box.api.controller.models.BoxResponse;
import br.com.bonabox.box.api.controller.models.CompartimentoResponse;
import br.com.bonabox.box.api.domain.entity.BoxCompartimentosEntity;
import br.com.bonabox.box.api.domain.entity.BoxEntity;
import br.com.bonabox.box.api.usecase.GetBoxUseCase;
import br.com.bonabox.box.api.usecase.ex.UseCaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author Leandro Marques
 *
 */
@RestController
@RequestMapping("/interno-box/api/v1/box")
public class BoxController {

	private final GetBoxUseCase getBoxUseCase;

	public BoxController(GetBoxUseCase getBoxUseCase) {
		this.getBoxUseCase = getBoxUseCase;
	}

	/**
	 * Consultar entrega
	 * 
	 * @param
	 * @return
	 */
	@GetMapping(value = "/{numero_serial}/{tipo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarByNumeroSerial(
			@PathVariable(name = "numero_serial") final String numeroSerial,
			@PathVariable(name = "tipo") final String tipo) {
		try {
			
			Object box = getBoxUseCase.consultarByNumeroSerialAndTipo(numeroSerial, tipo);

			return new ResponseEntity<Object>(box, HttpStatus.OK);

		} catch (UseCaseException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{box_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarCompartimentos(@PathVariable(name = "box_id") final Integer boxId) {
		try {

			Collection<BoxCompartimentosEntity> box = getBoxUseCase.consultarCompartimentos(boxId);

			List<BoxCompartimentosResponse> lista = box.stream().map(m -> {
				return new BoxCompartimentosResponse(m.getCodigo(), m.getCodigoCompartimento(),
						new CompartimentoResponse(m.getCompartimento().getCodigo(), m.getCompartimento().getNome(),
								m.getCompartimento().getDescricao(), m.getCompartimento().getTamanho()),
						m.getCodigoBox(), m.getLabel());
			}).collect(Collectors.toList());

			return new ResponseEntity<Object>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/owner/{owner}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultarBoxAdicionalByOwner(@PathVariable(name = "owner") final Integer owner) {
		try {

			Collection<BoxEntity> box = getBoxUseCase.consultarBoxAdicionalByOwner(owner);

			List<BoxResponse> lista = box.stream().map(m -> {
				return new BoxResponse(m.getBoxId(), m.getNumeroSerial(), m.getDataCadastro(),
						m.getCodigoUserCadastro(), m.getDataAtualizacao(), m.getCodigoUserAtualizacao(),
						m.getDataFabricacao(), m.getTipo(), m.getOwner(), m.getNome());
			}).collect(Collectors.toList());

			return new ResponseEntity<Object>(lista, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
