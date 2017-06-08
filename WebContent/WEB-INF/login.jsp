<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>101移动广告</title>

<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js">
</script>
<script>
	$(document).ready(function() {
		//
		var account = $("#account");
		account.val("账号");
		account.css({"color":"#BBB"});
		account.focus(function(){
			if($(this).val().trim() == "账号"){
				$(this).val('');
                $(this).css({"color":"#000"});
			} 
		});
		account.blur(function(){
			if($(this).val().trim() == ''){
				$(this).val('账号');
                $(this).css({"color":"#BBB"});
			} 
		});
		//
		var pass = $("#pass");
		pass.val("密码");
		pass.attr("type","text");
		pass.css({"color":"#BBB"});
		pass.focus(function(){
			if($(this).val().trim() == "密码"){
				$(this).attr("type","password");
				$(this).val('');
                $(this).css({"color":"#000"});
			} 
		});
		pass.blur(function(){
			if($(this).val().trim() == ''){
				$(this).attr("type","text");
				$(this).val('密码');
                $(this).css({"color":"#BBB"});
			} 
		});
		//
	    $("#btn").mouseenter(function() {
	    	$(this).css({"background-color":"#00AAAA"});
		});
	    $("#btn").mouseleave(function() {
	    	$(this).css({"background-color":"#00868B"});
		});
		
		//
 	    $("#btn").click(function() {
		    var account = $("#account").val().trim();
		    var password = $("#pass").val().trim();
		    var obj = {"account":account, "pass":password};
		    var myJSON = JSON.stringify(obj);
	    	$.post("${pageContext.request.contextPath}/loginhandle", myJSON, function(data,status){
                var dataObj = JSON.parse(data);
                if(dataObj.errMsg != "" ){
                	alert(""+dataObj.errMsg);

                } else{
                    //
                    window.location.href = "${pageContext.request.contextPath}/backstage";
                    
                } 
	        });
		}); 
		//
		

	});
</script>
</head>

<body style="background-image:url('images/bg.jpg');background-size:cover; 
      overflow: hidden">

	<div style="background-color: #FFF; width: 30%; border-radius: 20px; padding: 1%; 
		margin-left: 35%; margin-top: 8%;text-align: center; ">
	    <h2 style="color: #00868B">广告主登录</h2>
		<hr><br/>
		<input id="account"  type="text" name="account" onpaste="return false"
			    maxlength="20"
			    style="width:80%; font-size: medium;padding: 2% " /><br /> <br /> <br /> 
		<input id="pass" type="password" name="pass" onpaste="return false"
			    maxlength="15"
			    style="width:80%; font-size: medium;padding: 2% "/><br /><br />
	    <button id="btn" type="button" name="button" 
	            style="background-color:#00868B;border:0px;border-radius:3px; 
			    color:white; font-size:large;width:40%;
			    padding-bottom :2%;padding-top: 2%;margin-top:6%  " >登录
	    </button>
	
	</div>

</body>
</html>