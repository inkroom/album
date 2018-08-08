<%--
  User: 墨盒
  Date: 2017/8/16
  Time: 11:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!--Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE html>
<html>
<head>
    <title>404</title>
    <%--<link rel="stylesheet" href="css/style.css">--%>

    <!-- <singleLink href='//fonts.googleapis.com/css?family=Josefin+Sans:400,100,100italic,300,300italic,400italic,600,600italic,700,700italic' rel='stylesheet' type='text/css'> -->

    <!-- For-Mobile-Apps-and-Meta-Tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords"
          content="Error Page Widget Responsive, Login Form Web Template, Flat Pricing Tables, Flat Drop-Downs, Sign-Up Web Templates, Flat Web Templates, Login Sign-up Responsive Web Template, Smartphone Compatible Web Template, Free Web Designs for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design"/>
    <%--<script type="application/x-javascript"> addEventListener("load", function () {--%>
        <%--setTimeout(hideURLbar, 0);--%>
    <%--}, false);--%>
    <%--function hideURLbar() {--%>
        <%--window.scrollTo(0, 1);--%>
    <%--} </script>--%>
    <style type="text/css">
        body {
            padding: 0;
            margin: 0;
            background: #FFF;
            font-family: 'Open Sans', sans-serif;
        }

        body a {
            transition: 0.5s all;
            -webkit-transition: 0.5s all;
            -moz-transition: 0.5s all;
            -o-transition: 0.5s all;
            -ms-transition: 0.5s all;
            text-decoration: none;
        }

        body a:hover {
            text-decoration: none;
        }

        body a:focus, a:hover {
            text-decoration: none;
        }

        input[type="button"], input[type="submit"] {
            transition: 0.5s all;
            -webkit-transition: 0.5s all;
            -moz-transition: 0.5s all;
            -o-transition: 0.5s all;
            -ms-transition: 0.5s all;
        }

        h1, h2, h3, h4, h5, h6 {
            margin: 0;
            padding: 0;
            text-align: center;
            font-family: 'Raleway', sans-serif;
        }

        p {
            margin: 0;
        }

        ul {
            margin: 0;
            padding: 0;
        }

        label {
            margin: 0;
        }

        a:focus, a:hover {
            text-decoration: none;
            outline: none
        }

        img {
            width: 100%;
        }

        .clear {
            clear: both;
        }

        /*-- //Reset Code --*/
        .main {
            background: url('http://static.inkroom.cn/image/img/4.png') no-repeat 0 0;
            background-size: cover;
            min-height: 799px;
        }

        .text {
            width: 50%;
            margin: 0 auto;
            padding-top: 330px;
        }

        .left {
            float: left;
            width: 49%;

        }

        .left h2 {
            font-size: 140px;
            color: #000;

        }

        .right {
            float: left;
            width: 46%;
            padding-left: 4%;
            border-left: 1px solid #000;
        }

        .right h3 {
            font-size: 40px;
            padding-bottom: 10px;
            text-align: left;
        }

        .right h4 {
            font-size: 20px;
            text-align: left;
            width: 88%;
        }

        .right img {
            width: 13%;
            padding-left: 10px;
        }

        .right a {
            color: #000;
            border: 2px solid #000;
            padding: 8px 34px 8px 15px;
            display: inline-block;
            margin-top: 10px;
            font-size: 16px;
            background: url('http://static.inkroom.cn/image/img/arrow.png') no-repeat 97px 10px;
            background-size: 10%;
        }

        .right a:hover {
            color: #b76b24;
            border: 2px solid #b76b24;
            background: url('http://static.inkroom.cn/image/img/carrow.png') no-repeat 97px 10px;
            background-size: 10%;
        }

        .footer {
            padding-top: 264px;
            width: 100%;
            text-align: center;
        }

        .footer p {
            color: #000;
            font-size: 16px;
        }

        .footer a {
            color: #000;
        }

        .footer a:hover {
            color: #fff;
        }

        @media (max-width: 1600px) {
            .text {
                width: 50%;
                margin: 0 auto;
                padding-top: 340px;
            }
        }

        @media (max-width: 1440px) {
            .text {
                padding-top: 300px;
            }
        }

        @media (max-width: 1366px) {
            .text {
                padding-top: 275px;
            }

            .left h2 {
                font-size: 130px;
            }

            .right h4 {
                font-size: 19px;
            }
        }

        @media (max-width: 1280px) {
            .text {
                padding-top: 270px;
            }

            .left h2 {
                font-size: 120px;
                padding-left: 20px;
            }

        }

        @media (max-width: 1080px) {
            .main {
                min-height: 598px;
            }

            .text {
                padding-top: 218px;
            }

            .left h2 {
                font-size: 110px;
                padding-top: 10px;
            }

            .right h4 {
                font-size: 16px;
            }

            .footer {
                padding-top: 188px;
            }
        }

        @media (max-width: 1024px) {
            .main {

                min-height: 674px;
            }

            .text {
                padding-top: 207px;
            }

            .left {
                float: left;
                width: 55%;
            }

            /*-- agileits --*/
            .left h2 {
                font-size: 105px;
                padding-left: 70px;
            }

            .right {
                float: left;
                width: 35%;
                padding-left: 4%;
                border-left: 1px solid #000;
            }

            .right h3 {
                font-size: 38px;
            }

            .right a {
                font-size: 14px;
            }

            .footer {
                padding-top: 170px;
            }
        }

        @media (max-width: 991px) {
            .left {
                width: 59%;
            }

            .right {
                width: 32%;
                padding-left: 3%;
            }

            .right h4 {
                font-size: 15px;
                width: 100%;
            }
        }

        @media (max-width: 900px) {
            .main {
                min-height: 600px;
            }

            .text {
                padding-top: 180px;
            }

            .left {
                width: 55%;
            }

            .left h2 {
                font-size: 100px;
                padding: 15px 0 0 53px;

            }

            /*-- w3layouts--*/
            .right {
                width: 41%;
            }

            .right h4 {
                width: 97%;
            }
        }

        @media (max-width: 800px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/4.png') no-repeat -46px 0;
                background-size: cover;
            }
        }

        @media (max-width: 768px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/4.png') no-repeat -354px 0;
                min-height: 929px;
                background-size: cover;
            }

            .text {
                width: 65%;
                padding-top: 325px;
            }

            .left {
                width: 50%;
            }

            .left h2 {
                font-size: 100px;
                padding: 15px 0 0 0;
            }

            .right {
                width: 46%;
            }

            .footer {
                padding-top: 250px;
            }
        }

        @media (max-width: 736px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/4.png') no-repeat -215px 0;
                min-height: 715px;
                background-size: cover;
            }

            .text {
                width: 65%;
                padding-top: 241px;
            }

            .footer {
                padding-top: 200px;
                /*-- agileits --*/
            }
        }

        @media (max-width: 667px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/4.png') no-repeat -143px 0;
                min-height: 586px;
                background-size: cover;
            }

            .text {
                width: 64%;
                padding-top: 180px;
            }

            .right h4 {
                width: 93%;
            }

            .footer {
                padding-top: 168px;
            }
        }

        @media (max-width: 600px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/4.png') no-repeat -175px 0;
                min-height: 586px;
                background-size: cover;
            }
        }

        @media (max-width: 568px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/4.png') no-repeat -184px 0;
                min-height: 565px;
                background-size: cover;
            }

            .text {
                width: 65%;
                padding-top: 174px;
            }

            .footer {
                padding-top: 100px;
            }

            .footer p {
                color: #000;
                font-size: 14px;
                line-height: 25px;
            }
        }

        @media (max-width: 480px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/4.png') no-repeat -238px 0;
                min-height: 600px;
                /*-- w3layouts --*/
                background-size: cover;
            }

            .text {
                width: 83%;
                padding-top: 182px;
            }

            .footer {
                padding-top: 132px;
            }
        }

        @media (max-width: 414px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/5.png') no-repeat -83px -79px;
                background-size: 123%;
                min-height: 672px;
            }

            .text {
                width: 100%;
                padding-top: 165px;
            }

            .left {
                width: 100%;
                float: none;
            }

            .left h2 {
                font-size: 100px;
                padding: 17px 0 0 0;
            }

            .right {
                width: 39%;
                float: none;
                border: none;
                padding-left: 0;
                margin: 0 auto;
            }

            .right h4 {
                width: 100%;
            }

            .footer {
                padding-top: 100px;
            }
        }

        @media (max-width: 384px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/5.png') no-repeat -81px -79px;
                background-size: 128%;
                min-height: 672px;
            }
        }

        @media (max-width: 375px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/5.png') no-repeat -97px -129px;
                min-height: 603px;
                background-size: 136%;
            }

            .text {
                width: 100%;
                padding-top: 127px;
            }

            .left {
                width: 100%;
                float: none;
            }

            .left h2 {
                font-size: 100px;
                padding: 0 0 0 0;
            }

            .right {
                width: 39%;
                float: none;
                border: none;
                padding: 0;
                margin: 0 auto;
            }

            .right h4 {
                width: 100%;
            }

            .footer {
                padding-top: 113px;
            }
        }

        @media (max-width: 320px) {
            .main {
                background: url('http://static.inkroom.cn/image/img/5.png') no-repeat -87px -110px;
                background-size: 136%;
                min-height: 503px;
            }

            .text {
                width: 100%;
                padding-top: 92px;
            }

            .left h2 {
                font-size: 85px;
                padding: 0 0 0 0;
            }

            .right {
                width: 50%;
                text-align: center;
            }

            .right h4 {
                text-align: center;
            }

            .footer {
                padding-top: 106px;
            }
        }
    </style>
</head>
<body>
<div class="main">
    <div class="text">
        <div class="left">
            <h2>404</h2>
        </div>
        <div class="right">
            <h3><s:message code="page.404.title"/></h3>
            <h4><s:message code="page.404.information"/></h4>
            <a href="javascript:self.location=document.referrer;"><s:message code="page.404.handler"/> </a>
        </div>
        <div class="clear"></div>
    </div>
    <div class="footer">
        <p>
            <jsp:include page="modules/foot.jsp"/>
        </p>
    </div>
</div>
</body>
</html>