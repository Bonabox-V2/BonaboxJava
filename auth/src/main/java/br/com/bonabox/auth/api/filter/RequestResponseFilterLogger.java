package br.com.bonabox.auth.api.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;

public class RequestResponseFilterLogger {
    private String correlationId;

    private RequestFilterLogger request;

    private ResponseFilterLogger response;

    private Duration timeTaken;

    public RequestResponseFilterLogger(String correlationId, RequestFilterLogger request, ResponseFilterLogger response, Duration timeTaken) {
        this.correlationId = correlationId;
        this.request = request;
        this.response = response;
        this.timeTaken = timeTaken;
    }

    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
