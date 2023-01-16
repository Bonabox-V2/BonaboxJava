package br.com.bonabox.business.api.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class BoxServletRequestWrapper extends HttpServletRequestWrapper {

	private final Map<String, String> headerMap;

	public void addHeader(String name, String value) {
		headerMap.put(name, value);
	}

	public BoxServletRequestWrapper(HttpServletRequest request) {
		super(request);
		headerMap = new HashMap<String, String>();
	}

	public Enumeration<String> getHeaderNames() {
		HttpServletRequest request = (HttpServletRequest) getRequest();
		List<String> list = new ArrayList<String>();
		for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();)
			list.add(e.nextElement());
		for (Iterator<String> i = headerMap.keySet().iterator(); i.hasNext();) {
			list.add(i.next());
		}
		return Collections.enumeration(list);
	}

	public String getHeader(String name) {
		Object value;
		if ((value = headerMap.get(name)) != null)
			return value.toString();
		else
			return ((HttpServletRequest) getRequest()).getHeader(name);
	}
}
