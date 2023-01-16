package br.com.bonabox.condominio.api.usecase;


import br.com.bonabox.condominio.api.domain.CondominioEnderecos;

public interface CondominioEnderecoUseCase {

	public CondominioEnderecos consultar(Integer condominioId);

}
