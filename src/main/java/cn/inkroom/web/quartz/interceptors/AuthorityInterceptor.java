package cn.inkroom.web.quartz.interceptors;

import cn.inkroom.web.quartz.annotions.Authority;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.enums.Result;
import cn.inkroom.web.quartz.handler.MessageException;
import cn.inkroom.web.quartz.util.RequestUtil;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/9/2
 * @Time 21:19
 * @Descorption
 */
public class AuthorityInterceptor extends BaseInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (o instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) o;
            Authority a = method.getMethodAnnotation(Authority.class);

            if (a != null) {//需要权限拦截
                logger.info("获取的权限 = " + a.type());
                boolean isAjax = RequestUtil.isAjax(request);
                if (RequestUtil.getLogin(request) != null) {//已登录
                    //记录的sessionId与本机不同，说明已在异地二次登录
                    if (!RequestUtil.isExists(request)) {
                        //删除登录session记录
                        RequestUtil.logout(request);

                        logger.debug("   二次登录了");
                        if (isAjax) {
                            throw new MessageException("request.login.exists", Result.LOGIN_EXISTS);
                        }
                        request.getRequestDispatcher(Constants.URL_LOGIN).forward(request, response);
                        return false;
                    }
                } else {//未登录
                    if (isAjax)
                        throw new MessageException("request.login.not", Result.LOGIN_NOT);
                    request.getRequestDispatcher(Constants.URL_LOGIN).forward(request, response);
                    return false;
                }
                if (a.type() == Authority.Type.LOGIN) {

                }
                if (a.type() == Authority.Type.ME) {//访问自己
                    if (a.open()) {
                        logger.debug(request.getRequestURL() + "   空指针  " + request.getAttribute(Constants.KEY_REQUEST_IS_ME));
                        Long isMe = (Long) request.getAttribute(Constants.KEY_REQUEST_IS_ME);
                        if (isMe != null && isMe != -1L) {//是自己登陆，有权限
                            return true;
                        }
                    } else
                        return true;

                }
                throw new MessageException("request.album.authority");
            }
        }
        return true;
    }
}
