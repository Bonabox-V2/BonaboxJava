package br.com.bonabox.auth.api.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.File;

@Configuration
@Order(1)
public class DataConfigurations {
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean({"config"})
    public ConfigurationType config() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ConfigurationType configurationType = (ConfigurationType) objectMapper
                    .readValue(new File("auth-" + this.activeProfile + "-configurations.json"), ConfigurationType.class);
            return configurationType;
        } catch (Exception e) {
            e.printStackTrace();
            return new ConfigurationType();
        }
    }
}
