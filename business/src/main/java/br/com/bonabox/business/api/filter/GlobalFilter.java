package br.com.bonabox.business.api.filter;

import br.com.bonabox.business.config.ConfigurationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.WebUtils;

import javax.crypto.Cipher;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

//@WebFilter(urlPatterns = {"/decrypt/*"}, filterName = "decryptFilter")
@Component
public class GlobalFilter implements Filter {

	private static final String CORRELATION_ID = "correlation-id";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("config")
	private ConfigurationType configurationType;
	
	@Autowired
	private AsyncServiceInterface asyncServiceInterface;
	
	@Autowired
	private Cipher cipher;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String correlationId = null;
		
		Instant start = Instant.now();

		HttpServletRequest reqHttpServletRequest = (HttpServletRequest) request;
		ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
		ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(
				(HttpServletResponse) response);
		
		HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(reqHttpServletRequest);
		
		if(!this.isValid(requestWrapper)) {
			//logger.info("Credenciais inválidas - [SEGURANÇA DESATIVADA]");
			logger.warn("Credenciais inválidas - [SEGURANÇA DESATIVADA]");
			
			wrapperResponse.setStatus(ContentCachingResponseWrapper.SC_UNAUTHORIZED); 
			//return;
		} else {
		
			if (reqHttpServletRequest.getHeader(CORRELATION_ID) == null) {
				correlationId = UUID.randomUUID().toString();
			} else {
				correlationId = reqHttpServletRequest.getHeader(CORRELATION_ID);
			}
	
			UriComponents uriComponents = ServletUriComponentsBuilder.fromRequestUri(reqHttpServletRequest).build();
			
			MDC.MAPS.put(Thread.currentThread().getName(), new DataMDC(correlationId, uriComponents.toString()));
			wrapperResponse.addHeader(CORRELATION_ID, correlationId);
		
		}
		
		chain.doFilter(wrapperRequest, wrapperResponse);
		
		this.logRequestResponse(correlationId, wrapperRequest, wrapperResponse, start, Instant.now());
	}
	
	private String calculoHash(String appKey) throws Exception {
		//String appCredential = reqHttpServletRequest.getHeader("app-credential");
		//String appKey = reqHttpServletRequest.getHeader("app-key");
		//String host = reqHttpServletRequest.getHeader("host");
		//String requestBody = reqHttpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		//int contentLength = reqHttpServletRequest.getContentLength();
		logger.info("Hash from '{}'",appKey);
		return appKey;
	}
	
	private final boolean isValid(HttpServletRequest reqHttpServletRequest) {
		
		String appKey = null;
		try {
			
			String appCredential = reqHttpServletRequest.getHeader("app-credential");
			String appHash = reqHttpServletRequest.getHeader("app-hash");
			appKey = reqHttpServletRequest.getHeader("app-key");
			
			//reqHttpServletRequest.getHeaderNames().asIterator().forEachRemaining(f -> logger.info(f + "\t"+reqHttpServletRequest.getHeader(f)));
			
			//String requestBody = reqHttpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			//String calculoHash = this.calculoHash(appKey);
//logger.info("Infos para calculo de hash: "+calculoHash);
			/*if (!configurationType.getNotification().get("ThisServer")
					.equals(reqHttpServletRequest.getHeader("host"))) {
				logger.warn("Servidor {} não permitido", reqHttpServletRequest.getHeader("host"));
				//this.logRequestResponse("empty", wrapperRequest, wrapperResponse, start, Instant.now());
				return false;
			}*/
			
			// Validar app-key
			// Calcula hash
			/*String sha256hex = Hashing.sha512().hashString(calculoHash, StandardCharsets.UTF_8).toString();
				logger.info("Local sha256hex: == Recebida appHash: {}",sha256hex.equals(appHash));
				if (appHash.equals(sha256hex)) {

					// Descriptografa

					byte[] decryptedMessageBytes = cipher.doFinal(Base64.getDecoder().decode(appCredential.getBytes()));
					String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

					// Busca credencial no banco de dados
					String credencialSaved = "1dLPJLRYeDOi8UU5tebgsAM0z7YcuKmF7g2yHoLABOX";

					logger.info("Local credencialSaved: == Recebida decryptedMessage: {}",credencialSaved.equals(decryptedMessage));

					// Valida
					if (credencialSaved.equals(decryptedMessage)) {
						return true;
					}
				}*/
			return "1dLPJLRYeD0U8g3PiIA1gaM0z7YcuKmF7g2yHoLA".equals(appKey);
		} catch (Exception e) {
			logger.error("Erro ao tentar autenticar requisição {}", e);
			return false;
		}finally {
			logger.info("isValid = {}", "1dLPJLRYeD0U8g3PiIA1gaM0z7YcuKmF7g2yHoLA".equals(appKey));
		}
	}

	private void logRequestResponse(String correlationId, ContentCachingRequestWrapper wrapperRequest,
			ContentCachingResponseWrapper wrapperResponse, Instant start, Instant end) {
		try {
			Instant remoteStartTime = null;
			String time = wrapperRequest.getHeader("remote-start-time");
			if (time != null) {
				remoteStartTime = Instant.parse(time);
			}
			
			String requestBodyStr = getRequestBody(wrapperRequest);
			
			if(requestBodyStr == null || requestBodyStr.isEmpty() || requestBodyStr.isBlank()) {
				requestBodyStr = "\"\"";
			}

			String url = wrapperRequest.getRequestURL().toString();
			if(url != null && wrapperRequest.getQueryString() != null) {
				url += "?"+ wrapperRequest.getQueryString();
			}
			
			RequestFilterLogger request = new RequestFilterLogger(requestBodyStr,
					url, wrapperRequest.getMethod(),
					wrapperRequest.getRemoteHost(), wrapperRequest.getHeader("user-agent"), start, remoteStartTime,
					null);

			byte[] responseArray = wrapperResponse.getContentAsByteArray();
			String responseBodyStr = new String(responseArray, wrapperResponse.getCharacterEncoding());
			wrapperResponse.copyBodyToResponse();

			if(responseBodyStr == null || responseBodyStr.isEmpty() || responseBodyStr.isBlank()) {
				responseBodyStr = "\"\"";
			}
			
			ResponseFilterLogger response = new ResponseFilterLogger(responseBodyStr, wrapperResponse.getStatus(),
					wrapperResponse.getHeader("content-length"), wrapperResponse.getHeader("keep-Alive"),
					wrapperResponse.getHeader("connection"));

			Duration timeTaken =  Duration.between(start, end);
			String log = new RequestResponseFilterLogger(correlationId, request, response, timeTaken)
					.toString();
			
			asyncServiceInterface.sendLogger(log, correlationId, timeTaken.toMillis(), request.getUrl(), request.getMethod());

			logger.info(log);
			
		} catch (Exception e) {
			logger.error("Tentando logar request e response da classe {} method: {}, message: {} {]",
					GlobalFilter.class.getName(), "logRequestResponse", e.getMessage(), e);
		}
	}

	private String getRequestBody(ContentCachingRequestWrapper request) {
		ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
		if (wrapper != null) {
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				String payload;
				try {
					payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
				} catch (UnsupportedEncodingException e) {
					payload = "[unknown]";
				}
				return payload.replaceAll("\\r|\\n", "");
			}
		}
		return "";
	}
}

class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    /**
     * construct a wrapper for this request
     * 
     * @param request
     */
    public HeaderMapRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    private final Map<String, String> headerMap = new HashMap<String, String>();

    /**
     * add a header with given name and value
     * 
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = super.getHeader(name);
        if (headerMap.containsKey(name)) {
            headerValue = headerMap.get(name);
        }
        return headerValue;
    }

    /**
     * get the Header names
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        for (String name : headerMap.keySet()) {
            names.add(name);
        }
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (headerMap.containsKey(name)) {
            values.add(headerMap.get(name));
        }
        return Collections.enumeration(values);
    }

}

class RequestResponseFilterLogger {

	private final String correlationId;
	private final RequestFilterLogger request;
	private final ResponseFilterLogger response;
	private final Duration timeTaken;

	public RequestResponseFilterLogger(String correlationId, RequestFilterLogger request, ResponseFilterLogger response,
			Duration timeTaken) {
		super();
		this.correlationId = correlationId;
		this.request = request;
		this.response = response;
		this.timeTaken = timeTaken;
	}

	@Override
	public String toString() {

		return new StringBuilder().append("{\"request\":").append(request).append(", \"response\":").append(response)
				.append(", \"correlationId\":\"").append(correlationId).append("\", \"timeTaken\":\"")
				.append(timeTaken.toMillis()).append("\"}").toString();

	}
}

class RequestFilterLogger {

	private final String requestJson;
	private final String url;
	private final String method;
	private final String remoteCallerIp;
	private final String userAgent;
	private final Instant serverStartTime;
	private final Instant remoteStartTime;
	private final Instant appStartTime;

	public RequestFilterLogger(String requestJson, String url, String method, String remoteCallerIp, String userAgent,
			Instant serverStartTime, Instant remoteStartTime, Instant appStartTime) {
		super();
		this.requestJson = requestJson;
		this.url = url;
		this.method = method;
		this.remoteCallerIp = remoteCallerIp;
		this.userAgent = userAgent;
		this.serverStartTime = serverStartTime;
		this.remoteStartTime = remoteStartTime;
		this.appStartTime = appStartTime;
	}
	
	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

	@Override
	public String toString() {

		return new StringBuilder().append("{\"body\":").append(requestJson).append(", \"url\":\"").append(url)
				.append("\", \"method\":\"").append(method).append("\", \"remoteCallerIp\":\"").append(remoteCallerIp)
				.append("\", \"userAgent\":\"").append(userAgent).append("\", \"serverStartTime\":\"")
				.append(serverStartTime).append("\", \"remoteStartTime\":\"").append(remoteStartTime)
				.append("\", \"appStartTime\":\"").append(appStartTime).append("\"}").toString();
	}
}

class ResponseFilterLogger {
	private final String responseJson;
	private final int responseHttpCode;
	private final String contentSize;
	private final String keepAlive;
	private final String connection;

	public ResponseFilterLogger(String responseJson, int responseHttpCode, String contentSize, String keepAlive,
			String connection) {
		super();
		this.responseJson = responseJson;
		this.responseHttpCode = responseHttpCode;
		this.contentSize = contentSize;
		this.keepAlive = keepAlive;
		this.connection = connection;
	}

	@Override
	public String toString() {
		return "{\"body\":" + responseJson + ", \"httpCode\":\"" + responseHttpCode + "\", \"contentSize\":\""
				+ contentSize + "\", \"keepAlive\":\"" + keepAlive + "\", \"connection\":\"" + connection + "\"}";
	}
}