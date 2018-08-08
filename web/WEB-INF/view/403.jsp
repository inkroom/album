<%@ page import="cn.inkroom.web.quartz.config.Constants" %><%--
  User: 墨盒
  Date: 2017/8/16
  Time: 21:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>权限</title>
</head>
<body style="text-align: center;font-size: 40px">
权限不足
<%request.setAttribute("authority", Constants.KEY_REQUEST_AUTHORITY);%>
<div>${requestScope.get(requestScope.get('authority'))}</div>
</body>
</html>
