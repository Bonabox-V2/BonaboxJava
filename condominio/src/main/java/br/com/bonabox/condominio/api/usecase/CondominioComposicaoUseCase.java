package br.com.bonabox.condominio.api.usecase;

import br.com.bonabox.condominio.api.domain.CondominioComposicao;

import java.util.List;

public interface CondominioComposicaoUseCase {

	public  List<CondominioComposicao> obterComposicao(Integer condominioId, Integer alaId, Integer blocoId, Integer unidadeId);
	
}
