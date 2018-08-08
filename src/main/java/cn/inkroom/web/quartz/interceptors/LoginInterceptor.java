package cn.inkroom.web.quartz.interceptors;

import cn.inkroom.web.quartz.bean.AccountBean;
import cn.inkroom.web.quartz.config.Constants;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.inkroom.web.quartz.handler.MessageException;

public class LoginInterceptor extends BaseInterceptor {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute(Constants.KEY_SESSION_LOGIN) == null) {//未登录
            throw new MessageException("request.login.not");
//            if (RequestUtil.isAjax(request)) {
//                response.getWriter().write(new AjaxBean(Result.LOGIN_NOT).toString());
//                response.getWriter().close();
//                return false;
//            }
//            request.getRequestDispatcher("login").forward(request, response);
//            return false;
        } else {//已登录
            //记录的sessionId与本机不同，说明已在异地二次登录
            if (!((Map) request.getServletContext().getAttribute(Constants.KEY_CONTEXT_SESSION))
                    .get(((AccountBean) request.getSession().getAttribute(Constants.KEY_SESSION_LOGIN)).getUsername())
                    .equals(request.getSession().getId())) {
                logger.debug("   二次登录了");
                throw new MessageException("request.login.exists");
//                if (RequestUtil.isAjax(request))
//                    ResponseUtil.outJson(response, new AjaxBean(Result.LOGIN_EXISTS, messageConfig.getLoginExists()).toString());
//                else {
//                    request.setAttribute(Constants.KEY_REQUEST_MESSAGE, messageConfig.getLoginExists());
//                    request.getRequestDispatcher(Constants.URL_MESSAGE).forward(request, response);
//                }
//                return false;
            }
        }
        return true;
    }


}
