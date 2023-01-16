package br.com.bonabox.business.api.controller;

import br.com.bonabox.business.domain.EmpresaEntrega;
import br.com.bonabox.business.usecases.GetEmpresaEntregaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 
 * @author Leandro Marques
 *
 */
@RestController
@RequestMapping("/box/api/v1/empresa-entrega")
public class EmpresaEntregaController {

	private final GetEmpresaEntregaUseCase getEmpresaEntregaUseCase;

	public EmpresaEntregaController(GetEmpresaEntregaUseCase getEmpresaEntregaUseCase) {
		this.getEmpresaEntregaUseCase = getEmpresaEntregaUseCase;
	}


	/**
	 * Consultar empresa entregadora
	 * 
	 * @param entregaData
	 * @return
	 */
	@GetMapping(value = "{numero_serial}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> consultar(
			@PathVariable(name = "numero_serial") @NotNull @Size(min = 1, max = 32) final String numeroSerial) {
		try {

			List<EmpresaEntrega> empresaEntregas = getEmpresaEntregaUseCase.execute(numeroSerial);

			return new ResponseEntity<Object>(empresaEntregas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
