package com.frenlan.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public final class WebUtils {
	//UTF-8编码
	public static final String ENCODING_UTF_8 = "UTF-8";
	public static final String CONTENT_TYPE = "text/html;charset=UTF-8";
	public static final String LF = "\n";
	public static final String NULL_VALUE = null;
	public static final String EMPTY_VALUE = "";
	private static Logger log = Logger.getLogger(WebUtils.class);

	/**
	 * utf-8-编码
	 * @param url
	 * @return
	 */
	public static String urlEncode(String url) {
		if (null == url)
			return EMPTY_VALUE;
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (Exception e) {
			log.error("urlEncode error, ", e);
		}
		return url;
	}

	/**
	 * utf-8-解码
	 * @param url
	 * @return
	 */
	public static String urlDecode(String url) {
		if (null == url)
			return "";
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("urlDecode error, ", e);
		}
		return url; 
	}

	/**
	 * Base64 传输8Bit字节代码的编码方式
	 * @param str
	 * @return
	 */
	public static String base64Decode(String str) {
		if (null == str) {
			return "";
		}
		try {
			return new String(Base64.decodeBase64(str.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Base64 传输8Bit字节代码的解码方式
	 * @param str
	 * @return
	 */
	public static String base64Encode(String str) {
		if (null == str) {
			return "";
		}
		try {
			return new String(Base64.encodeBase64(str.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 拼接url后连接参数
	 * @param params
	 * @param name
	 * @param value
	 * @return
	 */
	public static StringBuilder addUrlParam(StringBuilder params, String name,String value) {
		//params.charAt(params.length() - 1)最后一个字符
		if ((params.length() > 0) && (params.charAt(params.length() - 1) != '&')) {
			params.append('&');
		}
		params.append(urlEncode(name));
		params.append('=');
		params.append(urlEncode(value));
		return params;
	}

	public static void main(String[] args) {
		String str = "cA==";
		str = base64Encode("111111");
		System.out.println(new StringBuilder().append("encode>>>>>>>>>")
				.append(str).toString());
		str = base64Decode(str);
		System.out.println(new StringBuilder().append("decode>>>>>>>>>")
				.append(str).toString());
	}
}