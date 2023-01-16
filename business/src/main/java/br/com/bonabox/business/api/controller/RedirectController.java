package br.com.bonabox.business.api.controller;

import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class RedirectController {

	public RedirectController() {
		super();
	}

	@RequestMapping(value = "/**", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> proxy(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, ProxyExchange<byte[]> proxy) throws Exception {

		return null;
		//return proxyUseCase.execute(request, response, proxy);
	}
	
}
