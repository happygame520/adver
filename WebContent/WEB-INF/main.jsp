<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>101移动广告</title>

<!-- 这个主要用于退出登录又点击返回按钮时还能回到原界面的情况（虽然消除了session，但是浏览器会缓存页面） -->
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>

<%
	String path = request.getContextPath();//
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	//basePath=http://localhost:8080/TEST
%>

<style type="text/css">
*{font-family: "Helvetica Neue", Helvetica, Arial, "PingFang SC", "Hiragino Sans GB", "Heiti SC", "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;}
</style>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js">
</script>
<script>
	$(document).ready(function() {
		//
		$("#exitbutton").mouseenter(function() {
			$(this).css({"color" : "#FFA500"});
		});
		$("#exitbutton").mouseleave(function() {
			$(this).css({"color" : "#000"});
		});
		$("#exitbutton").click(function() {
			$.post("${pageContext.request.contextPath}/bghandle", 
					{"flag":"exit"}, function(data,status){
                if(status == "success" ){
                	window.location.href = "${pageContext.request.contextPath}/login";
                }  
	        }); 
			
		});
		//
		$("#admin1").click(function(){
			  $("#admin1_app").slideToggle();
			  $("#admin1_income").slideToggle();
		});
		$("#admin2").click(function(){
			  $("#admin2_recharge").slideToggle();
			  $("#admin2_recharge_history").slideToggle();
			  $("#admin2_code").slideToggle();
		});
		//
		$("#admin1_app").click(function(){
			var src = "${pageContext.request.contextPath}/application";
			$("#content").attr("src", src);
		});
		$("#admin1_income").click(function(){
			var src = "${pageContext.request.contextPath}/income";
			$("#content").attr("src", src);
		});
		$("#admin2_recharge").click(function(){
			alert("请联系客服充值");
		});
		$("#admin2_recharge_history").click(function(){
			var src = "${pageContext.request.contextPath}/rechargehistory";
			$("#content").attr("src", src);
		});
		$("#admin2_code").click(function(){
			var src = "${pageContext.request.contextPath}/code";
			$("#content").attr("src", src);
		});
		
	});

</script>
</head>
<body>
<div id="container" style="padding: 0%"> 
<br>

<div id="header" style="background-color:white ;color: #111;font-size: xx-large;padding: 0% ">
<font style="font-size: xx-large;margin-left: 0%;font-weight:bold; ">101</font>移动广告平台
<font style="font-size: large;margin-left: 55%;" >${name}  欢迎你!</font>
<font id="exitbutton" style="background-color:white;font-size: large;margin-left: 3%;cursor:pointer">退出</font>
</div>
<br>

<div id="menu" style="background-color:#FFF;width:20%;height:100%; float:left;padding: 0% ">
<div id="admin1" style="background-color:#008B8B;color:white;border:1px solid #00AAAA;padding : 3%;cursor:pointer " >应用数据管理</div>
<div id="admin1_app" style="background-color:#EEE;color:black;border:1px solid #DDD;padding: 3%;cursor:pointer " >应用管理</div>
<div id="admin1_income" style="background-color:#EEE;color:black;border:1px solid #DDD;padding: 3%;cursor:pointer " >推广数据</div>

<div id="admin2" style="background-color:#008B8B;color:white;border:1px solid #00AAAA;padding : 3%;cursor:pointer " >账户中心</div>
<div id="admin2_recharge" style="background-color:#EEE;color:black;border:1px solid #DDD;padding: 3%;cursor:pointer " >充值</div>
<div id="admin2_recharge_history" style="background-color:#EEE;color:black;border:1px solid #DDD;padding: 3%;cursor:pointer " >充值记录</div>
<div id="admin2_code" style="background-color:#EEE;color:black;border:1px solid #DDD;padding: 3%;cursor:pointer " >修改密码</div>
</div>
<!-- <div style="width: 1%; float: left;">&nbsp</div> -->

<!-- <div id="content_div" style="background-color:#EEE;float:left;width: 79%"> -->

<iframe id="content" src="<%=basePath%>/welcome.jsp" style="border: 0px;
        background-color:#FFF; width:79%;height:500px;margin-left: 1%;margin-top:-0.6%; float:left; " ></iframe> 
<!-- </div>  -->

<!-- <div id="footer" style="background-color:#FFA500;clear:both;text-align:center;">
版权 © runoob.com</div> -->

</div> 


</body>
</html>