package com.frenlan.testcase;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.frenlan.action.ApiAction;
import com.frenlan.basecase.BaseCase;
import com.frenlan.util.ExcelUtil;

@Test(groups = { "ApiTestDemo" }, description = "Api测试")
public class ApiTestDemo extends BaseCase {
	// 预期结果
	private String expeString;
	private ApiAction apiAction;
	private Logger logger = Logger.getLogger(LoginActionTest.class);

	@BeforeClass(alwaysRun = true)
	public void beforeClassTest() {
		try {
			beforeClass();
			apiAction = new ApiAction();
		} catch (Exception e) {
			logger.error("执行ApiTestDemo.beforeClassTest错误：", e);
		}
	}

	@Test
	public void testLogin() {
		boolean flag = false;
		String body = "";
		try {
			String url = ExcelUtil.urlString("apicase", "1.1");
			if (true) {
				ExcelUtil.modifyExcel(ExcelUtil.row, ExcelUtil.column, "failspp");
				flag = true;
			}
		} catch (Exception e) {
			logger.error("执行LoginActionTest.testLogin错误：", e);
		}
		afterTest("testLogin", flag);
		Assert.assertEquals(flag, true, className + ".testLogin failed!");
	}

	@AfterClass(alwaysRun = true)
	public void afterClassTest() {
		try {
			afterClass();
		} catch (Exception e) {
			logger.error("执行ApiTestDemo.afterClassTest错误：", e);
		}

	}
}
