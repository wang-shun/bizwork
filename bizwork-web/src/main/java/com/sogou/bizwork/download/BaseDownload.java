package com.sogou.bizwork.download;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.springframework.web.context.WebApplicationContext;

import com.sogou.bizwork.util.Const;

public abstract class BaseDownload {
	
	@Setter
    protected WebApplicationContext ctx = null;

    @Setter
    protected String reportName;
    
    public static Map<String, String> reportClasses = new HashMap<String, String>() {
        {
        	put("UserListQuery", "com.sogou.crm.web.download.agent.AllCpcCostDownload");
        	put("HCStatistic", "com.sogou.crm.web.download.agent.AllCpcCostDownload");
        }
    };
    
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String outFileName = Const.reportNames.get(reportName);
        
    }
    
}
