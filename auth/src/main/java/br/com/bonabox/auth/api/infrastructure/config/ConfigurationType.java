package br.com.bonabox.auth.api.infrastructure.config;
import java.util.Map;

public class ConfigurationType {
    private Map<String, String> notification;

    public Map<String, String> getNotification() {
        return this.notification;
    }

    public void setNotification(Map<String, String> notification) {
        this.notification = notification;
    }
}