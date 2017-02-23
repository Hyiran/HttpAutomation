package com.frenlan.testcase;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.frenlan.action.LoginAction;
import com.frenlan.action.RegisterAction;
import com.frenlan.basecase.BaseCase;
import com.frenlan.util.DateTimeUtil;
import com.frenlan.util.WebUtils;

@Test(groups = { "RegisterActionTest" }, description = "注册测试")
public class RegisterActionTest extends BaseCase {
	private RegisterAction RegisterAction;
	private LoginAction LoginAction;
	private String uid;
	private String email;
	private String password;
	private String authtime;
	private Logger logger = Logger.getLogger(RegisterActionTest.class);

	@BeforeClass(alwaysRun = true)
	public void beforeClassTest() {
		try {
			beforeClass();
			RegisterAction = new RegisterAction();
			LoginAction = new LoginAction();
		} catch (Exception e) {
			logger.error("执行RegisterActionTest.beforeClassTest错误：", e);
		}

	}

	@Test(enabled = true, alwaysRun = true, description = "注册测试")
	public void testRegister() {
		boolean flag = false;
		try {
			beforeTest("testRegister");
			uid = DateTimeUtil.getDateTime();
			email = uid + "@test.com";
			password = WebUtils.base64Encode("111111");
			authtime = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss");

			JSONObject JSONObject = RegisterAction.register(email, password,
					uid, authtime);
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
			logger.error("执行RegisterActionTest.testRegister错误：", e);
		}
		afterTest("testRegister", flag);
		Assert.assertEquals(flag, true, className + ".testRegister failed!");
	}

	@Test(dependsOnMethods = { "testRegister" }, enabled = true, alwaysRun = true, description = "已注册用户登录测试")
	public void testRegisterUserLogin() {
		boolean flag = false;
		try {
			beforeTest("testRegisterUserLogin");
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
			logger.error("执行RegisterActionTest.testRegisterUserLogin错误：", e);
		}
		afterTest("testRegisterUserLogin", flag);
		Assert.assertEquals(flag, true, className
				+ ".testRegisterUserLogin failed!");
	}

	@Test(dependsOnMethods = { "testRegisterUserLogin" }, enabled = true, alwaysRun = true, description = "已注册用户重复注册")
	public void testRegisterWithExitsUser() {
		boolean flag = false;
		try {
			beforeTest("testRegisterWithExitsUser");
			authtime = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss");
			JSONObject JSONObject = RegisterAction.register(email, password,
					uid, authtime);
			String errorcode = JSONObject.getJSONObject("result").optString(
					"errorcode");
			if ("2004".equals(errorcode)) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("执行RegisterActionTest.testRegisterWithExitsUser错误：", e);
		}
		afterTest("testRegisterWithExitsUser", flag);
		Assert.assertEquals(flag, true, className
				+ ".testRegisterWithExitsUser failed!");
	}

	@AfterClass(alwaysRun = true)
	public void afterClassTest() {
		try {
			afterClass();
		} catch (Exception e) {
			logger.error("执行RegisterActionTest.afterClassTest错误：", e);
		}

	}
}
