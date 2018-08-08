package cn.inkroom.web.quartz.listeners;

import cn.inkroom.web.quartz.util.RequestUtil;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/12
 * @Time 21:40
 * @Descorption
 */
public class SessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent event) {
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        //session过期，登陆自动失效。从context里去除session
        RequestUtil.remove(event.getSession());
//        ((Map) event.getSession().getServletContext().getAttribute(Constants.KEY_CONTEXT_SESSION))
//                .remove(event.getSession().getAttribute(Constants.KEY_SESSION_LOGIN));

    }
}
