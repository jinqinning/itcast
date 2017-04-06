package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestPOI {
	@Test
	public void testwrite() {
		/**
		 *1 创建工作簿
		  2 创建工作表
		  3 创建行
		  4 创建单元格 
		 * 
		 */
		try {
			String fileName="C:\\Users\\Administrator\\Desktop\\用户列表.xlsx";
			if(fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){//判断是否是excel文档
				boolean is03Excel=fileName.matches("^.+\\.(?i)(xls)$");
				FileInputStream inputStream=new FileInputStream(fileName);
				Workbook workbook=is03Excel?new XSSFWorkbook(inputStream):new HSSFWorkbook(inputStream);
				Sheet sheet=workbook.getSheetAt(0);
				Row row=sheet.getRow(2);
				Cell cell=row.getCell(2);
//				System.out.println(cell.getStringCellValue());
				cell.setCellValue("'''");
				workbook.close();
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
