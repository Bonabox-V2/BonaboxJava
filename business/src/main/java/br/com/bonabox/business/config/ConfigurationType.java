package br.com.bonabox.business.config;

import java.util.Map;

public class ConfigurationType {

	private Map<String, String> notification;

	public ConfigurationType() {
		super();
	}

	public Map<String, String> getNotification() {
		return notification;
	}

	public void setNotification(Map<String, String> notification) {
		this.notification = notification;
	}

}
