package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.EnviarNotificacaoDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.http.HttpStatus;

import java.util.Map;

//@Component(value = "")
public class EnviarWhatsappTwilioDataProviderImpl implements EnviarNotificacaoDataProvider {

	public static final String ACCOUNT_SID = "AC7b61418326c5cc926f7d0e23ab7973a9";
	public static final String AUTH_TOKEN = "3357772f1c5de0c8ce5b78133e434b65";
	
	private DataMDC dataMDC;

	public boolean enviar(String texto, String numeroTelefone) throws DataProviderException {

		try {

			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			Message message = Message.creator(new com.twilio.type.PhoneNumber(numeroTelefone),
					"MGd8d0cfc4a0cd1947587ef7339081627d", texto).create();
		

			System.out.println(message.getSid());

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataProviderException(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public boolean enviarLista(Map<String, String> map) throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public EnviarNotificacaoDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}

}
