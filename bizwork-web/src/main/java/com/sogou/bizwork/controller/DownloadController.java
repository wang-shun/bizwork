package com.sogou.bizwork.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sogou.bizwork.bo.user.UserQuery;
import com.sogou.bizwork.bo.user.UserQueryInfo;
import com.sogou.bizwork.bo.user.UserStatisticInfo;
import com.sogou.bizwork.service.UserService;
import com.sogou.bizwork.util.CSVReportUtils;
import com.sogou.bizwork.util.Const;

@Controller
public class DownloadController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/download")
	 public void download(HttpServletRequest request, HttpServletResponse response, UserQuery query ){
		
		String reportName = query.getReportName();
		//表格title
		List<String> title = Arrays.asList(Const.reportTitles.get(reportName).split("[,]"));    
		//表格内容
		List<List<String>> data = new ArrayList<List<String>>();
				if("UserListQuery".equals(reportName)){
			data = generateUserQueryReport(query);
		}
		else if("HCStatistic".equals(reportName)){
			data = generateHCStatisticReport();
		}	
		//csv生成
		String wb = createCSV(title, data);
		response.setContentType("application/vnd.ms-excel;charset=GBK");
		String outFileName = Const.reportNames.get(reportName);
		try {
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0)
			outFileName = new String(outFileName.getBytes("UTF-8"), "ISO8859-1");
			
		else
		    outFileName = java.net.URLEncoder.encode(outFileName, "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + outFileName + ".csv");
			
		BufferedOutputStream os;
			os = new BufferedOutputStream(response.getOutputStream());
		    os.write(wb.getBytes("GBK"));
		    os.close();
		} catch (IOException e) {
			logger.error(e);
		}catch(Exception e) {
			logger.error(e);
		}
	}
	
	private List<List<String>> generateHCStatisticReport() {
		List<List<String>> data = new ArrayList<List<String>>();
		List<String> line = null;
		List<UserStatisticInfo> list = userService.getHCDescription();
		for( UserStatisticInfo s : list ){
			if( null != s ){
				line = new ArrayList<String>();
				line.add( s.getHcDescription());
				line.add( s.getLeaderName());
				line.add( String.valueOf(s.getCount()));
				data.add(line);
			}
		}
		return data;
	}
	
	private List<List<String>> generateUserQueryReport( UserQuery query) {
		List<List<String>> data = new ArrayList<List<String>>();
		List<String> line = null;
		List<UserQueryInfo> userList = userService.getUserListByQuery(query);		
		for (UserQueryInfo u : userList){
			if( null != u ){
				line = new ArrayList<String>();
				line.add( u.getEmployeeId());
				line.add(u.getUserName());
				line.add(u.getChineseName());
				line.add(u.getLeaderName());
				line.add(u.getGroupName());
				line.add(u.getBirthday());
				line.add(u.getHCDescription());
				data.add(line);
			}
		}
		
		return data;
	}
	
	public static String createCSV(List<String> title, List<List<String>> data) {
        return CSVReportUtils.genCSVReport(title, data);
    }
	

}
