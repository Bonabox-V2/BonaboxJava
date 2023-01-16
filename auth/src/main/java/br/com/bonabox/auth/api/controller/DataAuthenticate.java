package br.com.bonabox.auth.api.controller;

public class DataAuthenticate {
    private String token;
    private String hash;

    public DataAuthenticate(String token, String hash) {
        this.token = token;
        this.hash = hash;
    }

    public String getToken() {
        return this.token;
    }

    public String getHash() {
        return this.hash;
    }
}