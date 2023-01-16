package br.com.bonabox.condominio.api.usecase;

import br.com.bonabox.condominio.api.domain.Condominio;

public interface CondominioUseCase {

	public Condominio obterCondominio(Integer condominioId);
	
}
