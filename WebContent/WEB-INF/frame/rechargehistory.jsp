<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值记录</title>
<!-- <script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script> -->
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
table {
	border-collapse: collapse;
	width: 100%;
}

table,td,th {
	border: 1px solid #BBB;
	padding: 1%;
	text-align: center;
	font-size: small;
	color: black;
}

th {
	background-color: #DDD
}

#btn {
	background-color: #4682B4;
	color: #FFF;
	border: 0px;
	border-radius: 10%;
	cursor: pointer;
}
</style>
<script>
	$(document).ready(function() {
		//
		$(function() {
			$(".datepicker").datepicker(
	                {
	                    gotoCurrent: true,
	                    yearRange: 'c-10:c',
	                    prevText: '上个月',
	                    nextText: '下个月',
	                    showMonthAfterYear: true,
	                    dateFormat: 'yy-mm-dd',
	                    changeMonth: true,
	                    changeYear: true,
	                    dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
	                    currentText: "today",
	                    monthNamesShort: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
	                    /* showWeek:true, */
	                    calculateWeek: this.iso8601Week
	                });

		});
		//
		function load() {
			var date_start = $(datepicker1).val();
			var date_end = $(datepicker2).val();
			$.post("${pageContext.request.contextPath}/rechargehistory",
					 {"flag" : "queryrechargehistory", "date_start":date_start,"date_end":date_end },
					  function(data, status) {
				if (status == "success") {
					var array = JSON.parse(data);
					$.each(array, function(index, content){
						var row ="<tr> <td>" +content.date + "</ td> <td>" + Math.round(content.amount*100)/100
						+ "</ td> <td>"+ content.status +"</ td> </tr>";  
						$(rechargehistory_table).append(row);
				    });

				} 
			});
		}
		//
        load();
        //
        $("#btn").click(function(){
        	var date_start = $(datepicker1).val();
			var date_end = $(datepicker2).val();
			location.href = "${pageContext.request.contextPath}/rechargehistory?date_start="+date_start+"&date_end="+date_end;
		});
		//
	});
</script>
</head>
<body>
	<div style="background-color: #008B8B; color: white; padding: 1%;">
		充值记录</div>
	<br>
	<div style="font-size: small; ">
	开始日期：<input type="text"  class="datepicker" id="datepicker1" value="<%=request.getAttribute("date_start")%>" >
	&nbsp;&nbsp;结束日期：<input type="text" class="datepicker" id="datepicker2" value="<%=request.getAttribute("date_end")%>" >
	&nbsp;<button id="btn">查询</button>
	</div>
	<br>
	<table id="rechargehistory_table" border="1">
		<tr id ="table_head">
			<th style="width: 40%">汇款日期</th>
			<th style="width: 30%">汇款金额</th>
			<th style="width: 30%">状态</th>
		</tr>

	</table>
</body>
</html>