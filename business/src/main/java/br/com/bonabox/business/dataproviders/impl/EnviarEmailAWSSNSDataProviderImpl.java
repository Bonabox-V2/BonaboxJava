package br.com.bonabox.business.dataproviders.impl;


import br.com.bonabox.business.api.filter.DataMDC;
import br.com.bonabox.business.dataproviders.EnviarNotificacaoDataProvider;
import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

//@Component
public class EnviarEmailAWSSNSDataProviderImpl implements EnviarNotificacaoDataProvider {

	private DataMDC dataMDC;

	public boolean enviar(String mensagem, String topico) {

		try {

			PublishRequest request = PublishRequest.builder().message(mensagem).topicArn(topico).build();

			SnsClient snsClient = SnsClient.builder().region(Region.of("sa-east-1")).build();

			// pubTopic(snsClient, message, topicArn);

			PublishResponse result = snsClient.publish(request);
			System.out
					.println(result.messageId() + " Message sent. Status was " + result.sdkHttpResponse().statusCode());

			snsClient.close();

			return true;
		} catch (SnsException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			return false;
		}
	}

	@Override
	public boolean enviarLista(Map<String, String> map) throws DataProviderException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnviarNotificacaoDataProvider build(DataMDC dataMDC) {
		this.dataMDC = dataMDC;
		return this;
	}
	
}

class AmazonSESSample {

	// Replace sender@example.com with your "From" address.
	// This address must be verified.
	static final String FROM = "bonabox_nao_responda@newsafe.com.br";
	static final String FROMNAME = "Bonabox - chegou encomenda!";

	// Replace recipient@example.com with a "To" address. If your account
	// is still in the sandbox, this address must be verified.
	static final String TO = "lmjpaz@yahoo.com.br";

	// Replace smtp_username with your Amazon SES SMTP user name.
	static final String SMTP_USERNAME = "bonabox_nao_responda@newsafe.com.br";

	// Replace smtp_password with your Amazon SES SMTP password.
	static final String SMTP_PASSWORD = "QEJDaWjhkh";

	// The name of the Configuration Set to use for this message.
	// If you comment out or remove this variable, you will also need to
	// comment out or remove the header below.
	//static final String CONFIGSET = "ConfigSet";

	// Amazon SES SMTP host name. This example uses the US West (Oregon) region.
	// See
	// https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
	// for more information.
	static final String HOST = "mail.newsafe.com.br";

	// The port you will connect to on the Amazon SES SMTP endpoint.
	static final int PORT = 587;

	static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";

	static final String BODY = String.join(System.getProperty("line.separator"), "<h1>Amazon SES SMTP Email Test</h1>",
			"<p>This email was sent with Amazon SES using the ",
			"<a href='https://github.com/javaee/javamail'>Javamail Package</a>",
			" for <a href='https://www.java.com'>Java</a>.");

	public static void exec () throws Exception {

		// Create a Properties object to contain connection configuration information.
		Properties props = System.getProperties();
		/*props.put("mail.transport.protocol", "imap");
		props.put("mail.imap.port", "143");
		props.put("mail.imap.starttls.enable", "false");
		props.put("mail.imap.auth", "false");*/

		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", "143");
		props.put("mail.smtp.ssl.enable", "false");
		props.put("mail.smtp.auth", "false");
		
		// Create a Session object to represent a mail session with the specified
		// properties.
		Session session = Session.getDefaultInstance(props);

		// Create a message with the specified information.
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(FROM, FROMNAME));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
		msg.setSubject(SUBJECT);
		msg.setContent(BODY, "text/html");

		// Add a configuration set header. Comment or delete the
		// next line if you are not using a configuration set
		//msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

		// Create a transport.
		Transport transport = session.getTransport();

		// Send the message.
		try {
			System.out.println("Sending...");

			// Connect to Amazon SES using the SMTP username and password you specified
			// above.
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

			// Send the email.
			transport.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			// Close and terminate the connection.
			transport.close();
		}
	}
}