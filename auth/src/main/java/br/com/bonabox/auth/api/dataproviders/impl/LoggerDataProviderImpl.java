package br.com.bonabox.auth.api.dataproviders.impl;


import br.com.bonabox.auth.api.dataproviders.LoggerGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoggerDataProviderImpl implements LoggerGateway {
    //private final WebClient webClient = WebClient.builder().baseUrl(this.urlInternoBonaBox).build();

    //@Value("${bonabox-business.server}")
    private String urlInternoBonaBox;

    public void sendLogger(String inputLogger) {
        /*try {
            ((WebClient.RequestBodySpec)this.webClient.post().uri("/box/api/v1/logger", new Object[0])).body(Mono.just(inputLogger), inputLogger.getClass()).retrieve()
                    .bodyToMono(Void.class).block();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
