package br.com.bonabox.box.api.usecase.impl;

import br.com.bonabox.box.api.domain.entity.BoxInstalacaoEntity;
import br.com.bonabox.box.api.domain.repository.BoxInstalacaoRepositoryI;
import br.com.bonabox.box.api.usecase.GetBoxInstalacaoUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetBoxInstalacaoUseCaseImp implements GetBoxInstalacaoUseCase {

	private final BoxInstalacaoRepositoryI repositoryBoxInstalacaoRepositoryI;

	public GetBoxInstalacaoUseCaseImp(BoxInstalacaoRepositoryI repositoryBoxInstalacaoRepositoryI) {
		this.repositoryBoxInstalacaoRepositoryI = repositoryBoxInstalacaoRepositoryI;
	}

	@Override
	public BoxInstalacaoEntity execute(Integer boxId) {

		BoxInstalacaoEntity boxInstalacaoEntity = repositoryBoxInstalacaoRepositoryI.consultarByBoxId(boxId);

		return boxInstalacaoEntity;
	}

}
