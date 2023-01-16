package br.com.bonabox.box.api.usecase;

import br.com.bonabox.box.api.domain.entity.BoxCompartimentosEntity;
import br.com.bonabox.box.api.domain.entity.BoxEntity;
import br.com.bonabox.box.api.usecase.ex.UseCaseException;

import java.util.Collection;

public interface GetBoxUseCase {
	
	public Object consultarByNumeroSerial(String numeroSerial);
	
	public Object consultarByNumeroSerialAndTipo(String numeroSerial, String tipo) throws UseCaseException;
	
	public Collection<BoxCompartimentosEntity> consultarCompartimentos(Integer boxId);
	
	public Collection<BoxEntity> consultarBoxAdicionalByOwner(Integer owner);

}
