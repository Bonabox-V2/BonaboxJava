package br.com.bonabox.business.dataproviders;


import br.com.bonabox.business.api.filter.DataMDC;

import java.awt.image.BufferedImage;

public interface GenerateQRCodeDataProvider {

	BufferedImage generate(String codigo) throws Exception;

	BufferedImage obter(String codigo) throws Exception;
	
	GenerateQRCodeDataProvider build(DataMDC dataMDC);

}
