package com.frenlan.action;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import com.frenlan.httputil.DWHttpClient;
import com.frenlan.httputil.DWHttpResponse;
import com.frenlan.util.FileUtil;
import com.frenlan.util.PropUtil;
import com.frenlan.util.Utils;

public class LoginAction {
	private DWHttpClient request = new DWHttpClient();
	private DWHttpResponse response;
	private String url;
	private String template;
	private static PropUtil propUtil = new PropUtil("config/httptest.properties");
	private static Logger logger = Logger.getLogger(LoginAction.class);

	/**
	 * 初始化数据，构造方法
	 */
	public LoginAction() {
		String secret = propUtil.get("secret");
		StringBuffer sb = new StringBuffer();
		String baseUrl = propUtil.get("baseUrl") + "/mktvportal/mktv/login";
		String queryString1 = "timestamp=" + System.currentTimeMillis();
		String queryString2 = "&signature="
				+ Utils.md5((queryString1 + secret).getBytes());
		url = sb.append(baseUrl).append("?").append(queryString1)
				.append(queryString2).toString();
		FileUtil file = new FileUtil();
		logger.debug("url is :" + url);
		template = file.getContent("login.template");
		logger.debug("template is :");
		logger.debug(template);

	}

	/**
	 *  登录api
	 * @param email 邮箱
	 * @param password 密码
	 * @return
	 */
	public JSONObject login(String email, String password) {
		//body等于template
		String body = String.format(template, new Object[] { email, password });
		response = request.sendPostRequest(url, body);
		response.printResponse();
		if (response.getStatusCode() == 200) {
			return response.getBodyAsJson();
		}
		return null;

	}

	public static void main(String[] args) {
		JSONObject jSONObject = new LoginAction().login("kernel001@test.com",
				"111111");
		if (jSONObject != null) {
			logger.debug(jSONObject.optJSONObject("result").optInt("users"));
		}
	}
}
