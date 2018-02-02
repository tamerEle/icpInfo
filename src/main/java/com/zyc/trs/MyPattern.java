package com.zyc.trs;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPattern {

	public MyPattern() {
		// TODO Auto-generated constructor stub
	}
	
	public String findIdInHtml(String html) {
		System.out.println(html);
		Pattern pattern = null;
		pattern = Pattern.compile("doDetail\\('([0-9]+)'\\)");
		Matcher matcher = pattern.matcher(html);
		//替换第一个符合正则的数据
		if(matcher.find()) {
			System.out.println(matcher.group());
			String matcherString = matcher.group(1);
			return matcherString;
			/*System.out.println(matcherString);
			System.out.println(matcher.group(0) + " = "+ matcherString);
			Pattern pattern2 = Pattern.compile("[0-9]+");
			Matcher matcher2 = pattern2.matcher(matcherString);
			if(matcher2.find()) {
				return matcher2.group(0);
			}*/
		}
		return null;
	}
	
	public IcpInfoBean findIcpInfoInHtml(String html) {
		Pattern pattern = null;
		int count = 0;
		pattern = Pattern.compile("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">[\\s\\S]*?</table>");
		//pattern = Pattern.compile("<html>");
		Matcher matcher = pattern.matcher(html);
		
		//替换第一个符合正则的数据
		//System.out.println(matcher.matches());
		while(matcher.find()) {
			//System.out.println(matcher.groupCount());
			count++;
			String matcherString = matcher.group();
			//System.out.println(matcherString);
			
		}
		/**
		 * <tbody><tr>\n" + 
			"						<td align=\"left\" class=\"bx\" width=\"20%\">备案/许可证号：</td>\n" + 
			"						<td align=\"left\" class=\"by1\" width=\"30%\">京ICP证030173号&nbsp;</td>\n" + 
			"						<td align=\"left\" class=\"bx\" width=\"20%\">审核通过时间：</td>\n" + 
			"						<td align=\"left\" class=\"by2\" width=\"30%\">2017-12-22&nbsp;</td>\n" + 
			"					</tr>\n" + 
			"					<tr>\n" + 
			"						<td width=\"18%\" align=\"left\" class=\"bx\">主办单位名称：</td>\n" + 
			"						<td width=\"29%\" align=\"left\" class=\"by1\">北京百度网讯科技有限公司&nbsp;</td>\n" + 
			"						<td width=\"16%\" align=\"left\" class=\"bx\">主办单位性质：</td>\n" + 
			"						<td align=\"left\" class=\"by2\">企业&nbsp;</td>\n" + 
			"					</tr>\n" + 
		 */
		String BAH = patternInfo("<td align=\"left\" class=\"bx\" width=\"20%\">备案/许可证号：</td>"
				+"[\\s\\S]*?<td align=\"left\" class=\"by1\" width=\"30%\">([\\s\\S]*?)&nbsp;</td>" ,html);
		String passTime = patternInfo("<td align=\"left\" class=\"bx\" width=\"20%\">审核通过时间：</td>"
				+"[\\s\\S]*?<td align=\"left\" class=\"by2\" width=\"30%\">([\\s\\S]*?)&nbsp;</td>" ,html);
		
		String zbdw = patternInfo("<td width=\"18%\" align=\"left\" class=\"bx\">主办单位名称：</td>"
				+"[\\s\\S]*?<td width=\"29%\" align=\"left\" class=\"by1\">([\\s\\S]*?)&nbsp;</td>" ,html);
		String zbdwxz = patternInfo("<td width=\"16%\" align=\"left\" class=\"bx\">主办单位性质：</td>"
				+"[\\s\\S]*?<td align=\"left\" class=\"by2\">([\\s\\S]*?)&nbsp;</td>" ,html);
		/**
		 * <tbody><tr>\n" + 
			"						<td align=\"left\" class=\"bx\" width=\"20%\">网站名称：</td>\n" + 
			"						<td align=\"left\" class=\"by1\" width=\"30%\">百度&nbsp;</td>\n" + 
			"						<td align=\"left\" class=\"bx\" width=\"20%\">网站首页网址：</td>\n" + 
			"						<td align=\"left\" class=\"by2\" width=\"30%\"><div> 
			<a href=\"http://www.baidu.com\" target=\"_blank\">www.baidu.com</a></div>&nbsp;</td>\n" + 
			"					</tr>\n" + 
			"					<tr>\n" + 
			"						<td align=\"left\" class=\"bx\">网站负责人姓名：</td>\n" + 
			"						<td align=\"left\" class=\"by1\">苏静&nbsp;</td>\n" + 
			"						<td align=\"left\" class=\"bx\">网站域名：</td>\n" + 
			"						<td align=\"left\" class=\"by1\">\n" + 
			"							<span class=\"STYLE2\">\n" + 
			"				                     \n" + 
			"				                        <div>baidu.com</div> \n" + 
			"				                    &nbsp;\n" + 
			"				             </span>\n" + 
			"						</td>\n" + 
			"					</tr>\n" + 
			"<a href=\"http://www.adm999.com\" target=\"_blank\">www.adm999.com</a></div><div> "
	+ "<a href=\"http://www.adm999.cn\" target=\"_blank\">www.adm999.cn</a></div>&nbsp;</td>\n" 
		 */
		String wzmc = patternInfo("<td align=\"left\" class=\"bx\" width=\"20%\">网站名称：</td>"
				+"[\\s\\S]*?<td align=\"left\" class=\"by1\" width=\"30%\">([\\s\\S]*?)&nbsp;</td>" ,html);
		//System.out.println();
		String wzsydzString = patternInfo("<td align=\"left\" class=\"bx\" width=\"20%\">网站首页网址：</td>\n" + 
				"[\\\\s\\\\S]*?<td align=\"left\" class=\"by2\" width=\"30%\">"
				+"([\\s\\S]*?)</td>",html);
		IOTest ioTest = new IOTest();
		File htmlFile = new File("D:/outTextFile/outHtml.txt");
		ioTest.WriteInFile(htmlFile, html);
		
		System.out.println(wzsydzString);
		String wzsydz = patternWebsiteIndexInfo("<a[\\s\\S]*?>([\\s\\S]*?)</a>", wzsydzString);
		System.out.println(wzsydz);
		String wzfzrxm = patternInfo("<td align=\\\"left\\\" class=\\\"bx\\\">网站负责人姓名：</td>"
				+"[\\s\\S]*?<td align=\\\"left\\\" class=\\\"by1\\\">([\\s\\S]*?)&nbsp;</td>" ,html);
		IcpInfoBean icpInfoBean = new IcpInfoBean();
		icpInfoBean.setBAH(BAH);
		icpInfoBean.setPassTime(passTime);
		icpInfoBean.setWzfzrxm(wzfzrxm);
		icpInfoBean.setWzmc(wzmc);
		icpInfoBean.setWzsydz(wzsydz);
		icpInfoBean.setZbdw(zbdw);
		icpInfoBean.setZbdwxz(zbdwxz);
		System.out.println(icpInfoBean.getWzsydz());
		return icpInfoBean;
	}
	private String patternInfo(String pattrenString, String html) {
		Pattern pattern = Pattern.compile(pattrenString);
		//pattern = Pattern.compile("<html>");
		Matcher matcher = pattern.matcher(html);
		
		//替换第一个符合正则的数据
		//System.out.println(matcher.matches());
		if(matcher.find()) {
			String matcherString = matcher.group(1);
			System.out.println(matcher.group(1));
			return matcherString;
			
		}
		return null;
	}
	private String patternWebsiteIndexInfo(String pattrenString, String html) {
		if(html == null) return null;
		String matcherString = null;
		Pattern pattern = Pattern.compile(pattrenString);
		Matcher matcher = pattern.matcher(html);
		//替换第一个符合正则的数据
		//System.out.println(matcher.matches());
		if(matcher.find()) {
			matcherString = matcher.group(1);
		}
		while(matcher.find()) {
			matcherString += " " + matcher.group(1);
		}
		System.out.println(matcherString);
		return matcherString;
	}
	//doDetail('80945084');
	public static void main(String[] args) {
		MyPattern myPatten = new MyPattern();
		IcpInfoBean icpInfoBean = myPatten.findIcpInfoInHtml( baiduDetail);
		//ExportToExcel exportToExcel = new ExportToExcel();
		//exportToExcel.myProcess(icpInfoBean);
		myPatten.findIcpInfoInHtml( adm999Detail);
		myPatten.findIcpInfoInHtml( adm9Detail);
		
	}
	public static final String adm9Detail =
	"<td align=\"left\" class=\"bx\" width=\"20%\">网站首页网址：</td>\n" + 
	"						<td align=\"left\" class=\"by2\" width=\"30%\"><div> <a href=\"http://www.beiyagk.com\" target=\"_blank\">www.beiyagk.com</a></div>&nbsp;</td>";
	public static final String adm99Detail =
	"<tbody><tr>\n" + 
	"						<td align=\"left\" class=\"bx\" width=\"20%\">网站名称：</td>\n" + 
	"						<td align=\"left\" class=\"by1\" width=\"30%\">北亚骨科网&nbsp;</td>\n" + 
	"						<td align=\"left\" class=\"bx\" width=\"20%\">网站首页网址：</td>\n" + 
	"						<td align=\"left\" class=\"by2\" width=\"30%\"><div> <a href=\"http://www.beiyagk.com\" target=\"_blank\">www.beiyagk.com</a></div>&nbsp;</td>\n" + 
	"					</tr>\n" + 
	"					<tr>\n" + 
	"						<td align=\"left\" class=\"bx\">网站负责人姓名：</td>\n" + 
	"						<td align=\"left\" class=\"by1\">王建宁&nbsp;</td>\n" + 
	"						<td align=\"left\" class=\"bx\">网站域名：</td>\n" + 
	"						<td align=\"left\" class=\"by1\">\n" + 
	"							<span class=\"STYLE2\">\n" + 
	"				                     \n" + 
	"				                        <div>beiyagk.com</div> \n" + 
	"				                    &nbsp;\n" + 
	"				             </span>\n" + 
	"						</td>\n" + 
	"					</tr>\n" + 
	"					<tr>\n" + 
	"						<td align=\"left\" class=\"bx\">网站备案/许可证号：</td>\n" + 
	"						<td align=\"left\" class=\"by1\">京ICP备11043686号-5&nbsp;</td>\n" + 
	"						<td align=\"left\" class=\"bx\">网站前置审批项：</td>\n" + 
	"						<td align=\"left\" class=\"by1\">\n" + 
	"							&nbsp;\n" + 
	"						</td>\n" + 
	"					</tr>\n" + 
	"					<tr>\n" + 
	"						<td colspan=\"4\" bgcolor=\"#edf2f8\" align=\"center\">&nbsp;</td>\n" + 
	"					</tr>\n" + 
	"				</tbody>";
	public static final String adm999Detail ="<html><head>\n" + 
	"		<meta http-equiv=\"Content-Type\" content=\"text/html;charsetpub=GBK\">\n" + 
	"<meta http-equiv=\"Cache-Control\" content=\"no-store\">\n" + 
	"<meta http-equiv=\"Pragma\" content=\"no-cache\">\n" + 
	"<meta http-equiv=\"Expires\" content=\"0\">\n" + 
	"<meta http-equiv=\"x-ua-compatible\" content=\"ie=7\">\n" + 
	"		<title>备案详细信息</title>\n" + 
	"		<link href=\"/styles/style.css\" rel=\"stylesheet\" type=\"text/css\">\n" + 
	"		<link href=\"/styles/balckListTab.css\" rel=\"stylesheet\" type=\"text/css\">\n" + 
	"		<link href=\"/styles/tablesorter.css\" rel=\"stylesheet\" type=\"text/css\">\n" + 
	"		<script type=\"text/javascript\">\n" + 
	"		</script>\n" + 
	"	</head>\n" + 
	"	<body>\n" + 
	"		<div class=\"dh\">\n" + 
	"			<div class=\"fl\" width=\"80%\">\n" + 
	"				<img src=\"/images/dh01.gif\" width=\"29\" height=\"25\" hspace=\"10\" align=\"absmiddle\">\n" + 
	"				<span class=\"dhfont\">当前位置：公共查询&nbsp;&nbsp;－＞&nbsp;&nbsp;备案查询&nbsp;&nbsp;－＞&nbsp;&nbsp;备案信息</span>\n" + 
	"			</div>\n" + 
	"			<div class=\"fr\" width=\"150px\"><img id=\"z0\" hspace=\"10\" style=\"cursor:pointer;padding:1px\" align=\"absmiddle\" src=\"/images/backing.gif\" onclick=\"history.go(-1);\"></div>\n" + 
	"		</div>\n" + 
	"		<table width=\"97%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#c0d9f8\" style=\"margin-top: 15px\">\n" + 
	"			<tbody><tr>\n" + 
	"				<td width=\"3\" height=\"26\"><img src=\"/images/b_01.gif\" width=\"3\" height=\"26\"></td>\n" + 
	"				<td height=\"26\" background=\"/images/b_08.gif\" class=\"text in5\"><span class=\"text\">ICP备案主体信息</span></td>\n" + 
	"				<td width=\"3\" height=\"26\"><img src=\"/images/b_07.gif\" width=\"3\" height=\"26\"></td>\n" + 
	"			</tr>\n" + 
	"			<tr>\n" + 
	"				<td width=\"3\" background=\"/images/b_02.gif\"><img src=\"/images/b_02.gif\" width=\"3\" height=\"1\"></td>\n" + 
	"				<td class=\"a\">\n" + 
	"				<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" + 
	"					<tbody><tr>\n" + 
	"						<td align=\"left\" class=\"bx\" width=\"20%\">备案/许可证号：</td>\n" + 
	"						<td align=\"left\" class=\"by1\" width=\"30%\">京ICP备13004919号&nbsp;</td>\n" + 
	"						<td align=\"left\" class=\"bx\" width=\"20%\">审核通过时间：</td>\n" + 
	"						<td align=\"left\" class=\"by2\" width=\"30%\">2017-09-05&nbsp;</td>\n" + 
	"					</tr>\n" + 
	"					<tr>\n" + 
	"						<td width=\"18%\" align=\"left\" class=\"bx\">主办单位名称：</td>\n" + 
	"						<td width=\"29%\" align=\"left\" class=\"by1\">北京安定门中医医院&nbsp;</td>\n" + 
	"						<td width=\"16%\" align=\"left\" class=\"bx\">主办单位性质：</td>\n" + 
	"						<td align=\"left\" class=\"by2\">企业&nbsp;</td>\n" + 
	"					</tr>\n" + 
	"					<tr>\n" + 
	"						<td colspan=\"4\" bgcolor=\"#edf2f8\" align=\"center\">&nbsp;&nbsp;</td>\n" + 
	"					</tr>\n" + 
	"				</tbody></table>\n" + 
	"				</td>\n" + 
	"				<td width=\"3\" background=\"/images/b_06.gif\"><img src=\"/images/b_06.gif\" width=\"3\" height=\"1\"></td>\n" + 
	"			</tr>\n" + 
	"			<tr>\n" + 
	"				<td width=\"3\" height=\"3\"><img src=\"/images/b_03.gif\" width=\"3\" height=\"3\"></td>\n" + 
	"				<td height=\"3\" background=\"/images/b_04.gif\"></td>\n" + 
	"				<td width=\"3\" height=\"3\"><img src=\"/images/b_05.gif\" width=\"3\" height=\"3\"></td>\n" + 
	"			</tr>\n" + 
	"		</tbody></table>	\n" + 
	"		<table width=\"97%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#c0d9f8\" style=\"margin-top: 15px\">\n" + 
	"			<tbody><tr>\n" + 
	"				<td width=\"3\" height=\"26\"><img src=\"/images/b_01.gif\" width=\"3\" height=\"26\"></td>\n" + 
	"				<td height=\"26\" background=\"/images/b_08.gif\" class=\"text in5\"><span class=\"text\">ICP备案网站信息</span></td>\n" + 
	"				<td width=\"3\" height=\"26\"><img src=\"/images/b_07.gif\" width=\"3\" height=\"26\"></td>\n" + 
	"			</tr>\n" + 
	"			<tr>\n" + 
	"				<td width=\"3\" background=\"/images/b_02.gif\"><img src=\"/images/b_02.gif\" width=\"3\" height=\"1\"></td>\n" + 
	"				<td class=\"a\">\n" + 
	"				<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">			\n" + 
	"					<tbody><tr>\n" + 
	"						<td align=\"left\" class=\"bx\" width=\"20%\">网站名称：</td>\n" + 
	"						<td align=\"left\" class=\"by1\" width=\"30%\">北京安定门中医医院&nbsp;</td>\n" + 
	"						<td align=\"left\" class=\"bx\" width=\"20%\">网站首页网址：</td>\n" + 
	"						<td align=\"left\" class=\"by2\" width=\"30%\"><div> "
	+ "<a href=\"http://www.adm999.com\" target=\"_blank\">www.adm999.com</a></div><div> "
	+ "<a href=\"http://www.adm999.cn\" target=\"_blank\">www.adm999.cn</a></div>&nbsp;</td>\n" + 
	"					</tr>\n" + 
	"					<tr>\n" + 
	"						<td align=\"left\" class=\"bx\">网站负责人姓名：</td>\n" + 
	"						<td align=\"left\" class=\"by1\">杨丽莎&nbsp;</td>\n" + 
	"						<td align=\"left\" class=\"bx\">网站域名：</td>\n" + 
	"						<td align=\"left\" class=\"by1\">\n" + 
	"							<span class=\"STYLE2\">\n" + 
	"				                     \n" + 
	"				                        <div>adm999.com</div> \n" + 
	"				                    \n" + 
	"				                        <div>adm999.cn</div> \n" + 
	"				                    &nbsp;\n" + 
	"				             </span>\n" + 
	"						</td>\n" + 
	"					</tr>\n" + 
	"					<tr>\n" + 
	"						<td align=\"left\" class=\"bx\">网站备案/许可证号：</td>\n" + 
	"						<td align=\"left\" class=\"by1\">京ICP备13004919号-1&nbsp;</td>\n" + 
	"						<td align=\"left\" class=\"bx\">网站前置审批项：</td>\n" + 
	"						<td align=\"left\" class=\"by1\">\n" + 
	"							&nbsp;\n" + 
	"						</td>\n" + 
	"					</tr>\n" + 
	"					<tr>\n" + 
	"						<td colspan=\"4\" bgcolor=\"#edf2f8\" align=\"center\">&nbsp;</td>\n" + 
	"					</tr>\n" + 
	"				</tbody></table>\n" + 
	"				</td>\n" + 
	"				<td width=\"3\" background=\"/images/b_06.gif\"><img src=\"/images/b_06.gif\" width=\"3\" height=\"1\"></td>\n" + 
	"			</tr>\n" + 
	"			<tr>\n" + 
	"				<td width=\"3\" height=\"3\"><img src=\"/images/b_03.gif\" width=\"3\" height=\"3\"></td>\n" + 
	"				<td height=\"3\" background=\"/images/b_04.gif\"></td>\n" + 
	"				<td width=\"3\" height=\"3\"><img src=\"/images/b_05.gif\" width=\"3\" height=\"3\"></td>\n" + 
	"			</tr>\n" + 
	"		</tbody></table>\n" + 
	"	\n" + 
	"</body></html>";
	public static final String baiduDetail = "<html><head>\n" + 
			"		<meta http-equiv=\"Content-Type\" content=\"text/html;charset=GBK\">\n" + 
			"<meta http-equiv=\"Cache-Control\" content=\"no-store\">\n" + 
			"<meta http-equiv=\"Pragma\" content=\"no-cache\">\n" + 
			"<meta http-equiv=\"Expires\" content=\"0\">\n" + 
			"<meta http-equiv=\"x-ua-compatible\" content=\"ie=7\">\n" + 
			"		<title>备案详细信息</title>\n" + 
			"		<link href=\"/styles/style.css\" rel=\"stylesheet\" type=\"text/css\">\n" + 
			"		<link href=\"/styles/balckListTab.css\" rel=\"stylesheet\" type=\"text/css\">\n" + 
			"		<link href=\"/styles/tablesorter.css\" rel=\"stylesheet\" type=\"text/css\">\n" + 
			"		<script type=\"text/javascript\">\n" + 
			"		</script>\n" + 
			"	</head>\n" + 
			"	<body>\n" + 
			"		<div class=\"dh\">\n" + 
			"			<div class=\"fl\" width=\"80%\">\n" + 
			"				<img src=\"/images/dh01.gif\" width=\"29\" height=\"25\" hspace=\"10\" align=\"absmiddle\">\n" + 
			"				<span class=\"dhfont\">当前位置：公共查询&nbsp;&nbsp;－＞&nbsp;&nbsp;备案查询&nbsp;&nbsp;－＞&nbsp;&nbsp;备案信息</span>\n" + 
			"			</div>\n" + 
			"			<div class=\"fr\" width=\"150px\"><img id=\"z0\" hspace=\"10\" style=\"cursor:pointer;padding:1px\" align=\"absmiddle\" src=\"/images/backing.gif\" onclick=\"history.go(-1);\"></div>\n" + 
			"		</div>\n" + 
			"		<table width=\"97%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#c0d9f8\" style=\"margin-top: 15px\">\n" + 
			"			<tbody><tr>\n" + 
			"				<td width=\"3\" height=\"26\"><img src=\"/images/b_01.gif\" width=\"3\" height=\"26\"></td>\n" + 
			"				<td height=\"26\" background=\"/images/b_08.gif\" class=\"text in5\"><span class=\"text\">ICP备案主体信息</span></td>\n" + 
			"				<td width=\"3\" height=\"26\"><img src=\"/images/b_07.gif\" width=\"3\" height=\"26\"></td>\n" + 
			"			</tr>\n" + 
			"			<tr>\n" + 
			"				<td width=\"3\" background=\"/images/b_02.gif\"><img src=\"/images/b_02.gif\" width=\"3\" height=\"1\"></td>\n" + 
			"				<td class=\"a\">\n" + 
			"				<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" + 
			"					<tbody><tr>\n" + 
			"						<td align=\"left\" class=\"bx\" width=\"20%\">备案/许可证号：</td>\n" + 
			"						<td align=\"left\" class=\"by1\" width=\"30%\">京ICP证030173号&nbsp;</td>\n" + 
			"						<td align=\"left\" class=\"bx\" width=\"20%\">审核通过时间：</td>\n" + 
			"						<td align=\"left\" class=\"by2\" width=\"30%\">2017-12-22&nbsp;</td>\n" + 
			"					</tr>\n" + 
			"					<tr>\n" + 
			"						<td width=\"18%\" align=\"left\" class=\"bx\">主办单位名称：</td>\n" + 
			"						<td width=\"29%\" align=\"left\" class=\"by1\">北京百度网讯科技有限公司&nbsp;</td>\n" + 
			"						<td width=\"16%\" align=\"left\" class=\"bx\">主办单位性质：</td>\n" + 
			"						<td align=\"left\" class=\"by2\">企业&nbsp;</td>\n" + 
			"					</tr>\n" + 
			"					<tr>\n" + 
			"						<td colspan=\"4\" bgcolor=\"#edf2f8\" align=\"center\">&nbsp;&nbsp;</td>\n" + 
			"					</tr>\n" + 
			"				</tbody></table>\n" + 
			"				</td>\n" + 
			"				<td width=\"3\" background=\"/images/b_06.gif\"><img src=\"/images/b_06.gif\" width=\"3\" height=\"1\"></td>\n" + 
			"			</tr>\n" + 
			"			<tr>\n" + 
			"				<td width=\"3\" height=\"3\"><img src=\"/images/b_03.gif\" width=\"3\" height=\"3\"></td>\n" + 
			"				<td height=\"3\" background=\"/images/b_04.gif\"></td>\n" + 
			"				<td width=\"3\" height=\"3\"><img src=\"/images/b_05.gif\" width=\"3\" height=\"3\"></td>\n" + 
			"			</tr>\n" + 
			"		</tbody></table>	\n" + 
			"		<table width=\"97%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#c0d9f8\" style=\"margin-top: 15px\">\n" + 
			"			<tbody><tr>\n" + 
			"				<td width=\"3\" height=\"26\"><img src=\"/images/b_01.gif\" width=\"3\" height=\"26\"></td>\n" + 
			"				<td height=\"26\" background=\"/images/b_08.gif\" class=\"text in5\"><span class=\"text\">ICP备案网站信息</span></td>\n" + 
			"				<td width=\"3\" height=\"26\"><img src=\"/images/b_07.gif\" width=\"3\" height=\"26\"></td>\n" + 
			"			</tr>\n" + 
			"			<tr>\n" + 
			"				<td width=\"3\" background=\"/images/b_02.gif\"><img src=\"/images/b_02.gif\" width=\"3\" height=\"1\"></td>\n" + 
			"				<td class=\"a\">\n" + 
			"				<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">			\n" + 
			"					<tbody><tr>\n" + 
			"						<td align=\"left\" class=\"bx\" width=\"20%\">网站名称：</td>\n" + 
			"						<td align=\"left\" class=\"by1\" width=\"30%\">百度&nbsp;</td>\n" + 
			"						<td align=\"left\" class=\"bx\" width=\"20%\">网站首页网址：</td>\n" + 
			"						<td align=\"left\" class=\"by2\" width=\"30%\"><div> <a href=\"http://www.baidu.com\" target=\"_blank\">www.baidu.com</a></div>&nbsp;</td>\n" + 
			"					</tr>\n" + 
			"					<tr>\n" + 
			"						<td align=\"left\" class=\"bx\">网站负责人姓名：</td>\n" + 
			"						<td align=\"left\" class=\"by1\">苏静&nbsp;</td>\n" + 
			"						<td align=\"left\" class=\"bx\">网站域名：</td>\n" + 
			"						<td align=\"left\" class=\"by1\">\n" + 
			"							<span class=\"STYLE2\">\n" + 
			"				                     \n" + 
			"				                        <div>baidu.com</div> \n" + 
			"				                    &nbsp;\n" + 
			"				             </span>\n" + 
			"						</td>\n" + 
			"					</tr>\n" + 
			"					<tr>\n" + 
			"						<td align=\"left\" class=\"bx\">网站备案/许可证号：</td>\n" + 
			"						<td align=\"left\" class=\"by1\">京ICP证030173号-1&nbsp;</td>\n" + 
			"						<td align=\"left\" class=\"bx\">网站前置审批项：</td>\n" + 
			"						<td align=\"left\" class=\"by1\">\n" + 
			"							&nbsp;\n" + 
			"						</td>\n" + 
			"					</tr>\n" + 
			"					<tr>\n" + 
			"						<td colspan=\"4\" bgcolor=\"#edf2f8\" align=\"center\">&nbsp;</td>\n" + 
			"					</tr>\n" + 
			"				</tbody></table>\n" + 
			"				</td>\n" + 
			"				<td width=\"3\" background=\"/images/b_06.gif\"><img src=\"/images/b_06.gif\" width=\"3\" height=\"1\"></td>\n" + 
			"			</tr>\n" + 
			"			<tr>\n" + 
			"				<td width=\"3\" height=\"3\"><img src=\"/images/b_03.gif\" width=\"3\" height=\"3\"></td>\n" + 
			"				<td height=\"3\" background=\"/images/b_04.gif\"></td>\n" + 
			"				<td width=\"3\" height=\"3\"><img src=\"/images/b_05.gif\" width=\"3\" height=\"3\"></td>\n" + 
			"			</tr>\n" + 
			"		</tbody></table>\n" + 
			"	\n" + 
			"</body></html>";
	
	public static final String testHtml = "<html><head>\n" + 
			"<meta http-equiv=\"Content-Type\" content=\"text/html;charset=GBK\">\n" + 
			"<meta http-equiv=\"Cache-Control\" content=\"no-store\">\n" + 
			"<meta http-equiv=\"Pragma\" content=\"no-cache\">\n" + 
			"<meta http-equiv=\"Expires\" content=\"0\">\n" + 
			"<meta http-equiv=\"x-ua-compatible\" content=\"ie=7\">	\n" + 
			"<title>备案信息查询</title>\n" + 
			"<script src=\"/scripts/jquery.js\" type=\"text/javascript\"></script>		\n" + 
			"<script src=\"/scripts/ValidateTools.js\" type=\"text/javascript\"></script>	\n" + 
			"<script src=\"/scripts/common-validate.js\" type=\"text/javascript\"></script>\n" + 
			"<script type=\"text/javascript\" src=\"/scripts/maskDialog/maskDialog.js\"></script>	\n" + 
			"<link href=\"/styles/style.css\" rel=\"stylesheet\" type=\"text/css\">\n" + 
			"<link href=\"/scripts/maskDialog/maskDialog.css\" rel=\"stylesheet\" type=\"text/css\">\n" + 
			"<style type=\"text/css\"> \n" + 
			"	.M_1	{height:25px; width:380px; margin:0px auto; color:#fff; border-left:1px solid #5c79a3; border-right:1px solid #5c79a3; font-size:14px; font-weight: bold; text-align: center; line-height: 25px; background:url(/images/b_08.gif) red; }\n" + 
			"	.M_2	{height:200px; width:380px; margin:0px auto;text-align: center; border-left:1px solid #5c79a3; border-right:1px solid #5c79a3; border-bottom:1px solid #5c79a3; overflow: hidden;}\n" + 
			"	.M_2img {width:150px; margin:30px auto 0px auto; height:30px;}\n" + 
			"	.M_put	{border:1px solid #9ccceb;background:#ffffcc; height:24px; width:180px; text-indent: 5px; color:#2e801a; line-height:25px;  text-align: center; margin:10px auto;font-size:16px; font-weight: bold;}\n" + 
			"	.M_2_2	{height:40px; line-height: 40px; text-align: center;margin:30px auto 0px auto;}\n" + 
			"</style>\n" + 
			"<script type=\"text/javascript\">\n" + 
			"	var MaskDialog=new maskDialog(380,225);\n" + 
			"	window.onload=function(){\n" + 
			"		MaskDialog.mainForm=document.getElementById(\"div_Dialog\");\n" + 
			"	};\n" + 
			"	\n" + 
			"	function changeMaskHeight(){\n" + 
			"		MaskDialog.show();\n" + 
			"		var maskHeight = new Number($(\"#ifr_Mask\").attr(\"height\"))+50; \n" + 
			"		$(\"#ifr_Mask\").attr(\"height\",maskHeight);\n" + 
			"		$(\"#div_Mask\").css(\"height\",maskHeight);\n" + 
			"	}\n" + 
			"					function MM_preloadImages() { //v3.0\n" + 
			"					  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();\n" + 
			"					    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)\n" + 
			"					    if (a[i].indexOf(\"#\")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}\n" + 
			"					}\n" + 
			"					\n" + 
			"					function nxx(n){  \n" + 
			"					 	var b = document.getElementById('r'+n).style.display=='none';\n" + 
			"						for(var i=0;i<2;i++){\n" + 
			"							if(n==i){\n" + 
			"						if(!b){\n" + 
			"								$(\"#r\"+i).slideUp('fast');\n" + 
			"								$(\"#z\"+i).attr('src','/images/nxx7.gif ');\n" + 
			"							}else{\n" + 
			"								$(\"#r\"+i).slideDown('fast');\n" + 
			"								$(\"#z\"+i).attr('src','/images/nxx8.gif  ');\n" + 
			"							}	\n" + 
			"							}\n" + 
			"						}\n" + 
			"					}\n" + 
			"					function MM_openBrWindow(theURL,winName,features) { //v2.0\n" + 
			"					  window.open(theURL,winName,features);\n" + 
			"					}\n" + 
			"					\n" + 
			"					function doDetail(id){\n" + 
			"						if(id==undefined){\n" + 
			"							document.getElementById(\"vcode\").src = '/getVerifyCode?' + Math.floor(Math.random()*100);\n" + 
			"						}else{\n" + 
			"							document.getElementById(\"vcode\").src = '/getDetailVerifyCode?' + Math.floor(Math.random()*100);\n" + 
			"						}\n" + 
			"						document.getElementById('infoId').value=id;	\n" + 
			"						$(\"#verifyCode\").val('');\n" + 
			"						$(\"#errorDiv\").empty().hide();\n" + 
			"						changeMaskHeight();\n" + 
			"					}\n" + 
			"					\n" + 
			"					function go_back(){\n" + 
			"						from1 = document.getElementById(\"icpMemoInfo\");\n" + 
			"						url = '/icp/publish/query/icpMemoInfo_showPage.action';\n" + 
			"						from1.action = url;\n" + 
			"						from1.submit();\n" + 
			"					}\n" + 
			"					function doSubmit(){\n" + 
			"						var from1;\n" + 
			"						if($(\"#infoId\").val()=='undefined'){\n" + 
			"							from1 = document.getElementById(\"icpMemoInfo\");\n" + 
			"						}else{\n" + 
			"							from1 = document.getElementById(\"detailForm\");\n" + 
			"						}\n" + 
			"						var code = document.getElementById('verifyCode').value;\n" + 
			"						if(checkNull(code)){\n" + 
			"							if(checkKeyword('',false)){\n" + 
			"								if($(\"#infoId\").val()=='undefined'){\n" + 
			"									var inputNode=document.createElement(\"input\");\n" + 
			"									inputNode.setAttribute(\"name\",\"verifyCode\");\n" + 
			"									inputNode.setAttribute(\"value\",code);\n" + 
			"									inputNode.setAttribute(\"type\",\"hide\");\n" + 
			"									from1.appendChild(inputNode);\n" + 
			"								}\n" + 
			"								from1.submit();\n" + 
			"							}else{\n" + 
			"								return false;\n" + 
			"							}\n" + 
			"						}else{\n" + 
			"							showMessage();\n" + 
			"							return false;\n" + 
			"						}\n" + 
			"					}\n" + 
			"					function showMessage(){\n" + 
			"						document.getElementById('errorDiv').style.display = 'block';\n" + 
			"						document.getElementById('errorDiv').innerHTML = \"验证码不能为空，请输入验证码!\";\n" + 
			"						document.getElementById(\"verifyCode\").focus();\n" + 
			"					}	\n" + 
			"	</script>\n" + 
			"	</head>\n" + 
			"	<body>\n" + 
			"			<div class=\"dh\">\n" + 
			"				<div class=\"fl\" width=\"80%\">\n" + 
			"					<img src=\"/images/dh01.gif\" width=\"29\" height=\"25\" hspace=\"10\" align=\"absmiddle\">\n" + 
			"					<span class=\"dhfont\">当前位置：公共查询&nbsp;&nbsp;－＞&nbsp;&nbsp;备案查询</span>\n" + 
			"				</div>\n" + 
			"				<div class=\"fr\" width=\"150px\"><img id=\"z0\" hspace=\"10\" style=\"cursor:pointer;padding:1px\" align=\"absmiddle\" src=\"/images/backing.gif\" onclick=\"go_back()\"></div>\n" + 
			"			</div>\n" + 
			"			<br>\n" + 
			"			<form id=\"icpMemoInfo\" action=\"/icp/publish/query/icpMemoInfo_searchExecute.action\" method=\"post\">\n" + 
			"			<div id=\"r0\" style=\"display:none\">\n" + 
			"					  <table width=\"97%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" class=\"text in5\" style=\"background-color:lightsteelblue;\">\n" + 
			"					    <tbody><tr>\n" + 
			"					      <td align=\"left\" bgcolor=\"#F3F8FE\">主办单位名称：：</td>\n" + 
			"					      <td align=\"left\" bgcolor=\"#FFFFFF\"><label>\n" + 
			"					        <input name=\"unitName\" type=\"text\" class=\"in5 input\" id=\"unitName\" value=\"\">\n" + 
			"					      </label></td>\n" + 
			"					      <td align=\"left\" bgcolor=\"#F3F8FE\">网站名称：</td>\n" + 
			"					      <td align=\"left\" bgcolor=\"#FFFFFF\"><input name=\"siteName\" type=\"text\" class=\"in5 input\" id=\"siteName\" value=\"\"></td>\n" + 
			"					      <td rowspan=\"3\" align=\"center\" bgcolor=\"#FFFFFF\"><label>\n" + 
			"					        <input name=\"button\" type=\"submit\" class=\"an2\" id=\"button\" value=\"查询\" style=\"cursor:pointer\">\n" + 
			"					      </label>\n" + 
			"					        &nbsp;&nbsp;\n" + 
			"					        <label>\n" + 
			"					          <input name=\"button\" type=\"reset\" class=\"an2\" id=\"button\" value=\"重置\" style=\"cursor:pointer\">\n" + 
			"					        </label></td>\n" + 
			"					    </tr>\n" + 
			"					    <tr>\n" + 
			"						      <td align=\"left\" bgcolor=\"#F3F8FE\"> 网站域名：</td>\n" + 
			"						      <td align=\"left\" bgcolor=\"#FFFFFF\"><input name=\"siteDomain\" type=\"text\" class=\"in5 input\" id=\"siteDomain\" value=\"baidu.com\"></td>\n" + 
			"						      <td align=\"left\" bgcolor=\"#F3F8FE\">网站首页网址：</td>\n" + 
			"						      <td align=\"left\" bgcolor=\"#FFFFFF\"><input name=\"siteUrl\" type=\"text\" class=\"in5 input\" id=\"siteUrl\" value=\"\"></td>\n" + 
			"				        </tr>\n" + 
			"					    <tr>\n" + 
			"					      <td align=\"left\" bgcolor=\"#F3F8FE\">备案/许可证号：</td>\n" + 
			"					      <td align=\"left\" bgcolor=\"#FFFFFF\"><input name=\"mainLicense\" type=\"text\" class=\"in5 input\" id=\"mainLicense\" value=\"\"></td>\n" + 
			"					      <td align=\"left\" bgcolor=\"#F3F8FE\">网站IP地址：</td>\n" + 
			"					      <td align=\"left\" bgcolor=\"#FFFFFF\"><input name=\"siteIp\" type=\"text\" class=\"in5 input\" id=\"mainLicense\" value=\"\"></td>\n" + 
			"					    </tr>\n" + 
			"					    <tr>\n" + 
			"				         <td align=\"left\" class=\"by1\"><input type=\"radio\" name=\"condition\" id=\"radio6\" value=\"6\" onclick=\"setDisableState('6',true)\">\n" + 
			"				         <label for=\"radio6\">   证件类型：</label> \n" + 
			"				         </td>\n" + 
			"				          <td align=\"left\" valign=\"bottom\" class=\"by2\">\n" + 
			"				          	<div id=\"d6\" onclick=\"setNoDisable('6')\"> \n" + 
			"				          			<select id=\"z6\" name=\"mainUnitNature\" class=\"ibs in5\" onchange=\"ajaxMenuChange(this.id , 'z66','/common/menu/getCredentialsCategory.action');\" style=\"padding-right:5px; width: 120px;\">\n" + 
			"										<option value=\"-1\" selected=\"selected\">请选择</option>\n" + 
			"										\n" + 
			"											<option value=\"1\">军队</option>\n" + 
			"										\n" + 
			"											<option value=\"2\">政府机关</option>\n" + 
			"										\n" + 
			"											<option value=\"3\">事业单位</option>\n" + 
			"										\n" + 
			"											<option value=\"4\">企业</option>\n" + 
			"										\n" + 
			"											<option value=\"5\">个人</option>\n" + 
			"										\n" + 
			"											<option value=\"6\">社会团体</option>\n" + 
			"										\n" + 
			"											<option value=\"9\">民办非企业</option>\n" + 
			"										\n" + 
			"											<option value=\"10\">基金会</option>\n" + 
			"										\n" + 
			"											<option value=\"11\">律师事务所</option>\n" + 
			"										\n" + 
			"											<option value=\"12\">外国文化中心</option>\n" + 
			"										\n" + 
			"											<option value=\"13\">群团组织</option>\n" + 
			"										\n" + 
			"											<option value=\"14\">司法鉴定所</option>\n" + 
			"										\n" + 
			"									</select> \n" + 
			"									<select id=\"z66\" name=\"certType\" class=\"ibs in5\" style=\"padding-right:5px;width: 130px;\">\n" + 
			"										<option value=\"-1\" selected=\"selected\">请选择</option>\n" + 
			"										\n" + 
			"									</select> \n" + 
			"									证件号码：<input name=\"mainUnitCertNo\" type=\"text\" class=\"ibs in5\" id=\"z666\" value=\"\">\n" + 
			"									&nbsp;<span id=\"s6\" class=\"hong\"></span>\n" + 
			"							</div>		\n" + 
			"				          </td>\n" + 
			"				        </tr>\n" + 
			"					  </tbody></table>\n" + 
			"			    <br>\n" + 
			"			</div>\n" + 
			"			\n" + 
			"\n" + 
			"\n" + 
			"\n" + 
			"			<table width=\"97%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#c0d9f8\">\n" + 
			"			  <tbody><tr>\n" + 
			"				    <td width=\"3\" height=\"26\"><img src=\"/images/b_01.gif\" width=\"3\" height=\"26\"></td>\n" + 
			"				    <td height=\"26\" background=\"/images/b_08.gif\">&nbsp;</td>\n" + 
			"				    <td width=\"3\" height=\"26\"><img src=\"/images/b_07.gif\" width=\"3\" height=\"26\"></td>\n" + 
			"			  </tr>\n" + 
			"			  <tr>\n" + 
			"		    		<td width=\"3\" background=\"/images/b_02.gif\"><img src=\"/images/b_02.gif\" width=\"3\" height=\"1\"></td>\n" + 
			"		    		<td class=\"a\">\n" + 
			"		    			<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" + 
			"							 <tbody><tr>\n" + 
			"						        <td width=\"5%\" align=\"center\" class=\"bx\">序号</td>\n" + 
			"						        \n" + 
			"		      						\n" + 
			"		      							<td align=\"center\" class=\"bx\">主办单位名称</td>\n" + 
			"								        <td align=\"center\" class=\"bx\">主办单位性质</td>\n" + 
			"								        <td align=\"center\" class=\"bx\">网站备案/许可证号</td>\n" + 
			"								        <td align=\"center\" class=\"bx\">网站名称</td>\n" + 
			"								        <td align=\"center\" class=\"bx\">网站首页网址</td>\n" + 
			"								        <td align=\"center\" class=\"bx\">审核时间</td>\n" + 
			"								        <td align=\"center\" class=\"bx\">是否限制接入</td>\n" + 
			"								        \n" + 
			"								        	<td align=\"center\" class=\"by\">详细信息</td>\n" + 
			"								        	<input type=\"hidden\" id=\"detailFlag\" value=\"true\">\n" + 
			"								        \n" + 
			"		      						\n" + 
			"		      						\n" + 
			"		      						\n" + 
			"						    </tr>\n" + 
			"						    \n" + 
			"				      	 		\n" + 
			"								   \n" + 
			"									<tr id=\"1\" align=\"center\">\n" + 
			"										<td align=\"center\" class=\"by1\">1&nbsp;\n" + 
			"											</td>\n" + 
			"										<td align=\"center\" class=\"bxy\">北京百度网讯科技有限公司&nbsp;</td>\n" + 
			"										<td align=\"center\" class=\"bxy\">企业&nbsp;</td>\n" + 
			"										<td align=\"center\" class=\"bxy\">&nbsp;京ICP证030173号-1</td>\n" + 
			"										<td align=\"center\" class=\"bxy\">百度&nbsp;</td>\n" + 
			"										<td align=\"center\" class=\"bxy\"><div> <a href=\"http://www.baidu.com\" target=\"_blank\">www.baidu.com</a></div>&nbsp;</td>\n" + 
			"										<td align=\"center\" class=\"bxy\">2017-12-22&nbsp;</td>\n" + 
			"										\n" + 
			"							   			\n" + 
			"							   				<td align=\"center\" class=\"bxy\">否&nbsp;</td>\n" + 
			"							   				\n" + 
			"										 \n" + 
			"											<td align=\"center\" class=\"by2\">&nbsp;\n" + 
			"												<span onclick=\"doDetail('80945084');\" class=\"hui\" style=\"text-decoration: underline;color: blue;cursor:hand; \">详细</span>\n" + 
			"							   				</td>\n" + 
			"							   			\n" + 
			"							   			\n" + 
			"							   				\n" + 
			"							   			\n" + 
			"									</tr>\n" + 
			"									\n" + 
			"								\n" + 
			"								\n" + 
			"								\n" + 
			"							\n" + 
			"							<tr>\n" + 
			"				  			   <td height=\"20\" colspan=\"9\" align=\"center\" bgcolor=\"#edf2f8\">\n" + 
			"				  			</td></tr>\n" + 
			"						 </tbody></table>\n" + 
			"					</td>\n" + 
			"					<td width=\"3\" background=\"/images/b_06.gif\"><img src=\"/images/b_06.gif\" width=\"3\" height=\"1\"></td>\n" + 
			"			   </tr>\n" + 
			"			   <tr>\n" + 
			"				    <td width=\"3\" height=\"3\"><img src=\"/images/b_03.gif\" width=\"3\" height=\"3\"></td>\n" + 
			"				    <td height=\"3\" background=\"/images/b_04.gif\"></td>\n" + 
			"				    <td width=\"3\" height=\"3\"><img src=\"/images/b_05.gif\" width=\"3\" height=\"3\"></td>\n" + 
			"	 		   </tr>\n" + 
			"			</tbody></table>\n" + 
			"	   	    <table width=\"97%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"text in5\">\n" + 
			" 		   <tbody><tr>\n" + 
			"		        <td align=\"right\" colspan=\"4\">\n" + 
			"			        <div style=\"width: 97%\">\n" + 
			"						<style type=\"text/css\">\n" + 
			"<!--\n" + 
			"-->\n" + 
			"</style>\n" + 
			"\n" + 
			"每页记录数<select class=\"input\" name=\"page.pageSize\" onchange=\"go(this.value);\">\n" + 
			"<option value=\"5\">5</option>\n" + 
			"<option value=\"10\">10</option>\n" + 
			"<option value=\"20\" selected=\"selected\">20</option>\n" + 
			"<option value=\"50\">50</option>\n" + 
			"<option value=\"100\">100</option>\n" + 
			"</select>\n" + 
			"共<span class=\"red\">&nbsp;1&nbsp;</span>条记录&nbsp;\n" + 
			"当前第&nbsp;1/1&nbsp;页\n" + 
			"<span class=\"an7\">\n" + 
			"<span>首页</span>\n" + 
			"<span>上一页</span>\n" + 
			"<span>下一页</span>\n" + 
			"<span>尾页</span>\n" + 
			"&nbsp;&nbsp;<span>\n" + 
			"<input type=\"hidden\" name=\"pageNo\" value=\"1\">\n" + 
			"<input type=\"text\" class=\"input\" name=\"jumpPageNo\" id=\"jumpPageNo\" size=\"4\">\n" + 
			"<a href=\"#\" onclick=\"jump();\">转到</a>\n" + 
			"</span>\n" + 
			"\n" + 
			"<script type=\"text/javascript\">\n" + 
			"document.getElementById(\"icpMemoInfo\").onsubmit = function() {\n" + 
			"this.pageNo.value = 1;\n" + 
			"}\n" + 
			"function checkIsInteger(str){\n" + 
			"if(/^(\\-?)(\\d+)$/.test(str))\n" + 
			"return true;\n" + 
			"else\n" + 
			"return false;}\n" + 
			"function jump(){\n" + 
			"var form = document.getElementById('icpMemoInfo');\n" + 
			"var jumpPageNo = form.jumpPageNo.value;\n" + 
			"if(jumpPageNo == \"\"){\n" + 
			"alert(\"请输入要跳转的页码!\");return;}\n" + 
			"if(!checkIsInteger(jumpPageNo)){\n" + 
			"alert(\"页码必须是数字!\");return;}\n" + 
			"if(jumpPageNo < 1){alert(\"页码不能小于1\");return;}\n" + 
			"if(jumpPageNo > 1){\n" + 
			"alert(\"输入的页面超出最大页面范围!\");return;}\n" + 
			"form.pageNo.value = jumpPageNo;\n" + 
			"doDetail();\n" + 
			"}\n" + 
			"function firstPage(){\n" + 
			"var form = document.getElementById('icpMemoInfo');\n" + 
			"form.pageNo.value = 1;\n" + 
			"doDetail();\n" + 
			"}\n" + 
			"function lastPage(){\n" + 
			"var form = document.getElementById('icpMemoInfo');\n" + 
			"form.pageNo.value = 1;\n" + 
			"doDetail();\n" + 
			"}\n" + 
			"function previousPage(){\n" + 
			"var form = document.getElementById('icpMemoInfo');\n" + 
			"form.pageNo.value = 0;\n" + 
			"doDetail();\n" + 
			"}\n" + 
			"function nextPage(){\n" + 
			"var form = document.getElementById('icpMemoInfo');\n" + 
			"form.pageNo.value = 2;\n" + 
			"doDetail();\n" + 
			"}\n" + 
			"function setCookie(c_name,value,expiredays){\n" + 
			"var exdate=new Date();\n" + 
			"exdate.setDate(exdate.getDate()+expiredays);\n" + 
			"document.cookie=c_name+ \"=\" +escape(value)+((expiredays==null) ? \"\" : \";expires=\"+exdate.toGMTString()) + \";path=/\";\n" + 
			"}\n" + 
			"function go(pageSize){\n" + 
			"var form = document.getElementById('icpMemoInfo');\n" + 
			"setCookie('icp_report_pageSize', pageSize, 20);\n" + 
			"form.pageNo.value = 1;\n" + 
			"doDetail();\n" + 
			"}\n" + 
			"</script>\n" + 
			"\n" + 
			"\n" + 
			"					</span></div>\n" + 
			"		         </td>\n" + 
			"          </tr>\n" + 
			"</tbody></table> 	\n" + 
			"		</form>\n" + 
			"		<div id=\"div_Dialog\" style=\"top: 50px;\">\n" + 
			"			<div class=\"M_1\">请输入验证码</div>\n" + 
			"			\n" + 
			"			<div class=\"M_2\">\n" + 
			"			<form id=\"detailForm\" action=\"/icp/publish/query/icpMemoInfo_login.action\" method=\"post\">\n" + 
			"					<img class=\"M_2img\" width=\"200\" height=\"60\" id=\"vcode\" alt=\"看不清楚,请点击图片\" title=\"看不清楚,请点击图片\"> <br>\n" + 
			"				    <input class=\"M_put\" name=\"verifyCode\" type=\"text\" id=\"verifyCode\" size=\"8\" maxlength=\"6\"> &nbsp;\n" + 
			"				    <input id=\"infoId\" name=\"id\" type=\"hidden\">\n" + 
			"				    <div id=\"errorDiv\" class=\"errorTip\"></div>\n" + 
			"				    <input name=\"siteName\" type=\"hidden\" value=\"\"> \n" + 
			"				    <input name=\"siteDomain\" type=\"hidden\" value=\"baidu.com\">\n" + 
			"				    <input name=\"siteUrl\" type=\"hidden\" value=\"\">\n" + 
			"				    <input name=\"mainLicense\" type=\"hidden\" value=\"\">\n" + 
			"				    <input name=\"siteIp\" type=\"hidden\" value=\"\">\n" + 
			"				    <input name=\"unitName\" type=\"hidden\" value=\"\">\n" + 
			"				    <input name=\"mainUnitNature\" type=\"hidden\" value=\"-1\">\n" + 
			"				    <input name=\"certType\" type=\"hidden\" value=\"-1\">\n" + 
			"				    <input name=\"mainUnitCertNo\" type=\"hidden\" value=\"\">\n" + 
			"				    <input name=\"bindFlag\" type=\"hidden\" value=\"0\">\n" + 
			"				<div class=\"M_2_2\">\n" + 
			"					<input type=\"button\" onclick=\"doSubmit();\" class=\"an4\" value=\"确定\">&nbsp;&nbsp;&nbsp;<input type=\"button\" class=\"an4\" value=\" 关闭\" onclick=\"MaskDialog.hide();\">\n" + 
			"				</div>\n" + 
			"			</form>\n" + 
			"			</div>\n" + 
			"		</div>\n" + 
			"	\n" + 
			"	<script type=\"text/javascript\">\n" + 
			"	$('#vcode').click(function () { \n" + 
			"		var param = $('#vcode').attr('src').split('?');\n" + 
			"	    $(this).hide().attr('src',param[0]+'?'+Math.floor(Math.random()*100)).fadeIn(); \n" + 
			"	  });\n" + 
			"	</script>\n" + 
			"</body></html>"	;

}
