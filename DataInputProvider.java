package lib;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataInputProvider{
	public static Object[][] getSheet(String dataSheetName) {
		Object[][] data = null ;

		try {
			String text = "./data/Data_MasterSheet.xlsx";
			System.out.println(text);
			FileInputStream fis = new FileInputStream("./data/Data_MasterSheet.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);

			// get the number of rows
			int rowCount = sheet.getLastRowNum();

			// get the number of columns
			int columnCount = sheet.getRow(0).getLastCellNum();
			data = new String[rowCount][columnCount];

			int dataRowCount = 0;
			// loop through the rows
			for(int i=1; i <rowCount+1; i++){
				try {
					XSSFRow row = sheet.getRow(i);

					for(int j=0; j <columnCount; j++){ // loop through the columns
						try {
							String cellValue = getCellValue(row.getCell(j));
							data[i-1][j]  = cellValue; // add to the data array

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			fis.close();
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	private static String getCellValue(XSSFCell cell)
	{
		String cellValue = "";
		try{
			if(cell.getCellTypeEnum() == CellType.STRING) {
				cellValue = cell.getStringCellValue();
			}
			else if(cell.getCellTypeEnum() == CellType.NUMERIC)
			{
				cellValue = cell.getRawValue();
			}
		}catch(NullPointerException e){

		}

		return cellValue;
	}

	private static boolean isRowEmpty(String[] row)
	{
		int length = row.length;

		for(int i=0; i<length; i++)	{
			if(row[i].trim() != "") {
				return false;
			}
		}

		return true;
	}
}