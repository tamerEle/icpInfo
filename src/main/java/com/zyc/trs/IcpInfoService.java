package com.zyc.trs;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.service.spi.Startable;
import org.springframework.stereotype.Service;

@Service
public class IcpInfoService {

	HttpClient httpClient = null;
	Scanner scanner = new Scanner(System.in);
	public IcpInfoService() {
		// TODO Auto-generated constructor stub
	}
	
	public void geticpMemoInfo_showPage(String Url) {
		
	}
	
	public static void main(String[] args) {
		
		IcpInfoService iiService = new IcpInfoService();
		
		/*while(true) {
			System.out.println(iiService.formTest());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		iiService.start("1");
		iiService.startDetailInfo();
		//System.out.println(responseBody);
	}
	public void startDetailInfo() {
		String searchRusult=null;
		String validataCode =null;
		String siteDomain = null;
		
		//String responseBody = this.doPost("http://www.miitbeian.gov.cn/icp/publish/query/icpMemoInfo_showPage.action");
		String validataPicPath = this.downloadPic("http://www.miitbeian.gov.cn/getDetailVerifyCode?" + (new Date().getTime() %100));
		while(!(searchRusult != null && searchRusult.contains("true"))) {
			new picSwing(validataPicPath).run();
			Scanner in=new Scanner(System.in);
			validataCode  = in.nextLine();
			siteDomain  = in.nextLine();
			in.close();
			System.out.println(validataCode + siteDomain);
			break;
		}
		searchRusult =  this.icpMemoInfo_login(validataCode, siteDomain, "80945084");
		System.out.println(searchRusult);
	}
	/**
	 * 通过icpId 获取详细信息
	 * @param icpInfoBean
	 */
	public IcpInfoBean getDetailInfoByIcpId(IcpInfoBean icpInfoBean) {
		String searchRusult=null;
		String validataCode =null;
		String siteDomain = null;
		String validataPicPath = this.downloadPic("http://www.miitbeian.gov.cn/getDetailVerifyCode?" + (new Date().getTime() %100));
		while(!(searchRusult != null && searchRusult.contains("true"))) {
			new picSwing(validataPicPath).run();
			
			Scanner in = this.scanner;
			while(!in.hasNextLine()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			validataCode  = in.nextLine();
			//in.close();
			System.out.println(validataCode);
			break;
		}
		searchRusult =  this.icpMemoInfo_login(validataCode, siteDomain, icpInfoBean.getIcpId());
		MyPattern myPattern = new MyPattern();
		IcpInfoBean icpInfoDetailByHtml = myPattern.findIcpInfoInHtml(searchRusult);
		icpInfoDetailByHtml.setDomain(icpInfoBean.getDomain());
		icpInfoDetailByHtml.setIcpId(icpInfoBean.getIcpId());
		icpInfoDetailByHtml.setRowIndex(icpInfoBean.getRowIndex());
		System.out.println(searchRusult);
		return icpInfoDetailByHtml;
		
	}
	public void start(String siteDomain) {
		String searchRusult=null;
		String validataCode =null;
		//String responseBody = this.doPost("http://www.miitbeian.gov.cn/icp/publish/query/icpMemoInfo_showPage.action");
		String validataPicPath = this.downloadPic("http://www.miitbeian.gov.cn/getVerifyCode?" + (new Date().getTime() %100));
		while(!(searchRusult != null && searchRusult.contains("true"))) {
			new picSwing(validataPicPath).run();
			Scanner in=new Scanner(System.in);
			validataCode  = in.nextLine();
			in.close();
			System.out.println(validataCode + siteDomain);
			searchRusult =  this.validCodeAction(validataCode);
			System.out.println(searchRusult);
		}
		searchRusult =  this.icpMemoInfo_searchExecute(validataCode, siteDomain);
		System.out.println(searchRusult);
	}
	/**
	 * 根据sitedoamin 获取该网站的 icpID
	 * @param siteDomain
	 * @return
	 */
	public String searchIcpIdBySitedomain(String siteDomain) {
		String searchRusult=null;
		String validataCode =null;
		//String responseBody = this.doPost("http://www.miitbeian.gov.cn/icp/publish/query/icpMemoInfo_showPage.action");
		String validataPicPath = this.downloadPic("http://www.miitbeian.gov.cn/getVerifyCode?" + (new Date().getTime() %100));
		while(!(searchRusult != null && searchRusult.contains("true"))) {
			new picSwing(validataPicPath).run();
			Scanner in = this.scanner;
			while(!in.hasNextLine()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			validataCode  = in.nextLine();
			//in.close();
			System.out.println(validataCode + siteDomain);
			searchRusult =  this.validCodeAction(validataCode);
			System.out.println(searchRusult);
		}
		searchRusult =  this.icpMemoInfo_searchExecute(validataCode, siteDomain);
		MyPattern myPattern = new MyPattern();
		String icpId = myPattern.findIdInHtml(searchRusult);
		System.out.println(icpId);
		return icpId;
	}
	/**
	 * 普通页面的提交 返回
	 * @param url
	 * @return
	 */
	public String doPost(String url){  
        if(this.httpClient == null) {
        	httpClient =  HttpClients.createDefault();  
        }
        String result = null;  
        try{  
            HttpGet httpGet = new HttpGet(url);  
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
            //httpPost.setEntity(entity);  
            HttpResponse response = httpClient.execute(httpGet);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,"UTF-8");  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
	/**
	 * 下载验证码链接
	 * @param url
	 * @return
	 */
	public String downloadPic(String url) {
		if(this.httpClient == null) {
        	httpClient =  HttpClients.createDefault();  
        }
        String result = null;  
        try{  
            HttpGet httpGet = new HttpGet(url);  
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
            HttpResponse response = httpClient.execute(httpGet);  
            //httpGet = new HttpGet("http://www.miitbeian.gov.cn/getVerifyCode?84");  
            
            //response = httpClient.execute(httpGet);  
            if(response.getStatusLine().getStatusCode() == 200)  
            {  
                //得到实体  
                HttpEntity entity = response.getEntity();  
                  
                byte[] data = EntityUtils.toByteArray(entity);  
                String validataPicFolder = "E:\\stsworkspace\\icpInfo\\src\\main\\resources\\static\\pic\\";
                String validataPicName = (new Date().getTime() %100)+ ".jpg";
                String validataAbsolutePath = validataPicFolder + validataPicName;
                result = validataAbsolutePath;
                //图片存入磁盘  
                FileOutputStream fos = new FileOutputStream(validataAbsolutePath);  
                fos.write(data);  
                fos.close();  
                  
                System.out.println("图片下载成功!!!!");     
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
	}
	/**
	 * 下载详细信息的验证码链接
	 * @param url
	 * @return
	 */
	public String downloadDetailValidataPic(String url) {
		
		if(this.httpClient == null) {
        	httpClient =  HttpClients.createDefault();  
        }
        String result = null;  
        try{  
            HttpGet httpGet = new HttpGet(url);  
            HttpResponse response = httpClient.execute(httpGet);  
            httpGet = new HttpGet("http://www.miitbeian.gov.cn/getDetailVerifyCod?" + (new Date().getTime() %100));  
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
            //httpGet = new HttpGet("http://www.miitbeian.gov.cn/getVerifyCode?84");
            response = httpClient.execute(httpGet);  
            if(response.getStatusLine().getStatusCode() == 200)  
            {  
                //得到实体  
                HttpEntity entity = response.getEntity();  
                byte[] data = EntityUtils.toByteArray(entity);  
                String validataPicFolder = "E:\\stsworkspace\\icpInfo\\src\\main\\resources\\static\\pic\\";
                String validataPicName = (new Date().getTime() %100)+ ".jpg";
                String validataAbsolutePath = validataPicFolder + validataPicName;
                result = validataAbsolutePath;
                //图片存入磁盘  
                FileOutputStream fos = new FileOutputStream(validataAbsolutePath);  
                fos.write(data);  
                fos.close();  
                  
                System.out.println("图片下载成功!!!!");     
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
	}
	/**
	 * 第一个页面中的搜索 验证码和域名的输入
	 * @param validataCode
	 * @param siteDomain
	 * @return
	 */
	public String icpMemoInfo_searchExecute(String validataCode ,String siteDomain){  
		String url = "http://www.miitbeian.gov.cn/icp/publish/query/icpMemoInfo_searchExecute.action";
        if(this.httpClient == null) {
        	httpClient =  HttpClients.createDefault();  
        }
        String result = null;  
        try{  
            HttpPost httpPost = new HttpPost(url);  
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
            List <NameValuePair> params = new ArrayList<NameValuePair>();  
            //post表单的参数
  /*          siteName:
            	condition:1
            	siteDomain:b.com
            	siteUrl:
            	mainLicense:
            	siteIp:
            	unitName:
            	mainUnitNature:-1
            	certType:-1
            	mainUnitCertNo:
            	verifyCode:945664*/
            params.add(new BasicNameValuePair("siteName", ""));
            params.add(new BasicNameValuePair("siteUrl", ""));
            params.add(new BasicNameValuePair("mainLicense", ""));
            params.add(new BasicNameValuePair("siteIp", ""));
            params.add(new BasicNameValuePair("unitName", ""));
            params.add(new BasicNameValuePair("mainUnitCertNo", ""));
            params.add(new BasicNameValuePair("siteDomain",siteDomain ));
            params.add(new BasicNameValuePair("mainUnitNature", "-1"));
            params.add(new BasicNameValuePair("certType", "-1"));
            params.add(new BasicNameValuePair("verifyCode", validataCode));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"GBK"));  
            //httpPost.setEntity(entity);  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,"UTF-8");  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
	/**
	 * 详细页面信息输入
	 * @param validataCode
	 * @param siteDomain
	 * @return
	 */
	public String icpMemoInfo_login(String validataCode ,String siteDomain, String id){  
		String url = "http://www.miitbeian.gov.cn/icp/publish/query/icpMemoInfo_login.action";
        if(this.httpClient == null) {
        	httpClient =  HttpClients.createDefault();  
        }
        if(id == null) {
        	id = "";
        }
        String result = null;  
        try{  
            HttpPost httpPost = new HttpPost(url);  
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
            List <NameValuePair> params = new ArrayList<NameValuePair>();  
            params.add(new BasicNameValuePair("siteName", ""));
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("siteUrl", ""));
            params.add(new BasicNameValuePair("mainLicense", ""));
            params.add(new BasicNameValuePair("siteIp", ""));
            params.add(new BasicNameValuePair("unitName", ""));
            params.add(new BasicNameValuePair("mainUnitCertNo", ""));
            params.add(new BasicNameValuePair("siteDomain",siteDomain ));
            params.add(new BasicNameValuePair("mainUnitNature", "-1"));
            params.add(new BasicNameValuePair("certType", "-1"));
            params.add(new BasicNameValuePair("bindFlag", "0"));
            params.add(new BasicNameValuePair("verifyCode", validataCode));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"GBK"));  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,"UTF-8");  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }
	public String validCodeAction(String validataCode){  
		String url = "http://www.miitbeian.gov.cn/common/validate/validCode.action";
        if(this.httpClient == null) {
        	httpClient =  HttpClients.createDefault();  
        }
        String result = null;  
        try{  
            HttpPost httpPost = new HttpPost(url);  
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
            List <NameValuePair> params = new ArrayList<NameValuePair>();  
            params.add(new BasicNameValuePair("validateValue", new String(validataCode.getBytes("UTF-8") , "GBK")));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"GBK"));  
            //httpPost.setEntity(entity);  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,"UTF-8");  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
	/**
	 * 表单选件测试
	 * @param validataCode
	 * @param siteDomain
	 * @param id
	 * @return
	 */
	public String formTest(){  
		String url = "http://172.20.12.70:8081/infogate/CustomInfoViewDataAdapt";
        if(this.httpClient == null) {
        	httpClient =  HttpClients.createDefault();  
        }
        String result = null;  
        try{  
            HttpPost httpPost = new HttpPost(url);  
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
            List <NameValuePair> params = new ArrayList<NameValuePair>();  
            params.add(new BasicNameValuePair("SiteId", "4"));
            params.add(new BasicNameValuePair("ChannelId", "65"));
            params.add(new BasicNameValuePair("InfoViewId", "1"));
            params.add(new BasicNameValuePair("DocumentId", "0"));
            params.add(new BasicNameValuePair("OnlyCached", "false"));
            params.add(new BasicNameValuePair("NeedInit", "false"));
            
            params.add(new BasicNameValuePair("needverifycode", "0"));  
            params.add(new BasicNameValuePair("encoding","UTF-8" ));
            params.add(new BasicNameValuePair("verifycode", "0"));
            params.add(new BasicNameValuePair("resourcebase", "../../images/infoview/"));
            
            params.add(new BasicNameValuePair("InfoviewTitle", "ss"));
            params.add(new BasicNameValuePair("CachedInfoviewId", ""));
            params.add(new BasicNameValuePair("JustCached", ""));
            params.add(new BasicNameValuePair("name", "1"));
            params.add(new BasicNameValuePair("phone", "13820202020"));
            params.add(new BasicNameValuePair("company", "1"));
            params.add(new BasicNameValuePair("workSpace", "1"));
            params.add(new BasicNameValuePair("infogatePath", "http://172.20.12.70:8081/infogate"));
            params.add(new BasicNameValuePair("SelectFields", "name,phone,company,workSpace"));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"GBK"));  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,"UTF-8");  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }
}
