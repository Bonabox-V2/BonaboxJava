package br.com.bonabox.business.api.mapper;


import br.com.bonabox.business.api.models.UpdateStatusEntregaDataRequest;
import br.com.bonabox.business.domain.StatusEntrega;

public class StatusEntregaMapper {

	public static final StatusEntrega toStatusEntrega(UpdateStatusEntregaDataRequest entrega) {
		if (entrega == null)
			return null;
		
			return new StatusEntrega(entrega.getEntregaId(), entrega.getStatusEntregaId(), entrega.getSituacaoId(),
					entrega.getCodigoEntregador(), entrega.getDataHora(), entrega.getCodigoAla(), entrega.getCodigoBloco(),
					entrega.getCodigoUnidade(), entrega.getCodigoNumeroPortaBox(), entrega.getNomeMorador());
	}

}
