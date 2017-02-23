package com.frenlan.testcase;

import java.util.Map;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.frenlan.action.LoginAction;
import com.frenlan.basecase.BaseCase;
import com.frenlan.util.Base64;
import com.frenlan.util.ExcelUtil;

@Test(groups = { "LoginActionTest" }, description = "登录测试")
public class LoginActionTest extends BaseCase {
	private LoginAction LoginAction;
	private Logger logger = Logger.getLogger(LoginActionTest.class);

	@BeforeClass(alwaysRun = true)
	public void beforeClassTest() {
		try {
			beforeClass();
			LoginAction = new LoginAction();
		} catch (Exception e) {
			logger.error("执行LoginActionTest.beforeClassTest错误：", e);
		}
	}

	@Test(enabled = true, alwaysRun = true, description = "登录测试")
	public void testLogin() {
		boolean flag = false;

		try {
			beforeTest("testLogin");
			Map<String, String> userMap = ExcelUtil.importDataTable(
					"testdata.xls", "LoginActionTest", "testLogin");
			String email = userMap.get("email");
			String password = Base64.encode(userMap.get("password"), "utf-8");
			JSONObject JSONObject = LoginAction.login(email, password);
			String actualEmail = JSONObject.getJSONObject("result")
					.optJSONObject("user").optString("email");
			String token = JSONObject.getJSONObject("result")
					.optJSONObject("user").optString("token");
			String errorcode = JSONObject.getJSONObject("result").optString(
					"errorcode");
			if (email.equals(actualEmail) && !"".equals(token)
					&& "ok".equals(errorcode)) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("执行LoginActionTest.testLogin错误：", e);
		}
		afterTest("testLogin", flag);
		Assert.assertEquals(flag, true, className + ".testLogin failed!");
	}

	@Test(dependsOnMethods = { "testLogin" }, enabled = true, alwaysRun = true, description = "登录密码错误测试")
	public void testLoginWithPwdError() {
		boolean flag = false;

		try {
			beforeTest("testLoginWithPwdError");
			Map<String, String> userMap = ExcelUtil.importDataTable(
					"testdata.xls", "LoginActionTest", "testLoginWithPwdError");
			String email = userMap.get("email");
			String password = Base64.encode(userMap.get("password"), "utf-8");
			JSONObject JSONObject = LoginAction.login(email, password);
			String errorcode = JSONObject.getJSONObject("result").optString(
					"errorcode");
			if ("2201".equals(errorcode)) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("执行LoginActionTest.testLoginWithPwdError错误：", e);
		}
		afterTest("testLoginWithPwdError", flag);
		Assert.assertEquals(flag, true, className
				+ ".testLoginWithPwdError failed!");
	}

	@AfterClass(alwaysRun = true)
	public void afterClassTest() {
		try {
			afterClass();
		} catch (Exception e) {
			logger.error("执行LoginActionTest.afterClassTest错误：", e);
		}

	}
}
