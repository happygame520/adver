<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用设置</title>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%
    String appname = request.getParameter("appname");
    String appstatus = request.getParameter("appstatus");
    String uid = request.getParameter("uid");
    String appid = request.getParameter("appid");
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
button {
    background-color: #006400;
    color: #FFF;
    border: 0px;
    border-radius: 5%;
    cursor:pointer;
    margin-left: 3%;
    font-size: large;
    padding-left: 2%;
    padding-right: 2%;
    outline:none;
    
}
</style>

<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js">
</script>
<script>
	$(document).ready(function() {
		//
		var appstatus = '<%=appstatus %>';
		//alert("appstatus="+appstatus);
		if(appstatus == '开'){
			$("#open").attr("checked", true);
		} else {
			$("#close").attr("checked", true);
		}
		//
		$("#save").click(function(){
			var appstatus = $("input[name='status']:checked").val();
			//alert(appstatus);
			//如果不传入uid,appid等导致application无法通过request.getParameter方法获取参数值，当然也可以使用session将参数加入attribute中，我这里用的是前者，重定向时都传一下
			$.post("${pageContext.request.contextPath}/application", {"flag" : "save", "appstatus2": appstatus, "uid":'<%=uid%>', "appid":'<%=appid%>'}, function(data, status) {
				if (data == "success!") {
					alert("修改成功");
				}
			});
		});
		
	});
</script>
</head>

<body>
<div style="background-color: #008B8B; color: white; padding: 1%;">
		应用设置</div>
	<br>
	<div style="height: 100px" >
	  <span style="margin-left: 3%" >应用名称:&nbsp&nbsp&nbsp&nbsp&nbsp<%=appname %></span><br><br>
	  <span style="margin-left: 3%" >推广状态:&nbsp&nbsp&nbsp&nbsp</span>
	  <input id="open" type="radio" name="status" value="开">开
      <input id="close" type="radio" name="status" value="关">关
	</div>
    <br>
	<button id="save">保存</button>
</body>
</html>