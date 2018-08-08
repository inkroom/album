<%--
  User: 墨盒
  Date: 2017/9/11
  Time: 9:47
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <!-- <singleLink rel="shortcut icon" href="http://static.inkroom.cn/image/img/favicon.ico" type="image/x-icon"/> -->
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/style.css">
    <link rel="stylesheet" type="text/css" href="http://static.inkroom.cn/image/css/input.css"/>
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
                <h2>注册墨盒相册</h2>
            </div>
            <div class="input_row">
                <input class="diy_border" type="text" name="nickname"
                       placeholder="昵称">
            </div>
            <div class="input_row">
                <input class="diy_border" type="text" name="username"
                       placeholder="用户名">
            </div>
            <div class="input_row">
                <input class="diy_border" type="password" name="password"
                       placeholder="密码">
            </div>
            <div class="input_row">
                <input class="diy_border" type="password" name="pardon"
                       placeholder="重复密码">
            </div>
            <div class="input_row" style="margin-bottom: 15px;">
                <div class="btn not_select" style="display: block">注册</div>
            </div>
        </div>
        <!--<div style="height: 1000px;width: 10px;background: red"></div>-->
    </div>
</div>
<div class="footer">
    <jsp:include page="modules/foot.jsp" flush="false"/>
</div>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/jquery.min.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="http://static.inkroom.cn/image/js/index.js"></script>
<script type="text/javascript">
    $(function () {
        $('.btn').on('click', function () {
            var $nickname = $('input[name=nickname]');
            var $username = $('input[name=username]');
            var $password = $('input[name=password]');
            var $pardon = $('input[name=pardon]');
            if ($nickname.val() === '') {
//                layer.tipTop('');
                show('nick');
                return;
            }
            if ($username.val() === '') {
                show('username')
                return;
            }
            if ($password.val() === '') {
                show('password')
                return;
            }
            if ($pardon.val() === '') {
                show('pardon')
                return;
            }
            if ($pardon.val() !== $password.val()) {
                show('eq');
                return;
            }
            ajax({
                url: '',
                type: 'post',
                data: {
                    nickname: $nickname.val(),
                    username: $username.val(),
                    password: $password.val(),
                    pardon: $pardon.val()
                },
                success: function (result) {
                    if (result.status === 0) {
                        show('注册成功', '前往相册', function () {
                            window.location.href = "album/" + result.data.id + "/";
                        })
                    }
                }
            })
        })

    })
</script>
</body>
</html>
