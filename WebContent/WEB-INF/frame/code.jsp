<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>密码修改</title>
  <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
  <script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
  <script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
  <link rel="stylesheet" href="jqueryui/style.css">
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
input {
	width: 30%;
	padding: 1%;
	margin: 1%;
	font-size: medium;
}

span {
	font-size: small;
}
button {
    background-color: #006400;
    color: #FFF;
    border: 0px;
    border-radius: 5%;
    cursor:pointer;
    margin-left: 10%;
    font-size: medium;
    padding-left: 2%;
    padding-right: 2%;
    outline:none;
    
}
</style>
<script>
$(document).ready(function() {
	var pattern = /[^\a-\z\A-\Z0-9]/g;
    //
	$("#btn").click(function(){
	    var initcode = $("#init_code").val().trim();
	    var newcode1 = $("#new_code1").val().trim();
	    var newcode2 = $("#new_code2").val().trim();
	    if( initcode.length == 0 ){
		    alert("密码不能为空");
		    $("#init_code").focus();
            return false;
	    }
	    if( newcode1.length == 0 ){
		    alert("密码不能为空");
		    $("#new_code1").focus();
            return false;
	    }
	    if( newcode2.length == 0 ){
		    alert("密码不能为空");
		    $("#new_code2").focus();
            return false;
	    }
	    if(pattern.test(initcode)){
			alert("密码应为字母数字的组合，请重新输入");
			$("#init_code").focus();
			return false;
		}
	    if(pattern.test(newcode1)){
			alert("密码应为字母数字的组合，请重新输入");
			$("#new_code1").focus();
			return false;
		}
	    if(pattern.test(newcode2)){
			alert("密码应为字母数字的组合，请重新输入");
			$("#new_code2").focus();
			return false;
		}
	    if(newcode1 != newcode2){
			alert("两次输入的新密码不相同，请重新输入");
			$("#new_code1").focus();
			return false;
		}
		
		//如果不传入uid,appid等导致application无法通过request.getParameter方法获取参数值，当然也可以使用session将参数加入attribute中，我这里用的是前者，重定向时都传一下
		$.post("${pageContext.request.contextPath}/code", 
				{"flag" : "resetpassword", "initcode": initcode, "newcode1": newcode1 }, 
				function(data, status) {
			if (data == "success!") {
				alert("修改成功");
			} else {
				alert(data);
			}
		});
	});
    
});
</script>
</head>
<body>
	<div style="background-color: #008B8B; color: white; padding: 1%;">
		密码修改</div>
	<br/>
	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原密码：</span> <input id = "init_code" type="password" ><br/>
	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;新密码：</span> <input id = "new_code1" type="password" ><br/>
	<span>确认新密码：</span> <input id = "new_code2" type="password" style=""><br/>
	<br>
	<button id = "btn" >修改</button>
	
</body>
</html>