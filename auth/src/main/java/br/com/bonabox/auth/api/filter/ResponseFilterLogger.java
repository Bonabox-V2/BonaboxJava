package br.com.bonabox.auth.api.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseFilterLogger {
    private String responseJson;

    private int responseHttpCode;

    private String contentSize;

    private String keepAlive;

    private String connection;

    public ResponseFilterLogger(String responseJson, int responseHttpCode, String contentSize, String keepAlive, String connection) {
        this.responseJson = responseJson;
        this.responseHttpCode = responseHttpCode;
        this.contentSize = contentSize;
        this.keepAlive = keepAlive;
        this.connection = connection;
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
