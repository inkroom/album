<%@ page import="cn.inkroom.web.quartz.config.Constants" %><%--
  User: 墨盒
  Date: 2017/8/12
  Time: 22:38
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- <singleLink rel="shortcut icon" href="http://static.inkroom.cn/image/img/favicon.ico" type="image/x-icon"/> -->
    <title>提示</title>
    <meta name = "viewport" content="width=device-width,initial-scale=1.0 user-scalable = no , maximum-scale=1.0">
</head>
<body>
<%request.setAttribute("key", Constants.KEY_REQUEST_MESSAGE);%>
<h2 style="text-align: center">${requestScope.get(requestScope.get('key'))}</h2>
</body>
</html>
