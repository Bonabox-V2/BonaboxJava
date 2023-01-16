package br.com.bonabox.condominio.api.usecase.impl;

import br.com.bonabox.condominio.api.domain.CondominioEnderecos;
import br.com.bonabox.condominio.api.domain.repository.CondominioEnderecoRepositoryI;
import br.com.bonabox.condominio.api.domain.repository.entity.CondominioEnderecosEntity;
import br.com.bonabox.condominio.api.usecase.CondominioEnderecoUseCase;
import org.springframework.stereotype.Service;

@Service
public class CondominioEnderecoUseCaseImp implements CondominioEnderecoUseCase {

	/*private final CondominioEnderecoRepositoryI condominioEnderecoRepositoryI;

	public CondominioEnderecoUseCaseImp(CondominioEnderecoRepositoryI condominioEnderecoRepositoryI) {
		this.condominioEnderecoRepositoryI = condominioEnderecoRepositoryI;
	}*/

	@Override
	public CondominioEnderecos consultar (Integer condominioId) {

		//CondominioEnderecosEntity enderecoEnt = condominioEnderecoRepositoryI.findById(condominioId).get();

		//CondominioEnderecos condominioEnderecos = new CondominioEnderecos(enderecoEnt.getCep(), enderecoEnt.getLogradouro(), enderecoEnt.getComplemento(), enderecoEnt.getBairro(), enderecoEnt.getCidade(), enderecoEnt.getUf());
		
		return null;
	}

}
