package br.com.bonabox.condominio.api.usecase.impl;

import br.com.bonabox.condominio.api.domain.CondominioComposicao;
import br.com.bonabox.condominio.api.domain.repository.CondominioComposicaoRepositoryI;
import br.com.bonabox.condominio.api.domain.repository.entity.CondominioComposicaoEntity;
import br.com.bonabox.condominio.api.usecase.CondominioComposicaoUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CondominioComposicaoUseCaseImp implements CondominioComposicaoUseCase {

	private final CondominioComposicaoRepositoryI condominioComposicaoRepositoryI;

	public CondominioComposicaoUseCaseImp(CondominioComposicaoRepositoryI condominioComposicaoRepositoryI) {
		this.condominioComposicaoRepositoryI = condominioComposicaoRepositoryI;
	}

	@Override
	public List<CondominioComposicao> obterComposicao(Integer condominioId, Integer alaId, Integer blocoId,
													  Integer unidadeId) {

		List<CondominioComposicaoEntity> cComposicaoEntity = condominioComposicaoRepositoryI
				.findComposicao(condominioId, alaId, blocoId, unidadeId);

		return cComposicaoEntity.stream().map(m -> {
			return new CondominioComposicao(m.getCondominioComposicaoId(), m.getCondominioId(), m.getAlaId(),
					m.getBlocoId(), m.getUnidadeId());
		}).collect(Collectors.toList());

	}

}
