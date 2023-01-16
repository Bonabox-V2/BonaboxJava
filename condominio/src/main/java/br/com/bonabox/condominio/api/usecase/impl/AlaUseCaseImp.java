package br.com.bonabox.condominio.api.usecase.impl;

import br.com.bonabox.condominio.api.domain.Ala;
import br.com.bonabox.condominio.api.domain.repository.AlaRepositoryI;
import br.com.bonabox.condominio.api.usecase.AlaUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class AlaUseCaseImp implements AlaUseCase {

    private final AlaRepositoryI alaRepositoryI;

    public AlaUseCaseImp(AlaRepositoryI alaRepositoryI) {
        this.alaRepositoryI = alaRepositoryI;
    }

    @Override
    public List<Ala> obterAlaPorCondominio(Integer condominioId) {

        List<Ala> alaEntity = alaRepositoryI.findAlaByCondominioId(condominioId);

        return alaEntity.stream().map(m -> {
            return new Ala(m.getAlaId(), m.getLabel(), m.getNome(), m.getDescricao());
        }).collect(Collectors.toList());

    }

}

