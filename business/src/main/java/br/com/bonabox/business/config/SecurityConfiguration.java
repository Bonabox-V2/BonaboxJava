package br.com.bonabox.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Configuration
public class SecurityConfiguration {

	/*private final SecureRestTemplateProperties properties;

	public SecurityConfiguration(SecureRestTemplateProperties properties) {
		this.properties = properties;
	}*/
	
	@Bean
	public Cipher cipher() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException {

		File privateKeyFile = new File("./private.key");
		byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

		Cipher decryptCipher = Cipher.getInstance("RSA");
		decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

		return decryptCipher;
		
	}

	/*RestTemplate restTemplate() throws Exception {
		final SSLContext sslContext;
		try {
			sslContext = SSLContextBuilder.create()
					.loadTrustMaterial(new URL(properties.getTrustStore()), properties.getTrustStorePassword())
					.setProtocol(properties.getProtocol()).build();
		} catch (Exception e) {
			throw new IllegalStateException("Failed to setup client SSL context", e);
		} finally {
			// it's good security practice to zero out passwords,
			// which is why they're char[]
			Arrays.fill(properties.getTrustStorePassword(), (char) 0);
		}

		final HttpClient httpClient = HttpClientBuilder.create().setSSLContext(sslContext).build();

		final ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		return new RestTemplate(requestFactory);
	}*/

}

//@ConfigurationProperties("secure-rest")
class SecureRestTemplateProperties {

	/**
	 * URL location, typically with file:// scheme, of a CA trust store file in JKS
	 * format.
	 */
	String trustStore;

	/**
	 * The store password of the given trust store.
	 */
	char[] trustStorePassword;

	/**
	 * One of the SSLContext algorithms listed at
	 * https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SSLContext
	 * .
	 */
	String protocol = "TLSv1.2";

	public String getTrustStore() {
		return trustStore;
	}

	public void setTrustStore(String trustStore) {
		this.trustStore = trustStore;
	}

	public char[] getTrustStorePassword() {
		return trustStorePassword;
	}

	public void setTrustStorePassword(char[] trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

}
