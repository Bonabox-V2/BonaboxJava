package br.com.bonabox.business.api.controller;

import br.com.bonabox.business.dataproviders.GenerateQRCodeDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/box/api/v1/view")
public class VisualizerController {
	
	@Autowired
	private GenerateQRCodeDataProvider generateQRCodeDataProvider;
	
	@GetMapping(value = "/notification/{codigo}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<Object> qrCodeEntrega(@PathVariable("codigo") String codigo) {
		try {
			
			var codeDataType = generateQRCodeDataProvider.obter(codigo);
			
			//QRCodeDataType codeDataType = UpdateFinalizarEntregaUseCaseImpl.MAP_QRCODE.get(codigo);

			Objects.requireNonNull(codeDataType, "");

			return new ResponseEntity<Object>(codeDataType, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
