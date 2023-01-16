package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;

import java.util.Map;

public interface EnviarNotificacaoDataProvider {
	
	boolean enviar(String texto, String numeroTelefone) throws DataProviderException;
	
	boolean enviarLista(Map<String, String> map) throws DataProviderException;
	
	EnviarNotificacaoDataProvider build(DataMDC dataMDC);

}