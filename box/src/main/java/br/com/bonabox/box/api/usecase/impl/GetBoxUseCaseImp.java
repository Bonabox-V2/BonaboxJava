package br.com.bonabox.box.api.usecase.impl;

import br.com.bonabox.box.api.domain.entity.BoxCompartimentosEntity;
import br.com.bonabox.box.api.domain.entity.BoxEntity;
import br.com.bonabox.box.api.domain.repository.BoxCompartimentosRepositoryI;
import br.com.bonabox.box.api.domain.repository.BoxRepositoryI;
import br.com.bonabox.box.api.usecase.GetBoxUseCase;
import br.com.bonabox.box.api.usecase.ex.UseCaseException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class GetBoxUseCaseImp implements GetBoxUseCase {

	private final BoxRepositoryI repositoryInterface;
	private final BoxCompartimentosRepositoryI boxCompartimentosRepositoryI;

	public GetBoxUseCaseImp(BoxRepositoryI repositoryInterface,
                            BoxCompartimentosRepositoryI boxCompartimentosRepositoryI) {
		this.repositoryInterface = repositoryInterface;
		this.boxCompartimentosRepositoryI = boxCompartimentosRepositoryI;
	}

	@Override
	public Object consultarByNumeroSerial(String numeroSerial) {

		Object obj = repositoryInterface.findBoxBySerialNumber(numeroSerial);

		return obj;
	}

	@Override
	public Object consultarByNumeroSerialAndTipo(String numeroSerial, String tipo) throws UseCaseException {
		try {

			Object obj = repositoryInterface.findBoxBySerialNumberAndTipo(numeroSerial, tipo);

			Objects.requireNonNull(obj, "Dados incorretos!");

			return obj;
		} catch (NullPointerException e) {
			throw new UseCaseException(e);
		} catch (Exception e) {
			throw new UseCaseException(e, "Erro genérico");
		}
	}

	@Override
	public Collection<BoxCompartimentosEntity> consultarCompartimentos(Integer boxId) {
		Collection<BoxCompartimentosEntity> lista = boxCompartimentosRepositoryI.consultarCompartimentos(boxId);
		return lista;
	}

	@Override
	public Collection<BoxEntity> consultarBoxAdicionalByOwner(Integer owner) {
		Collection<BoxEntity> lista = repositoryInterface.findBoxAdicionalByOwner(owner);
		return lista;
	}

}
