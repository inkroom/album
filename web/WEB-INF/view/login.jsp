<%--
  Created by IntelliJ IDEA.
  User: 墨盒
  Date: 2017/3/6
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath }"/>--%>
<html>
<head>
    <title>登录</title>
    <%--<!-- <singleLink rel="shortcut icon" href="http://static.inkroom.cn/image/img/favicon.ico" type="image/x-icon"/> -->--%>
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/input.css"/>
    <meta name = "viewport" content="width=device-width,initial-scale=1.0 user-scalable = no , maximum-scale=1.0">
    <style type="text/css">
        .wrap .login_frame {
            width: 70%;
            /*display: inline-block;*/
            /*height: expression(this.parentNode.width);*/
            margin: 0 auto;
            padding: 10%; /*假装居中*/
        }

        .wrap .login_frame .input_row {
            /*display: inline;*/
            margin: 5% auto;
            width: 80%;
        }

        .wrap .login_frame .input_row h2 {
            font-family: "楷体";
            color: #cc2fc8;
            text-align: center;
        }

        .wrap .login_frame .input_row input {
            width: 100%;
            height: 30px;
            padding-left: 5px;
            border-radius: 7px;
        }

        .wrap .login_frame .input_row .diy_border {
            border: blueviolet 1px solid;
            border-radius: 0;
        }


    </style>
</head>
<body>
<div class="wrap">
    <div class="wrapper">
        <div class="login_frame">
            <div class="input_row">
                <h2>登录墨盒相册</h2>
            </div>
            <div class="input_row">
                <input class="diy_border" type="text" value="${requestScope.get('account')}" name="account"
                       placeholder="账号">
            </div>
            <div class="input_row">
                <input class="diy_border" type="password" value="${requestScope.get('password')}" name="password"
                       placeholder="密码">
            </div>
            <div class="input_row" style="margin-bottom: 15px;">
                <div class="btn not_select" style="display: block">登录</div>
            </div>
            <div class="input_row" style="text-align: right;margin-top: 15px">
                <label style="margin: 5%;">
                    <input type="checkbox" class="regular-checkbox" id="remember">
                    <label for="remember"></label>&nbsp;记住密码
                </label>
                <label style="margin: 5%;">
                    <input type="checkbox" class="regular-checkbox" id="auto">
                    <label for="auto"></label>&nbsp;自动登录
                </label>
            </div>
        </div>
        <!--<div style="height: 1000px;width: 10px;background: red"></div>-->
    </div>
</div>
<div class="footer">
    <jsp:include page="modules/foot.jsp" flush="false"/>
</div>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/jquery.min.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/index.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/lib/layer/2.4/layer.js"></script>
<%--<script type="text/javascript">--%>
    <%--&lt;%&ndash;引入移动端layer，css文件路径错误&ndash;%&gt;--%>
    <%--var script=document.createElement("script");--%>
    <%--script.setAttribute("type", "text/javascript");--%>
    <%--script.setAttribute("src", "http://static.inkroom.cn/image/mobile/lib/layer/layer.js");--%>
    <%--var heads = document.getElementsByTagName("head");--%>
    <%--if(heads.length)--%>
        <%--heads[0].appendChild(script);--%>
    <%--else--%>
        <%--document.documentElement.appendChild(script);--%>
    <%--//document.write('<script type=\"text/javascript\" src=\"http://static.inkroom.cn/image/mobile/lib/layer/layer.js\"/>');--%>
    <%--}--%>
    <%--//    }--%>
    <%--//    chage();--%>

<%--</script>--%>
<script type="text/javascript">
//    alert('版本+'+layer.v);
    $(function () {
        var $account = $('.input_row input[name=account]:eq(0)');
        var $password = $('.input_row input[name=password]:eq(0)');

        $('#remember').on('change', function () {//记住密码
            if (!$(this).is(':checked')) {
                $('#auto').prop('checked', false);
            }
        });
        $('#auto').on('change', function () {//自动登录
            if ($(this).is(':checked')) {
                $('#remember').prop('checked', true);
            }
        });

        $account.on('focus blur', function () {
            if ($(this).is(':focus')) {
                $(this).removeClass('error');
            } else {
                if ($(this).val() === '') {
                    $(this).addClass('error');
                }
            }
        });

        $password.on('focus blur', function () {
            if ($(this).is(':focus')) {
                $(this).removeClass('error');
            } else {
                if ($(this).val() === '') {
                    $(this).addClass('error');
                }
            }
        });

        $('.input_row input[type=password]').on('keydown', function (e) {
            if (e.keyCode === 13)//回车登录
                $('.input_row .btn').trigger('click');
        });
        $('.input_row .btn').click(function () {
            if ($account.val() !== '' && $password.val() !== '') {
                ajax({
                    shade:0.7,
                    url: 'login',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        account: $account.val(),
                        password: $password.val(),
                        remember: $('#remember').is(":checked"),
                        auto: $('#auto').is(':checked')
                    },
                    success: function (data) {
                        if (data.status === 0) {
                            if (window.location.href.indexOf('login') > -1 || window.location.href.indexOf('/') > -1)
                                window.location.href = "album/" + data.data.id + "/";
                            else
                                window.location.reload();
                        } else if(data.status === 1){
                            layer.open({
                                type: 0
                                , title: false
                                , icon: 2
                                , content: '账号或密码错误'
                                , btn: '知道了'
                                , btnAlign: 'c' //按钮居中
                                , shade: 0 //不显示遮罩
                                , yes: function () {
                                    layer.closeAll();
                                }
                            });
                        } else {
                            layer.open({
                                type: 0
                                , title: false
                                , icon: 2
                                , content: data.message
                                , btn: '知道了'
                                , btnAlign: 'c' //按钮居中
                                , shade: 0 //不显示遮罩
                                , yes: function () {
                                    layer.closeAll();
                                }
                            });
                        }
//                            $('.login_frame').find('.message').remove();
//                            $('.login_frame').append('<div class="input_row message">' + data.msg + '</div>');
                    },
                    error: function () {
                        layer.open({
                            type: 0
                            , title: '登录失败'
                            , icon: 2
                            , content: '登录失败，请联系系统管理员'
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
        <%
        if (Boolean.TRUE.equals(request.getAttribute("auto"))){
        %>
        $('#auto').prop('checked', true).trigger('change');
        <%}else if(Boolean.FALSE.equals(request.getAttribute("auto"))){%>
        $('#remember').prop('checked', true).trigger('change');
        <%}%>
    });
</script>
</body>
</html>