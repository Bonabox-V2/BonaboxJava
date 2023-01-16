package br.com.bonabox.condominio.api.controller;

import br.com.bonabox.condominio.api.domain.Bloco;
import br.com.bonabox.condominio.api.usecase.BlocoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/interno-condominio/api/v1/bloco")
public class BlocoController {

    private final BlocoUseCase blocoUseCase;

    public BlocoController(BlocoUseCase blocoUseCase) {
        this.blocoUseCase = blocoUseCase;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> consultarBlocoByCondominioId(
            @RequestParam(name = "condominio_id") final Integer condominioId,
            @RequestParam(name = "ala_id") final Integer alaId) {
        try {

            List<Bloco> blocoLista = blocoUseCase.obterBlocoPorCondominio(condominioId, alaId);

            return new ResponseEntity<Object>(blocoLista, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get-bloco", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> consultarBloco (
            @RequestParam(name = "bloco_id") final Integer blocoId) {
        try {

            Bloco bloco = blocoUseCase.obterBlocoPorCondominio(blocoId);

            return new ResponseEntity<Object>(bloco, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
