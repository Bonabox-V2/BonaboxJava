package br.com.bonabox.condominio.api.usecase;

import br.com.bonabox.condominio.api.domain.Ala;

import java.util.List;

public interface AlaUseCase {

    public  List<Ala> obterAlaPorCondominio(Integer condominioId);

}

