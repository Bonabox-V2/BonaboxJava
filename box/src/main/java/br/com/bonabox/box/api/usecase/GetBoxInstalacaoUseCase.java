package br.com.bonabox.box.api.usecase;


import br.com.bonabox.box.api.domain.entity.BoxInstalacaoEntity;

public interface GetBoxInstalacaoUseCase {

	public BoxInstalacaoEntity execute(Integer boxId);

}
