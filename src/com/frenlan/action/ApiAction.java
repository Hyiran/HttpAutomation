package com.frenlan.action;

import org.apache.log4j.Logger;

import com.frenlan.httputil.DWHttpClient;
import com.frenlan.httputil.DWHttpResponse;
import com.frenlan.util.ExcelUtil;

public class ApiAction {
	private DWHttpClient request = new DWHttpClient();
	private DWHttpResponse response;
	private static Logger logger = Logger.getLogger(ApiAction.class);

	/**
	 * apiDemo
	 * @param url
	 * @param body
	 * @return
	 */
	public boolean apiActionTest(String url, String body) {
		if (!ExcelUtil.flag) {
			request.sendGetRequest(url);
		} else {
			response = request.sendPostRequest(url, body);
		}
		response.printResponse();
		if (response.getStatusCode() == 200) {
			return true;
		}
		return false;

	}
}
