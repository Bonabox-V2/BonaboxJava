package br.com.bonabox.condominio.api.usecase.impl;


import br.com.bonabox.condominio.api.domain.Unidade;
import br.com.bonabox.condominio.api.domain.repository.UnidadeRepositoryI;
import br.com.bonabox.condominio.api.domain.repository.entity.UnidadeEntity;
import br.com.bonabox.condominio.api.usecase.UnidadeUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadeUseCaseImp implements UnidadeUseCase {

	private final UnidadeRepositoryI unidadeRepositoryI;

	public UnidadeUseCaseImp(UnidadeRepositoryI unidadeRepositoryI) {
		this.unidadeRepositoryI = unidadeRepositoryI;
	}

	@Override
	public List<Unidade> obterUnidadePorCondominio(Integer condominioId, Integer alaId, Integer blocoId) {

		List<UnidadeEntity> unidadeEntity = unidadeRepositoryI.findUnidadeByCondominioId(condominioId, alaId, blocoId);

		return unidadeEntity.stream().map(m -> {
			return new Unidade(m.getUnidadeId(), m.getPiso(), m.getNumeroUnidade(), m.getLabelUnidade());
		}).collect(Collectors.toList());

	}

	@Override
	public Unidade obterUnidadePorCodigo(Integer codigoUnidade) {
		UnidadeEntity m = unidadeRepositoryI.findById(codigoUnidade).get();
		return new Unidade(m.getUnidadeId(), m.getPiso(), m.getNumeroUnidade(), m.getLabelUnidade());
	}

}
