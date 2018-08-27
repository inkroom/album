<%@ page import="cn.inkroom.web.quartz.config.Constants" trimDirectiveWhitespaces="true" %>
<%--
  Created by IntelliJ IDEA.
  User: 墨盒
  Date: 2017/3/2
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value=""/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%
        request.setAttribute("account", Constants.KEY_REQUEST_VISIT_ACCOUNT);
    %>
    <title>${requestScope.get(requestScope.get('account')).getNickname()}相册</title>
    <meta name = "viewport" content="width=device-width,initial-scale=1.0 user-scalable = no , maximum-scale=1.0">
    <style type="text/css">
        #form {
            margin: auto;
        }

        #form td {
            padding: 8px 0;
        }

        #form .col-left {
            vertical-align: top;
            text-align: right;
            width: 30%;
        }

        #form .col-right {
        }

        #form .col-right input, textarea {
            width: 180px;
            resize: vertical;
        }

        #form .col-right > span {
            margin-left: 10px;;
            vertical-align: top;
        }
    </style>
    <!-- <singleLink rel="shortcut icon" href="http://static.inkroom.cn/image/img/favicon.ico" type="image/x-icon"/> -->
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/menu.css"/>
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/input.css">
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/upload.css">
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/manager.css">
</head>
<body>
<div class="wrap">
    <div class="wrapper">
        <%--<h1 style="text-align: center;margin: 0 auto">--%>
        <%--欢迎来到${requestScope.get(requestScope.get('account')).getNickname()}的图片收藏站--%>
        <jsp:include page="modules/head.jsp" flush="true"/>
        <%--<%request.setAttribute("login", Constants.KEY_SESSION_LOGIN); %>--%>
        <%--<c:if test="${empty sessionScope.get(requestScope.get('login'))}">--%>
        <%--<a class="a_btn" href="login">登陆</a>--%>
        <%--</c:if>--%>
        <%--</h1>--%>
        <div class="image_group" style="display: block">
            <%
                request.setAttribute("albums", Constants.KEY_REQUEST_ALBUMS);
                request.setAttribute("isMe", Constants.KEY_REQUEST_IS_ME);
            %>
            <c:if test="${requestScope.get(requestScope.get('albums')).size()==0}">
                <h1 id="none" style="text-align: center">暂无相册</h1>
            </c:if>
            <c:forEach items="${requestScope.get(requestScope.get('albums'))}" var="item">
                <c:choose>
                    <c:when test="${item.getAuthority()==0}">
                        <div class="image_item" onclick="location.href='${item.getId()}/'">
                            <div class="image">
                                <c:if test="${item.getNumber() eq 0}">
                                    <img lazy="http://static.inkroom.cn/image/img/no.jpg"
                                         src="http://static.inkroom.cn/image/img/no.jpg" alt=""
                                         title="${item.getContent()}(共${item.getNumber()}张)"/>
                                </c:if>
                                <c:if test="${(item.getNumber() > 0)}">
                                    <img lazy="${path}/common/${item.getOwner()}/${item.getId()}/${item.getCover()}/res?width=400&height=300"
                                         src="http://static.inkroom.cn/image/img/loading.jpg" alt=""
                                         title="${item.getContent()}(共${item.getNumber()}张)"/>
                                </c:if>
                                <span class="count not_select">${item.getNumber()}</span>
                            </div>
                            <div class="name">${item.getName()}</div>
                        </div>
                    </c:when>
                    <c:when test="${item.getAuthority()==1&& requestScope.get(requestScope.get('isMe'))!=-1}">
                        <div class="image_item" onclick="location.href='${item.getId()}/'">
                            <div class="image">
                                <img lazy="${path}/common/${item.getOwner()}/${item.getId()}/${item.getCover()}/res?width=400&height=300"
                                     src="http://static.inkroom.cn/image/img/loading.jpg" alt=""
                                     title="${item.getContent()}(共${item.getNumber()}张)"/>
                                <span class="count not_select">${item.getNumber()}</span>
                            </div>
                            <div class="name">${item.getName()}</div>
                        </div>
                    </c:when>
                    <%--不是访问自己则需要回答问题--%>
                    <c:when test="${item.getAuthority()>1}">
                        <c:set var="click"
                               value="onclick=\"answerQuestion(${item.getId()},${item.getAuthority()})\""/>
                        <c:if test="${requestScope.get(requestScope.get('isMe'))!=-1}">
                            <c:set var="click" value="onclick=\"location.href='${item.getId()}/'\""/>
                        </c:if>
                        <div class="image_item" ${click}>
                            <div class="image">
                                <img lazy="${path}/common/${item.getOwner()}/${item.getId()}/${item.getCover()}/res?width=400&height=300"
                                     src="http://static.inkroom.cn/image/img/loading.jpg" alt=""
                                     title="${item.getContent()}(共${item.getNumber()}张)"/>
                                <span class="count not_select">${item.getNumber()}</span>
                            </div>
                            <div class="name">${item.getName()}</div>
                        </div>
                    </c:when>
                </c:choose>
                <%--<c:if test="${item.getAuthority()==0 && requestScope.get(requestScope.get('isMe'))}">--%>
                <%----%>
                <%--</c:if>--%>
                <%--&lt;%&ndash;<c:if test="${!requestScope.get(requestScope.get('isMe'))}">&ndash;%&gt;--%>
                <%--<div class="item" onclick="location.href='${item.getId()}/quartz'">--%>
                <%--<div class="image">--%>
                <%--<img lazy="common/${item.getCover()}/res?width=400&height=300"--%>
                <%--src="http://static.inkroom.cn/image/img/loading.jpg" alt=""/>--%>
                <%--<span class="count not_select">${item.getNumber()}</span>--%>
                <%--</div>--%>
                <%--<div class="name">${item.getName()}</div>--%>
                <%--</div>--%>
                <%--</c:if>--%>
            </c:forEach>
        </div>
    </div>
</div>
<div class="footer">
    <jsp:include page="modules/foot.jsp" />
</div>
<%--<div id="createTable" style="display: none">--%>
<%--<table id="form">--%>
<%--<tr>--%>
<%--<td class="col-left">相册名称：</td>--%>
<%--<td class="col-right"><input type="text" name="name" maxlength="30"><span><span--%>
<%--class="count">0</span>/30</span></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td class="col-left">相册描述：</td>--%>
<%--<td class="col-right"><textarea name="content" maxlength="200"></textarea><span><span class="count">0</span>/200</span>--%>
<%--</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td class="col-left">分类：</td>--%>
<%--<td class="col-right">--%>
<%--<select name="type" id="">--%>
<%--<option value="1">类别1</option>--%>
<%--<option value="2">类别2</option>--%>
<%--</select>--%>
<%--</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td class="col-left">权限：</td>--%>
<%--<td class="col-right">--%>
<%--<select name="authority" id="">--%>
<%--<option value="0">所有人可见</option>--%>
<%--<option value="1">仅自己可见</option>--%>
<%--<option value="2">回答问题可见</option>--%>
<%--</select>--%>
<%--</td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--<div style="text-align: center;margin-bottom: 8px">--%>
<%--<input class="btn" type="button" value="确定"/>--%>
<%--</div>--%>
<%--</div>--%>

<script type="text/javascript" src="http://static.inkroom.cn/image/js/jquery.min.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/index.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/album.js"></script>
<%--<script src="http://jq22.qiniudn.com/masonry-docs.min.js"></script>--%>
<%request.setAttribute("isMe", Constants.KEY_REQUEST_IS_ME);%>
<c:if test="${requestScope.get(requestScope.get('isMe'))!=-1}">
    <script type="text/javascript" src="http://static.inkroom.cn/image/lib/webuploader/0.1.5/webuploader.min.js"></script>
    <script type="text/javascript" src="http://static.inkroom.cn/image/js/menu.js"></script>
    <script type="text/javascript" src="http://static.inkroom.cn/image/js/manager.js"></script>
    <script type="text/javascript" src="http://static.inkroom.cn/image/js/upload.js"></script>
    <script type="text/javascript">
        $(function () {
            loadAlbumMenu('${path}');
            loadAlbumManager('${path}/common/upload');
            loadCreate();
        });
    </script>
</c:if>
<c:if test="${!empty param.get('id')}">
    <script type="text/javascript">
        $(function () {
            var $items = $('.image_item');
            for (var i = 0; i < $items.length; i++) {
                var id = getId($items.eq(i).find('img').attr('src'), 1);
                if (id ===${param.get('id')}) {
                    $items.eq(i).trigger('click');
                }
            }
        });
    </script>
</c:if>
</body>
</html>