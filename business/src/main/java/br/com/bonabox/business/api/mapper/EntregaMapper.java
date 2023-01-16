package br.com.bonabox.business.api.mapper;


import br.com.bonabox.business.api.models.CreateEntregaDataRequest;
import br.com.bonabox.business.api.models.CreateEntregaDataResponse;
import br.com.bonabox.business.api.models.UpdateStatusEntregaDataRequest;
import br.com.bonabox.business.domain.Entrega;

public class EntregaMapper {

	public static final Entrega toEntrega(CreateEntregaDataRequest dataRequest) {
		if (dataRequest == null)
			return null;
		return new Entrega(dataRequest.getNumeroSerial(), null, null, dataRequest.getEmpresaEntregadora());
	}

	public static final CreateEntregaDataResponse toCreateEntregaDataReponse(Entrega entrega) {
		if (entrega == null)
			return null;
		return new CreateEntregaDataResponse(entrega.getCodigoEntrega(), entrega.getDataHoraCriacao());
	}

	public static final Entrega toEntrega(UpdateStatusEntregaDataRequest entrega) {
		if (entrega == null)
			return null;
		return new Entrega(null, entrega.getEntregaId(), null, null);
	}

}
