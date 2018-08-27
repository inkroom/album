<%@ page import="cn.inkroom.web.quartz.config.Constants" %><%--
  Created by IntelliJ IDEA.
  User: 墨盒
  Date: 2017/3/9
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>相册</title>
    <!-- <singleLink rel="shortcut icon" href="http://static.inkroom.cn/image/img/favicon.ico" type="image/x-icon"/> -->
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/input.css"/>
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/manager.css"/>
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/upload.css"/>
    <meta name = "viewport" content="width=device-width,initial-scale=1.0 user-scalable = no , maximum-scale=1.0">
    <style type="text/css">
        .wrapper {
            padding-top: 5%;
        }

        .header {
            background: #6fbfff;
            overflow: hidden;
            text-align: right;
        }

        .header a {
            /*float: right;*/
        }
    </style>

</head>
<body>
<%--<div class="content">--%>
<div class="wrap">
    <div class="header"><%request.setAttribute("login", Constants.KEY_SESSION_LOGIN);%>
        <c:set var="account" target="cn.inkroom.web.quartz.bean.AccountBean"
               value="${sessionScope.get(requestScope.get('login'))}"/>
        <span class="size">${account.getSize()}(已用)/${account.getCapacity()}(共)</span>
        <span>${sessionScope.get(requestScope.get('login'))}</span>
        <a href="javascript:void(0);" class="a_btn"
           onclick="$.ajax({url:'logout',success:function() {window.location.href='index'}})">注销</a>
        <%--<div style="clear: right"></div>--%>
    </div>
    <div class="wrapper"></div>
</div>

<%--<div style="width: 100%;height: 20px;background: black"></div>--%>
<div class="footer">@墨盒</div>
<%--</div>--%>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/jquery.min.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/upload.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/manager.js"></script>
<script type="text/javascript" src="http://cdn.staticfile.org/webuploader/0.1.0/webuploader.min.js"></script>
<script type="text/javascript">
    $(function () {
        ajax({
            url: 'init',
            type: 'get',
            dataType: 'json',
            success: function (status) {
                if (status.status === 0) {
                    var manager = new ImageManager({
                        url: 'upload',
                        owner: '.wrapper',
                        dirs: status.dirs,
                        defaultCover: 'static\\img\\loading.jpg',
                        prefix: 'img',
                        onRemove: function (dirs) {
//                                    console.LogSupport(dirs.maxLength);
                            if (dirs.length > 0 && confirm('确认删除相册？')) {
                                var names = [];
                                for (var i = 0; i < dirs.length; i++) {
                                    names.push({
                                        order: dirs[i].order,
                                        name: dirs[i].name
                                    });
                                }
//                                        console.LogSupport(names);
                                ajax({
                                    url: 'removeDir',
                                    type: 'post',
                                    data: {
                                        paths: JSON.stringify(names)
                                    },
                                    dataType: 'json',
                                    success: function (status) {
                                        if (status.status === 0) {
                                            var dirs = manager.getDirs();
                                            for (var i = status.dirs.length - 1; i >= 0; i--) {
                                                if (status.dirs[i].name === dirs[status.dirs[i].order].name) {
                                                    manager.remove(status.dirs[i].order);
                                                }
                                            }
                                        }
                                    },
                                    error: function () {

                                    }
                                });
                            }
//                                    return confirm('确认删除相册？');
                        },
                        enter: function (dir) {
                            if (dir.count > 0) {
                                window.location.href = "index?dir=" + dir.name;
                            }
                            console.log(dir);
                        },
                        onFinish: function (dir) {
                            layer.open({
                                type: 0
                                , title: false
                                , icon: 1
                                , content: '上传完成'
                                , btn: '知道了'
                                , btnAlign: 'c' //按钮居中
                                , shade: 0 //不显示遮罩
                                , yes: function () {
                                    layer.closeAll();
                                }
                            });
                        },
                        onCreateDir: function (dirName) {
                            ajax({
                                url: dirName + '/createDir',
                                type: 'get',
                                dataType: 'json',
                                success: function (status) {
                                    if (status.status === 0) {
                                        manager.append({
                                            name: dirName,
                                            count: 0,
                                            cover: 'http://static.inkroom.cn/image/img/no.jpg'
                                        });
                                    }
                                },
                                error: function () {
                                    layer.open({
                                        type: 0
                                        , title: false
                                        , icon: 2
                                        , content: '网络错误'
                                        , btn: '知道了'
                                        , btnAlign: 'c' //按钮居中
                                        , shade: 0 //不显示遮罩
                                        , yes: function () {
                                            layer.closeAll();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                else if (status.status === 1) {

                }

            },
            error: function () {
                layer.open({
                    type: 0
                    , title: false
                    , icon: 2
                    , content: '网络错误'
                    , btn: '知道了'
                    , btnAlign: 'c' //按钮居中
                    , shade: 0 //不显示遮罩
                    , yes: function () {
                        layer.closeAll();
                    }
                });
            }
        });


    })
    ;


    //            manger.append({
    //                cover: '../http://static.inkroom.cn/image/img/10.jpg',
    //                name: '测试append',
    //                count: '300'
    //            });
    //
    //            console.LogSupport(manger.getDirs());
</script>
</body>
</html>
