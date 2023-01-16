package br.com.bonabox.auth.api.filter;

import br.com.bonabox.auth.api.dataproviders.LoggerGateway;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AsyncService implements AsyncServiceInterface {
    private final LoggerGateway loggerGateway;

    public AsyncService(LoggerGateway loggerGateway) {
        this.loggerGateway = loggerGateway;
    }

    @Async("threadPoolTaskExecutor")
    public void sendLogger(String inputLogger, String correlationId, long timeTaken) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule((Module)new JSR310Module());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            String input = mapper
                    .writeValueAsString(new LoggerInterno(Integer.valueOf(0), LocalDateTime.now(), inputLogger, correlationId, timeTaken));
            this.loggerGateway.sendLogger(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
