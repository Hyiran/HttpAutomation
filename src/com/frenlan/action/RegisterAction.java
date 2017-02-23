package com.frenlan.action;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import com.frenlan.httputil.DWHttpClient;
import com.frenlan.httputil.DWHttpResponse;
import com.frenlan.util.DateTimeUtil;
import com.frenlan.util.FileUtil;
import com.frenlan.util.PropUtil;
import com.frenlan.util.Utils;

/**
 * 注册操作类
 * 
 * @author eson
 * @author Administrator
 */
public class RegisterAction {
	private DWHttpClient request = new DWHttpClient();
	private DWHttpResponse response;
	private String url;
	private String template;
	private static PropUtil PropUtil = new PropUtil(
			"config/httptest.properties");
	private static Logger logger = Logger.getLogger(RegisterAction.class);

	/**
	 * 初始化数据，构造函数
	 */
	public RegisterAction() {
		String secret = PropUtil.get("secret");
		StringBuffer sb = new StringBuffer();
		String baseUrl = PropUtil.get("baseUrl") + "/mktvportal/mktv/register";
		String queryString1 = "timestamp=" + System.currentTimeMillis();
		String queryString2 = "&signature="
				+ Utils.md5((queryString1 + secret).getBytes());
		url = sb.append(baseUrl).append("?").append(queryString1)
				.append(queryString2).toString();
		FileUtil file = new FileUtil();
		logger.debug("url is :" + url);
		template = file.getContent("register.template");
		logger.debug("template is :");
		logger.debug(template);

	}

	/**
	 * 注册api
	 * @param email
	 * @param password
	 * @param uid
	 * @param authtime
	 * @return
	 */
	public JSONObject register(String email, String password, String uid,
			String authtime) {
		String body = String.format(template, new Object[] { email, password,
				uid, authtime });
		response = request.sendPostRequest(url, body);
		response.printResponse();
		if (response.getStatusCode() == 200) {
			return response.getBodyAsJson();
		}
		return null;

	}

	public static void main(String[] args) {
		String uid = DateTimeUtil.getDateTime();
		String email = uid + "@test.com";
		String password = "111111";
		String authtime = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss");

		JSONObject JSONObject = new RegisterAction().register(email, password,
				uid, authtime);
		if (JSONObject != null) {
			logger.debug(JSONObject.optJSONObject("result").optInt("users"));
		}

		JSONObject JSONObject1 = new RegisterAction().register(email, password,
				uid, authtime);
		if (JSONObject != null) {
			logger.debug(JSONObject1.optJSONObject("result").optInt("users"));
		}
	}
}
