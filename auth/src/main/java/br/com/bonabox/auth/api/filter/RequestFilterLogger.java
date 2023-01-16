package br.com.bonabox.auth.api.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;

public class RequestFilterLogger {
    private String requestJson;

    private String url;

    private String method;

    private String remoteCallerIp;

    private String userAgent;

    private Instant serverStartTime;

    private Instant remoteStartTime;

    private Instant appStartTime;

    public RequestFilterLogger(String requestJson, String url, String method, String remoteCallerIp, String userAgent, Instant serverStartTime, Instant remoteStartTime, Instant appStartTime) {
        this.requestJson = requestJson;
        this.url = url;
        this.method = method;
        this.remoteCallerIp = remoteCallerIp;
        this.userAgent = userAgent;
        this.serverStartTime = serverStartTime;
        this.remoteStartTime = remoteStartTime;
        this.appStartTime = appStartTime;
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

