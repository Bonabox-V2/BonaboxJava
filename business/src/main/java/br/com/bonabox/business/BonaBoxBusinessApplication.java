package br.com.bonabox.business;

import br.com.bonabox.business.config.ConfigurationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class BonaBoxBusinessApplication {

	
	@Autowired
	@Qualifier("config")
	private ConfigurationType config;
	
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BonaBoxBusinessApplication.class, args);
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(config.getNotification().get("HostEmail"));
	    mailSender.setPort(Integer.parseInt(config.getNotification().get("PortEmail")));
	    
	    mailSender.setUsername(config.getNotification().get("UserEmail"));
	    mailSender.setPassword(config.getNotification().get("PassEmail"));
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    
	    return mailSender;
	}
	
}
