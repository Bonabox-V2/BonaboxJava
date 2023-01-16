package br.com.bonabox.business.dataproviders.impl;

import br.com.bonabox.business.dataproviders.ex.DataProviderException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class EnviarEmailAWSDataProviderImpl {

	static final String FROM = "sender@example.com";
	static final String TO = "recipient@example.com";
	static final String CONFIGSET = "ConfigSet";
	static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";
	static final String HTMLBODY = "<h1>Amazon SES test (AWS SDK for Java)</h1>"
			+ "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
			+ "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>" + "AWS SDK for Java</a>";

	static final String TEXTBODY = "This email was sent through Amazon SES " + "using the AWS SDK for Java.";

	public boolean enviar(String from, String to, String condigSet, String subject, String htmlBody, String textBody)
			throws DataProviderException {

		try {
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					// Replace US_WEST_2 with the AWS Region you're using for
					// Amazon SES.
					.withRegion(Regions.US_EAST_2).build();
			SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(to))
					.withMessage(new Message()
							.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBody))
									.withText(new Content().withCharset("UTF-8").withData(textBody)))
							.withSubject(new Content().withCharset("UTF-8").withData(subject)))
					.withSource(from);
					// Comment or remove the next line if you are not using a
					// configuration set
					//.withConfigurationSetName(condigSet);
			client.sendEmail(request);
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent. Error message: " + ex.getMessage());
		}
		return false;
	}

	public String getFile() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		try (InputStream inputStream = classLoader.getResourceAsStream("./email-qrcode.html")) {
			return readFromInputStream(inputStream);
		}
	}

	private String readFromInputStream(InputStream inputStream) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}
	
	/*public static void main(String[] args) throws Exception {
		new EnviarEmailAWSDataProviderImpl().enviar("informa@mail.bonabox.com.br",
				null, "Comunicado de entrega", "<html>Teste</html>", "This email was sent through Amazon SES " + "using the AWS SDK for Java.");
	}*/
}
