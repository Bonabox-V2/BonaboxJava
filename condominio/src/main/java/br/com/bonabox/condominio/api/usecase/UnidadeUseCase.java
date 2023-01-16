package br.com.bonabox.condominio.api.usecase;


import br.com.bonabox.condominio.api.domain.Unidade;

import java.util.List;

public interface UnidadeUseCase {

	public List<Unidade> obterUnidadePorCondominio(Integer condominioId, Integer alaId, Integer blocoId);

	public Unidade obterUnidadePorCodigo(Integer codigoUnidade);

}
