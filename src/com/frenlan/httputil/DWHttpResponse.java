package com.frenlan.httputil;

import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.frenlan.util.JsonFormatUtil;

/**
 * 基础类,HTTP请求的响应服务
 * @since 0.4.0
 */
public class DWHttpResponse {
	private String statusLine = null;
	private int statusCode = 0;
	private String body;
	private HashMap<String, String> headers = new HashMap<String, String>();
	private Logger logger = Logger.getLogger(DWHttpResponse.class);

	public JSONObject getBodyAsJson() {
		try {
			return new JSONObject(body);
		} catch (JSONException e) {
			logger.error("getBodyAsJson error ：", e);
		}
		return null;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusLine() {
		return statusLine;
	}

	public void setStatusLine(String statusLine) {
		this.statusLine = statusLine;
	}

	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

	public String getHeader(String name) {
		return headers.get(name);
	}

	public Set<String> getHeaderNames() {
		return headers.keySet();
	}

	/**
	 * 打印响应的消息
	 */
	public void printResponse() {
		logger.debug("----------------------------------------------");
		logger.debug("Http Response statusCode: " + statusCode);
		logger.debug("Http Response statusLine: " + statusLine);
		logger.debug("Http Response Headers:");
		for (String name : getHeaderNames()) {
			logger.debug(name + ": " + getHeader(name));
		}
		logger.debug("Http Response Body:");
		logger.debug(JsonFormatUtil.formatJson(getBody()));
		logger.debug("----------------------------------------------");
	}

}
