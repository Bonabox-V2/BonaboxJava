package br.com.bonabox.condominio.api.usecase.impl;

import br.com.bonabox.condominio.api.domain.Pessoa;
import br.com.bonabox.condominio.api.domain.repository.PessoaRepositoryI;
import br.com.bonabox.condominio.api.domain.repository.entity.PessoaEntity;
import br.com.bonabox.condominio.api.usecase.PessoaUseCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaUseCaseImp implements PessoaUseCase {

	private final PessoaRepositoryI pessoaRepositoryI;

	public PessoaUseCaseImp(PessoaRepositoryI pessoaRepositoryI) {
		this.pessoaRepositoryI = pessoaRepositoryI;
	}

	@Override
	public Pessoa obterPessoaPorUnidade(Integer condominioId, Integer alaId, Integer blocoId, Integer unidadeId,
										Integer codigoTipoPessoa) {

		PessoaEntity pessoaEntity = pessoaRepositoryI.findContatoPessoaAdmin(condominioId, alaId, blocoId, unidadeId,
				codigoTipoPessoa);

		return new Pessoa(pessoaEntity.getPessoaId(), pessoaEntity.getNome(), pessoaEntity.getNumeroCelular(),
				pessoaEntity.getEmail());
	}

	@Override
	public Pessoa obterPessoaMoradorPrincipalPorUnidade(Integer condominioId, Integer alaId, Integer blocoId,
			Integer unidadeId) {

		List<PessoaEntity> pessoaLista = pessoaRepositoryI.findContatoPessoaMoradorDeUnidade(condominioId, alaId,
				blocoId, unidadeId);

		Optional<PessoaEntity> op = pessoaLista.stream().filter(p -> p.isEhPrincipal()).findFirst();
		PessoaEntity pessoaEntity = op.isPresent() ? op.get() : op.orElseThrow();

		return new Pessoa(pessoaEntity.getPessoaId(), pessoaEntity.getNome(), pessoaEntity.getNumeroCelular(),
				pessoaEntity.getEmail());

	}

	@Override
	public List<Pessoa> obterPessoaMoradorPorUnidadeList(Integer condominioId, Integer alaId, Integer blocoId,
			Integer unidadeId) {

		List<PessoaEntity> pessoaLista = pessoaRepositoryI.findContatoPessoaMoradorDeUnidade(condominioId, alaId,
				blocoId, unidadeId);

		List<Pessoa> retorno = new ArrayList<Pessoa>();
		
		for (PessoaEntity pessoaEntity : pessoaLista) {
			if (pessoaEntity.isEhPrincipal()) {
				retorno.add(new Pessoa(pessoaEntity.getPessoaId(), pessoaEntity.getNome(),
						pessoaEntity.getNumeroCelular(), pessoaEntity.getEmail()));
			}
		}

		return retorno;
	}

}
