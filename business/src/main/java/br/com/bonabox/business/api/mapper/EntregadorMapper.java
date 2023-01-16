package br.com.bonabox.business.api.mapper;



import br.com.bonabox.business.api.models.CreateEntregadorDataRequest;
import br.com.bonabox.business.api.models.CreateEntregadorDataResponse;
import br.com.bonabox.business.domain.Entregador;

import java.time.LocalDateTime;

public class EntregadorMapper {

	public static final Entregador toEntregador(CreateEntregadorDataRequest dataRequest) {
		if (dataRequest == null)
			return null;
		return new Entregador(0, dataRequest.getDdi(), dataRequest.getDdd(), dataRequest.getTelefone(),
				dataRequest.getNome(), LocalDateTime.now(), dataRequest.getNumeroSerial());
	}

	public static final CreateEntregadorDataResponse toCreateEntregadorDataReponse(Entregador entregador) {
		if (entregador == null)
			return null;
		return new CreateEntregadorDataResponse(entregador.getEntregadorId(), entregador.getDdi(), entregador.getDdd(),
				entregador.getTelefone(), entregador.getNome(), entregador.getDataHoraCadastro());
	}

}
