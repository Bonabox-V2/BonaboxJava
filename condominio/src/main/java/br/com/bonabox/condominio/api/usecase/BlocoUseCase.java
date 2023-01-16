package br.com.bonabox.condominio.api.usecase;

import br.com.bonabox.condominio.api.domain.Bloco;

import java.util.List;

public interface BlocoUseCase {

    public List<Bloco> obterBlocoPorCondominio(Integer condominioId, Integer alaId);

    public  Bloco obterBlocoPorCondominio(Integer blocoId);

}
