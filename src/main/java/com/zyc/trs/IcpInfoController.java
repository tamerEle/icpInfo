package com.zyc.trs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
public class IcpInfoController {

	public IcpInfoController() {
		// TODO Auto-generated constructor stub
	}
	
	//http://www.miitbeian.gov.cn/icp/publish/query/icpMemoInfo_showPage.action
	@RequestMapping("/test")
	@ResponseBody
	public String getIcpInfoAction() {
		return "1";
	}
}
