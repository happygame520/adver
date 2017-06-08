<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head>
    <title>页面错误</title>
    <!-- 3秒钟后自动跳转回首页 -->
    <meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath}/login">
  </head>
  <body>
    <h2>页面错误</h2>
    3秒钟后自动跳转回首页，如果没有跳转，请点击<a href="${pageContext.request.contextPath}/login">这里</a>
  </body>
</html>