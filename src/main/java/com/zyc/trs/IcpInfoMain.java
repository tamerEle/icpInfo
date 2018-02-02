package com.zyc.trs;

import java.io.IOException;

import org.apache.poi.hssf.extractor.ExcelExtractor;

public class IcpInfoMain {

	public IcpInfoMain() {
	}
	//public static final int startRowIndex = 100;
	public static void main(String[] args) {
		java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
		java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "ERROR");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "ERROR");
		
		int startRowIndex = 102;
		IcpInfoService iiService = new IcpInfoService();
		try {
			ExportToExcel exportToExcel = new ExportToExcel("C:\\Users\\Administrator\\Desktop\\hospital.xls");
			while(true) {
				IcpInfoBean icpInfoBean = new IcpInfoBean();
				icpInfoBean.setRowIndex(startRowIndex);
				String domain = exportToExcel.getCellValue(startRowIndex++, 3);
				icpInfoBean.setDomain(domain);
				String icpId = iiService.searchIcpIdBySitedomain(icpInfoBean.getDomain());
				icpInfoBean.setIcpId(icpId);	
				icpInfoBean = iiService.getDetailInfoByIcpId(icpInfoBean);
				exportToExcel.setIcpInfoInText(icpInfoBean);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
