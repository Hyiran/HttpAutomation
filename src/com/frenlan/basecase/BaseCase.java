package com.frenlan.basecase;

import org.apache.log4j.Logger;
import com.frenlan.util.DateTimeUtil;

public class BaseCase {
	protected final String className = this.getClass().getName();
	protected static String capturePath;
	private long beforeSuiteStarts = 0;
	private long afterSuiteStops = 0;
	private long beforeClassStarts = 0;
	private long afterClassStops = 0;
	private long beforeTestStarts = 0;
	private long afterTestStops = 0;
	private Logger logger = Logger.getLogger(BaseCase.class);

	public void beforeSuite() {
		String begins = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss:SSS");
		beforeSuiteStarts = System.currentTimeMillis();
		logger.info("======" + begins + "：测试集开始======");
	}

	public void afterSuite() {
		String ends = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss:SSS");
		afterSuiteStops = System.currentTimeMillis();
		logger.info("======" + ends + "：测试集结束======");
		logger.info("======本次测试集运行消耗时间 "
				+ (afterSuiteStops - beforeSuiteStarts) / 1000 + " 秒！======");
	}

	public void beforeClass() {
		String begins = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss:SSS");
		beforeClassStarts = System.currentTimeMillis();
		logger.info("======" + begins + "：测试【" + className + "】开始======");
	}

	public void afterClass() {
		String ends = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss:SSS");
		afterClassStops = System.currentTimeMillis();
		logger.info("======" + ends + "：测试【" + className + "】结束======");
		logger.info("======本次测试运行消耗时间 " + (afterClassStops - beforeClassStarts)
				/ 1000 + " 秒！======");
	}

	public void beforeTest(String methodName) {
		String begins = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss:SSS");
		beforeTestStarts = System.currentTimeMillis();
		logger.info("======" + begins + "：测试用例【" + className + "." + methodName
				+ "】开始======");
	}

	public void afterTest(String methodName, boolean isSucceed) {
		String ends = DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss:SSS");
		afterTestStops = System.currentTimeMillis();
		if (isSucceed) {
			logger.info("测试用例【" + className + "." + methodName + "】 运行通过！");
		} else {
			logger.error("测试用例 【" + className + "." + methodName + "】 运行失败！");
		}
		logger.info("======" + ends + "：测试用例【" + className + "." + methodName
				+ "】结束======");
		logger.info("======本次测试用例运行消耗时间 " + (afterTestStops - beforeTestStarts)
				/ 1000 + " 秒！======");
	}

	public static void main(String[] args) {
		BaseCase baseCase = new BaseCase();
		baseCase.afterClass();
		System.out
				.println(DateTimeUtil.formatedTime("yyyy-MM-dd HH:mm:ss:SSS"));
	}
}