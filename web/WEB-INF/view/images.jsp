<%@ page import="cn.inkroom.web.quartz.config.Constants" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: 墨盒
  Date: 2017/8/16
  Time: 14:05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="path" value=""/>
<html>
<head>
    <%request.setAttribute("visitAccount", Constants.KEY_REQUEST_VISIT_ACCOUNT);%>
    <%request.setAttribute("visitAlbum", Constants.KEY_REQUEST_VISIT_ALBUM);%>
    <title>${requestScope.get(requestScope.get('visitAccount')).getNickname()}相册——${requestScope.get(requestScope.get('visitAlbum')).getName()}</title>

    <!-- <singleLink rel="shortcut icon" href="http://static.inkroom.cn/image/img/favicon.ico" type="image/x-icon"/> -->
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/style.css">
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/menu.css">
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/manager.css">
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/input.css">
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/upload.css">
    <style type="text/css">
        .image_item {
            margin: 10px 5px;
            display: inline-block;
        }

        .image_item > a {

        }

        .image_item > a > img {
            height: 150px;
        }

    </style>
</head>
<body>
<div class="wrap">
    <div class="wrapper">
        <jsp:include page="modules/head.jsp" flush="false"/>
        <%--<h1 style="text-align: center;margin: 0 auto">--%>
        <%--<%request.setAttribute("album", Constants.KEY_REQUEST_ALBUM);%>--%>
        <%--<a class="a_btn" href="javascript:self.location=document.referrer;">返回</a>--%>
        <%--欢迎来到墨盒的图片收藏站 ${requestScope.get(requestScope.get('album')).getSize()}(已用)--%>
        <%--&lt;%&ndash;<%request.setAttribute("login", Constants.KEY_SESSION_LOGIN); %>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<c:if test="${empty sessionScope.get(requestScope.get('login'))}">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<a class="a_btn" href="login">登陆</a>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
        <%--</h1>--%>
        <%request.setAttribute("quartz", Constants.KEY_REQUEST_IMAGES); %>
        <div class="image_group">
            <c:if test="${requestScope.get(requestScope.get('quartz')).size()==0}">
                <h1 style="text-align: center">暂无图片</h1>
            </c:if>
            <c:forEach var="item" items="${requestScope.get(requestScope.get('quartz'))}">
                <%--<div class="image_item">--%>
                <%--<a href="javascript:void(0);">--%>
                <%--<img lazy="common/${requestScope.get(requestScope.get('visitAccount')).getId()}/${requestScope.get(requestScope.get('visitAlbum')).getId()}/${item.getId()}/res?width=400&height=300"--%>
                <%--src="http://static.inkroom.cn/image/img/loading.jpg"--%>
                <%--layer-src="common/${requestScope.get(requestScope.get('visitAccount')).getId()}/${requestScope.get(requestScope.get('visitAlbum')).getId()}/${item.getId()}/res"/>--%>
                <%--</a>--%>
                <%--</div>--%>

                <div class="image_item">
                    <div class="image">
                        <img lazy="${path}/common/${requestScope.get(requestScope.get('visitAccount')).getId()}/${requestScope.get(requestScope.get('visitAlbum')).getId()}/${item.getId()}/res?width=400&height=300"
                             layer-src="${path}/common/${requestScope.get(requestScope.get('visitAccount')).getId()}/${requestScope.get(requestScope.get('visitAlbum')).getId()}/${item.getId()}/res"
                             src="http://static.inkroom.cn/image/img/loading.jpg" alt=""
                             title="创建于${item.getCreateTime()}">
                    </div>
                    <div class="name">${item.getCreateTime()}</div>
                </div>
            </c:forEach>
        </div>

    </div>
</div>
<div class="footer">@墨盒</div>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/jquery.min.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/index.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/menu.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/lib/webuploader/0.1.5/webuploader.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/images.js"></script>
<%request.setAttribute("isMe", Constants.KEY_REQUEST_IS_ME); %>
<c:if test="${requestScope.get(requestScope.get('isMe'))!=-1}">
    <script type="text/javascript" src="http://static.inkroom.cn/image/js/manager.js"></script>
    <script type="text/javascript" src="http://static.inkroom.cn/image/js/upload.js"></script>
    <script type="text/javascript">
        $(function () {
            loadImageMenu('${path}');
            loadImageManager('${path}/common/upload');
        });
    </script>
</c:if>
</body>
</html>
