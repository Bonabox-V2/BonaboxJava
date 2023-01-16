package br.com.bonabox.condominio.api.usecase.impl;

import br.com.bonabox.condominio.api.domain.Condominio;
import br.com.bonabox.condominio.api.domain.CondominioEnderecos;
import br.com.bonabox.condominio.api.domain.repository.CondominioRepositoryI;
import br.com.bonabox.condominio.api.domain.repository.entity.CondominioEntity;
import br.com.bonabox.condominio.api.usecase.CondominioUseCase;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CondominioUseCaseImp implements CondominioUseCase {

	private final CondominioRepositoryI condominioRepositoryI;

	public CondominioUseCaseImp(CondominioRepositoryI condominioRepositoryI) {
		this.condominioRepositoryI = condominioRepositoryI;
	}

	@Override
	public Condominio obterCondominio(Integer condominioId) {

		CondominioEntity condominioEntity = condominioRepositoryI.getOne(condominioId);

		/*condominioEntity.getEnderecos().stream()
				.forEach(e -> System.out.println(e.getLogradouro() + " " + e.getBairro()));*/

		Set<CondominioEnderecos> enderecos = condominioEntity.getEnderecos().stream().map(m -> {
			return new CondominioEnderecos(m.getCep(), m.getLogradouro(), m.getComplemento(), m.getBairro(), m.getCidade(),
					m.getUf(), m.getNumero());
		}).collect(Collectors.toSet());

		return new Condominio(condominioEntity.getCondominioId(), condominioEntity.getNome(),
				condominioEntity.getAreaTotalMetro2(), condominioEntity.isPortariaVirtual(),
				condominioEntity.isHabilitadoCorreios(), enderecos);
	}

}
