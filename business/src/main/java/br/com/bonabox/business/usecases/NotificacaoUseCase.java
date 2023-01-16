package br.com.bonabox.business.usecases;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.domain.CompartimentoStatusTempResponse;
import br.com.bonabox.business.domain.webclient.StatusEntregaDataWebClient;

public interface NotificacaoUseCase {

	void notificar(CompartimentoStatusTempResponse compartimentoStatusTempResponse,
				   StatusEntregaDataWebClient statusEntregaDataWebClient, String codigoEntrega,
				   StatusEntregaDataWebClient dataWebClient, String dataHoraDeposito);
	
	NotificacaoUseCase build(DataMDC dataMdc);
	
}
