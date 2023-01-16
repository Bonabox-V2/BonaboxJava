package br.com.bonabox.business.config;

import br.com.bonabox.business.util.GenerateUID;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UIDConfiguration {

	private final GenerateUID generateUID;

	public UIDConfiguration(GenerateUID generateUID) {
		this.generateUID = generateUID;
	}

	@Bean("generate-uid-entrega")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public GenerateUID uidEntrega() {
		return generateUID;
	}
	
	@Bean("generate-uid-compartimento")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public GenerateUID uidCompartimento() {
		return generateUID;
	}

}
