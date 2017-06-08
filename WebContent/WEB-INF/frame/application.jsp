<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用管理</title>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%
	if (session == null) {
%>
<script>
	alert("登录超时，请重新登录");
	parent.location.href = '${pageContext.request.contextPath}/login';
</script>
<%
	}
%>
<%
	Object accountObj = session.getAttribute("account");
	if (accountObj == null) {
%>
<script>
	alert("登录超时，请重新登录");
	parent.location.href = '${pageContext.request.contextPath}/login';
</script>
<%
	}
%>
<style type="text/css">
*{font-family: "Helvetica Neue", Helvetica, Arial, "PingFang SC", "Hiragino Sans GB", "Heiti SC", "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;}
</style>
<style type="text/css">
table {
	border-collapse: collapse;
	width:100%;
}

table,td,th {
	border: 1px solid #BBB;
	padding:1%;
	text-align: center;
	font-size: small;
}
th {
    background-color: #EEE
}
button {
    background-color: #4682B4;
    color: #FFF;
    border: 0px;
    border-radius: 10%;
    cursor:pointer;
    
}
</style>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js">
</script>
<script>
	$(document).ready(function() {
		//
		function load() {
			$.post("${pageContext.request.contextPath}/application", {"flag" : "load"}, function(data, status) {
				if (status == "success") {
					var array = JSON.parse(data);
					$.each(array, function(index, content){
						var row ="<tr> <td id='appname'>" +content.appname+ "</ td> <td id='uid'>" +content.uid
						+ "</ td> <td id='appid'>"+content.appid+"</ td> <td>"+content.appkey
						+"</ td> <td id='appstatus'>"+content.appstatus+"</ td> <td><button onclick='set(this)' >设置</button></ td> </tr>";  
						$(app_table).append(row);
				    });
				}
			});
		}
		//
        load();
        //
	});
	//
	function set(btn){
        var appname = $(btn).parents("tr").find("#appname").text();
        var uid = $(btn).parents("tr").find("#uid").text();
        var appid = $(btn).parents("tr").find("#appid").text();
        var appstatus = $(btn).parents("tr").find("#appstatus").text();
        
        location.href = "${pageContext.request.contextPath}/application?flag=set&appname="+appname+"&uid="+uid+"&appid="+appid+"&appstatus="+appstatus;

    }
</script>

</head>
<body>
	<div style="background-color: #008B8B; color: white; padding: 1%;">
		应用管理</div>
	<br>
	<table id="app_table" border="1" >
		<tr>
			<th>应用</th>
			<th>uid</th>
			<th>appid</th>
			<th>appkey</th>
			<th>推广状态</th>
			<th>操作</th>
		</tr>

	</table>

</body>
</html>