package br.com.bonabox.condominio.api.usecase.impl;

import br.com.bonabox.condominio.api.domain.Ala;
import br.com.bonabox.condominio.api.domain.Bloco;
import br.com.bonabox.condominio.api.domain.repository.AlaRepositoryI;
import br.com.bonabox.condominio.api.domain.repository.BlocoRepositoryI;
import br.com.bonabox.condominio.api.usecase.AlaUseCase;
import br.com.bonabox.condominio.api.usecase.BlocoUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlocoUseCaseImp implements BlocoUseCase {

    private final BlocoRepositoryI blocoRepositoryI;

    public BlocoUseCaseImp(BlocoRepositoryI blocoRepositoryI) {
        this.blocoRepositoryI = blocoRepositoryI;
    }

    @Override
    public List<Bloco> obterBlocoPorCondominio(Integer condominioId, Integer alaId) {

        List<Bloco> blocoEntity = blocoRepositoryI.findBlocoByCondominioId(condominioId, alaId);

        return blocoEntity.stream().map(m -> {
            return new Bloco(m.getBlocoId(), m.getLabel(), m.getNome(), m.getDescricao());
        }).collect(Collectors.toList());

    }

    @Override
    public Bloco obterBlocoPorCondominio(Integer blocoId) {
        Bloco m = blocoRepositoryI.findById(blocoId).get();
        return new Bloco(m.getBlocoId(), m.getLabel(), m.getNome(), m.getDescricao());
    }

}

