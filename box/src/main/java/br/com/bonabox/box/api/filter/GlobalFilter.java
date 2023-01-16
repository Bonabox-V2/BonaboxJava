package br.com.bonabox.box.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

//@WebFilter(urlPatterns = {"/decrypt/*"}, filterName = "decryptFilter")
@Component
public class GlobalFilter implements Filter {

	private static final String CORRELATION_ID = "correlation-id";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AsyncServiceInterface asyncServiceInterface;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String correlationId = null;
		Instant start = Instant.now();

		HttpServletRequest reqHttpServletRequest = (HttpServletRequest) request;
		ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
		ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(
				(HttpServletResponse) response);

		if (reqHttpServletRequest.getHeader(CORRELATION_ID) == null) {
			correlationId = UUID.randomUUID().toString();
		} else {
			correlationId = reqHttpServletRequest.getHeader(CORRELATION_ID);
		}

		//MDC.MAPS.put(Thread.currentThread().getName(), correlationId);
		wrapperResponse.addHeader(CORRELATION_ID, correlationId);

		chain.doFilter(wrapperRequest, wrapperResponse);

		this.logRequestResponse(correlationId, wrapperRequest, wrapperResponse, start, Instant.now());
	}

	private void logRequestResponse(String correlationId, ContentCachingRequestWrapper wrapperRequest,
			ContentCachingResponseWrapper wrapperResponse, Instant start, Instant end) {
		try {
			Instant remoteStartTime = null;
			String time = wrapperRequest.getHeader("remote-start-time");
			if (time != null) {
				remoteStartTime = Instant.parse(time);
			}

			String body = "";
			if(!wrapperRequest.getMethod().equals("GET")) {
				body = getRequestBody(wrapperRequest);
			}
			
			String url = wrapperRequest.getRequestURL().toString();
			if(url != null && wrapperRequest.getQueryString() != null) {
				url += "?"+ wrapperRequest.getQueryString();
			}
			
			RequestFilterLogger request = new RequestFilterLogger(body,
					url, wrapperRequest.getMethod(),
					wrapperRequest.getRemoteHost(), wrapperRequest.getHeader("user-agent"), start, remoteStartTime,
					null);

			byte[] responseArray = wrapperResponse.getContentAsByteArray();
			String responseBodyStr = new String(responseArray, wrapperResponse.getCharacterEncoding());
			wrapperResponse.copyBodyToResponse();
			
			ResponseFilterLogger response = new ResponseFilterLogger(responseBodyStr, wrapperResponse.getStatus(),
					wrapperResponse.getHeader("content-length"), wrapperResponse.getHeader("keep-Alive"),
					wrapperResponse.getHeader("connection"));

			
			Duration timeTaken = Duration.between(start, end);
			String log = new RequestResponseFilterLogger(correlationId, request, response, timeTaken)
					.toString();
			
			logger.info(log);
			
			asyncServiceInterface.sendLogger(log, correlationId, timeTaken.toMillis(), request.getUrl(), request.getMethod());
			
		} catch (Exception e) {
			logger.error("Tentando logar request e response da classe {} method: {}, message: {}",
					GlobalFilter.class.getName(), "logRequestResponse", e.getMessage());
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

class RequestResponseFilterLogger {

	private String correlationId;
	private RequestFilterLogger request;
	private ResponseFilterLogger response;
	private Duration timeTaken;

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
		return "{\"request\":" + request + ", \"response\":" + response + ", \"correlationId\":\"" + correlationId
				+ "\", \"timeTaken\":\"" + timeTaken.toMillis() + "\"}";
	}
}

class RequestFilterLogger {

	private String requestJson;
	private String url;
	private String method;
	private String remoteCallerIp;
	private String userAgent;
	private Instant serverStartTime;
	private Instant remoteStartTime;
	private Instant appStartTime;

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
		return "{\"body\":" + requestJson + ", \"url\":\"" + url + "\", \"method\":\"" + method
				+ "\", \"remoteCallerIp\":\"" + remoteCallerIp + "\", \"userAgent\":\"" + userAgent
				+ "\", \"serverStartTime\":\"" + serverStartTime + "\", \"remoteStartTime\":\"" + remoteStartTime
				+ "\", \"appStartTime\":\"" + appStartTime + "\"}";
	}
}

class ResponseFilterLogger {
	private String responseJson;
	private int responseHttpCode;
	private String contentSize;
	private String keepAlive;
	private String connection;

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