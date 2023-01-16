package br.com.bonabox.condominio.api.controller;

import br.com.bonabox.condominio.api.domain.Ala;
import br.com.bonabox.condominio.api.usecase.AlaUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/interno-condominio/api/v1/ala")
public class AlaController {

    private final AlaUseCase alaUseCase;

    public AlaController(AlaUseCase alaUseCase) {
        this.alaUseCase = alaUseCase;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> consultarAlaByCondominioId(
            @RequestParam(name = "condominio_id") final Integer condominioId) {
        try {
            List<Ala> alaLista = alaUseCase.obterAlaPorCondominio(condominioId);
            return new ResponseEntity<Object>(alaLista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}