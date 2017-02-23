package com.frenlan.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;

public class ExcelUtil {
	private static Logger logger = Logger.getLogger(ExcelUtil.class);
	public static boolean flag = false;
	public static int row;
	public static int column;
	public final static File file = new File("resource/商城接口测试用例.xls");

	/**
	 * 返回测试用例所在行的内容
	 * 
	 * @param excelName
	 *            excel文件名
	 * @param sheetName
	 *            sheet页
	 * @param findContent
	 *            查找内容
	 * @return
	 */
	public static Map<String, String> importDataTable(String excelName,
			String sheetName, String findContent) {

		@SuppressWarnings("unchecked")
		Map<String, String> mapdata = new LinkedMap();
		mapdata = getSpecifySheet(sheetName, findContent);

		logger.info("导出excel数据:" + mapdata);
		return mapdata;
	}

	/**
	 * 根据测试用例名称来读取该用例对应的行数据 {password=222222,
	 * method=testLoginWithPwdError,email=base004@test.com}
	 * 
	 * @param excelpath
	 *            excel路径
	 * @param sheetName
	 *            sheet页
	 * @param caseName
	 *            测试用例
	 * @return
	 */
	public static Map<String, String> getSpecifySheet(String sheetName,
			String caseName) {

		List<String> header = null;
		Map<String, String> rowMap = new HashMap<String, String>();

		boolean findRow = false;
		int rowNumber = 0;

		try {
			// 读取excel文件
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			// 行数
			int rows = sheet.getRows();
			System.out.println("该sheet页共有" + rows + "条测试用例");
			// 列数
			int columns = sheet.getColumns();
			header = new ArrayList<String>();
			// 读取excel第一行，及表格标题
			for (int columnindex = 0; columnindex < columns; columnindex++) {
				String headerElement = sheet.getCell(columnindex, 0)
						.getContents().trim();
				header.add(headerElement);
			}
			logger.debug("当前excel表头为 :" + header);
			// 遍历method所在列的单元格，查找指定的测试用例
			for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
				String cellContent = sheet.getCell(0, rowIndex).getContents()
						.toLowerCase().trim();
				System.out.println("测试用例" + rowIndex + "---" + cellContent);
				logger.info("正在运行测试用例名称为" + cellContent);

				// 是否找到指定的测试用例
				if (cellContent.equalsIgnoreCase(caseName)) {
					logger.debug("当前测试用例名称为:" + cellContent);
					findRow = true;
					row = rowIndex;
					System.err.println(row);
					rowNumber = rowIndex;
					break; // 跳出for循环
				} else {
					findRow = false;
				}
			}

			if (findRow) {
				for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
					String findContent = sheet.getCell(columnIndex, rowNumber)
							.getContents().trim();
					String mapHeader = header.get(columnIndex);
					System.out.println("mapheader---" + mapHeader);
					System.out.println("findContent---" + findContent);
					// excel的表头和列单元格<key,value>
					rowMap.put(mapHeader, findContent);
				}
			}
			logger.debug("当前行的数据 :" + rowMap);

		} catch (BiffException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		return rowMap;
	}

	/**
	 * 读取excel中有效的url连接
	 * 
	 * @param excelPath
	 * @param sheetName
	 * @param caseName
	 * @return
	 */
	public static String urlString(String sheetName, String caseName) {
		StringBuilder urlBuilder = new StringBuilder();
		Map<String, String> urldata = getSpecifySheet(sheetName, caseName);

		column = urldata.size();
		System.err.println(column);
		// 根据实际的测试用例来
		String iP = urldata.get("IP");
		String url = urldata.get("Url");
		String account = urldata.get("account");
		String headersType = urldata.get("HeadersType");
		String headersValue = urldata.get("HeadersValue");
		String rtn = urldata.get("Return");
		String assKey = urldata.get("AssKey");
		String actResult = urldata.get("ActResult");

		if ("POST".equals(urldata.get("Method")))
			flag = true;
		System.err.println(flag);

		// 如果不执行测试用例，则跳过
		if ("Y".equals(urldata.get("Run"))) {
			urlBuilder.append(iP).append(url).append("?").append(account)
					.append("&").append(headersType).append("&")
					.append(headersValue).append("&").append(rtn).append("&")
					.append(assKey).append("&").append(actResult);
			System.out.println(urlBuilder.toString());
			return urlBuilder.toString();
		} else {
			return null;
		}
	}

	/**
	 * 修改指定单元格的数据内容
	 * 
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void modifyExcel(int row, int column,String content) {
		// 创建用于写入内容的Excel文件的引用
		WritableWorkbook wb = null;
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			// 获取Excel文件
			wb = Workbook.createWorkbook(file, workbook);
			if (wb != null) {
				// 通过Excel文件获取第一个工作簿sheet
				WritableSheet sheets = wb.getSheet("apicase");
				WritableCell cell = sheets.getWritableCell(column, row);// 获取修改单元格的单元格
				jxl.format.CellFormat cf = cell.getCellFormat();// 获取待修改单元格的格式
				// 构建Excel表的表头
				Label label1 = new Label(column, row, content);
				// 将修改后的单元格的格式设定成跟原来一样
				label1.setCellFormat(cf);
				sheets.addCell(label1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				wb.write();
				wb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		 * ExcelUtil.modifycellData(
		 * "D:\\workspace\\HttpAutomation\\resource\\商城接口测试用例.xls", "apicase",
		 * "1.1");
		 */

		modifyExcel(ExcelUtil.row, ExcelUtil.column,"fail21");
	}
}
