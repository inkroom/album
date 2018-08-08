<%@ page import="cn.inkroom.web.quartz.config.Constants" %>
<%@ page import="cn.inkroom.web.quartz.util.RequestUtil" %>
<%--
  User: 墨盒
  Date: 2017/9/1
  Time: 23:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("account", Constants.KEY_REQUEST_VISIT_ACCOUNT);
%>
<h1 style="text-align: center;margin: 0 auto">
    <a class="a_btn" href="javascript:self.location=document.referrer;">返回</a>
    欢迎来到${requestScope.get(requestScope.get('account')).getNickname()}的图片收藏站
    <%=(RequestUtil.getLogin(request) == null ? "<a class=\"a_btn\" href=\"/login\">登陆</a>" : "<div class=\"btn\" id=\"logout\">注销</div>") %>
</h1>
<script type="text/javascript">
    window.onload = function () {
        $('#logout').on('click', function () {
            ajax({
                url: 'logout',
                dataType: 'json',
                success: function () {
                    location.href = "login";
                }
            });
        });
    }
</script>
