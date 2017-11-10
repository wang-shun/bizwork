<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sogou.bizwork.bo.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>用户管理</title>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	System.out.println("刷新");
	Result res = null;
%>
<Script Language="JavaScript">
    function add(){
    	if(document.getElementById("englishname").value.length != 0
    			&& document.getElementById("chinesename").value.length != 0
    			&& document.getElementById("email").value.length != 0
    			&& document.getElementById("job").value.length != 0
    			&& document.getElementById("employeeid").value.length != 0
    			){
    		var json = "{\"userInfo\":{\"username\":\""+document.getElementById("englishname").value
    			+"\",\"chinesename\":\""+document.getElementById("chinesename").value
    			+"\",\"email\":\""+document.getElementById("email").value
    			+"\",\"group\":\""+document.getElementById("group").value
    			+"\",\"employeeid\":"+document.getElementById("employeeid").value
    			+",\"job\":\""+document.getElementById("job").value+"\"}}";
    		var settings = {
      			  "async": true,
      			  "crossDomain": true,
      			  "url": "http://"+window.location.host+"/addUser.do",
      			  "method": "POST",
      			  "headers": {
      			    "content-type": "application/json",
      			    "cache-control": "no-cache",
      			    "postman-token": "587419ca-df25-f40b-93b4-099d0d2f6d4e"
      			  },
      			  "processData": false,
      			  "data": json
      			}
    		$.ajax(settings).done(function (response) {
    			  console.log(response);
    			});
    		alert("添加完成");
    	}else{
    		alert("请输入名字和邮箱");
    	}
    	
    } 
    function init(){
    	$.getJSON("http://"+window.location.host+"/queryUserInfo.do", function(data) {
			var str="";
			$.each(data.data, function(i, item){
				str+="<tr>"
					+"<td width=\"10%\"><div align=\"center\">"+item["id"]+"</div></td>"
					+"<td width=\"70%\"><div align=\"center\">"+item["chineseName"]+"</div></td>"
					+"<td width=\"50%\"><div align=\"center\">"+item["email"]+"</div></td>"
					+"<td width=\"10%\"><div align=\"center\">"+item["groupName"]+"</div></td>"
					+"<td width=\"10%\"><div align=\"center\">"+item["job"]+"</div></td>"
					+"<td width=\"10%\"><div align=\"center\">"+item["employeeId"]+"</div></td>"
				+"</tr>"
				});
			 document.getElementById("wjtable").innerHTML=str;
		});
    			
    }
</Script>
</head>
<body onload="init()">
	<center>
		<br> 增加用户
			<form name="form1" action="" method="post">
				<br>namePinyin：<input id="englishname" type="text" name="englishname" />
				<br>name：<input id="chinesename" type="text" name="chinesename" />
				<br>email：<input id="email" type="text" name="email" />
				<br>group：<input id="group" type="text" name="group" />
				<br>job：<input id="job" type="text" name="group" />
				<br>employeeId：<input id="employeeid" type="text" name="group" />
				
				<br> <input type="Button" value="增加用户" onClick="add()" />刷新后查看新增人员
			</form>
		当前员工信息如下
		<table id="wjtable"  width="50%" border="1">
			<tr>
				<th width="10%"><div align="center"><B>id</B></div></th>
				<th width="20%"><div align="center"><B>username</B></div></th>
				<th width="30%"><div align="center"><B>email</B></div></th>
				<th width="10%"><div align="center"><B>group</B></div></th>
				<th width="10%"><div align="center"><B>job</B></div></th>
				<th width="10%"><div align="center"><B>employeeId</B></div></th>
			</tr>
		</table>
	</center>
	<form name="form" action="" method="post">
		<input type="text" name="username" style="visibility: hidden;">
	</form>
	<br>
<script  src="jquery-1.11.0.min.js"></script>
</body>
</html>