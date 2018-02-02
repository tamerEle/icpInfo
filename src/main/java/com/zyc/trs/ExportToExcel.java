package com.zyc.trs;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportToExcel {
	String xlsPath ;
	public ExportToExcel(String xlsPath) throws IOException {
		/*FileInputStream fileInputStream=new FileInputStream(xlsPath);
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook(fileInputStream); 
        this.sheet = wb.getSheetAt(0);*/
        this.xlsPath = xlsPath;
    }
	public static void main(String[] args) {
		
		while(true) {
			try {
				ExportToExcel exportToExcel =  new ExportToExcel("C:\\Users\\Administrator\\Desktop\\hospital.xls");
				exportToExcel.editInExcel("C:\\Users\\Administrator\\Desktop\\hospital.xls");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public String  getCellValue(int rowIndex, int colIndex) throws IOException {
		FileInputStream fileInputStream=new FileInputStream(this.xlsPath);
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook(fileInputStream); 
        HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(rowIndex);
		HSSFCell cell = row.getCell(colIndex);
		fileInputStream.close();
		return cell.getStringCellValue();
	}
	public void setIcpInfoInExcel(IcpInfoBean icpInfoBean) throws IOException, InterruptedException {
		StringBuilder sBuilder = new StringBuilder();
		String icpString = icpInfoBean.getZbdw() + "\t" + icpInfoBean.getZbdwxz() + "\t" + icpInfoBean.getBAH() + "\t" + icpInfoBean.getWzmc()
		+ "\t" + icpInfoBean.getWzsydz() + "\t" + icpInfoBean.getWzfzrxm()+ "\t" + icpInfoBean.getPassTime();
		sBuilder.append(icpString) ;
        File outTextFile = new File("D:/outTextFile/out.txt");
        IOTest ioTest = new IOTest();
	}
	public void setIcpInfoInText(IcpInfoBean icpInfoBean) throws IOException, InterruptedException {
		StringBuilder sBuilder = new StringBuilder();
		String icpString = icpInfoBean.getZbdw() + "\t" + icpInfoBean.getZbdwxz() + "\t" 
		+ icpInfoBean.getBAH() + "\t" + icpInfoBean.getWzmc()
		+ "\t" + icpInfoBean.getWzsydz() + "\t" + icpInfoBean.getWzfzrxm()+ "\t" + icpInfoBean.getPassTime();
		System.out.println(icpInfoBean.getWzsydz());
		sBuilder.append(icpString) ;
		sBuilder.append("\r\n") ;
        File outTextFile = new File("D:/outTextFile/out.txt");
        IOTest ioTest = new IOTest();
        ioTest.appendInFile(outTextFile, sBuilder.toString());
	}
	private void setCellValue(int colIndex, String value, HSSFRow row) {
		if(value == null) value = "";
		row.getCell(colIndex).setCellValue(value);
	}
	public void editInExcel(String xlsPath) throws IOException {
		FileInputStream fileInputStream=new FileInputStream(xlsPath);
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook(fileInputStream); 
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row  = sheet.getRow(1);
        HSSFCell cell = row.getCell(1);
        System.out.println(cell.getStringCellValue());
	}
	public void myProcess(IcpInfoBean icpInfoBean) {
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        
      //  new HSSFWorkbook
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("学生表一");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        HSSFCell cell = row.createCell(0);  
        cell.setCellValue("学号");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("姓名");  
        cell.setCellStyle(style);  
        cell = row.createCell( 2);  
        cell.setCellValue("年龄");  
        cell.setCellStyle(style);  
        cell = row.createCell( 3);  
        cell.setCellValue("生日");  
        cell.setCellStyle(style);  
  
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
        //List list = CreateSimpleExcelToDisk.getStudent();  
  
        for (int i = 0; i < 1; i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            // 第四步，创建单元格，并设置值  
            row.createCell( 0).setCellValue(icpInfoBean.getIcpId());  
            row.createCell( 1).setCellValue(icpInfoBean.getPassTime());  
            row.createCell( 2).setCellValue(icpInfoBean.getWzfzrxm());  
            /*cell = row.createCell((short) 3);  
            cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(stu  
                    .getBirth())); */ 
        }  
        // 第六步，将文件存到指定位置  
        try  
        {  
            FileOutputStream fout = new FileOutputStream("E:/students.xls");  
            wb.write(fout);  
            fout.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
	}
}
