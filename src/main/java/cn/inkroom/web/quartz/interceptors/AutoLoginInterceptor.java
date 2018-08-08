package cn.inkroom.web.quartz.interceptors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.inkroom.web.quartz.bean.AccountBean;
import cn.inkroom.web.quartz.handler.MessageException;
import cn.inkroom.web.quartz.service.AccountService;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.util.CookieUtil;
import cn.inkroom.web.quartz.util.EncryptUtil;
import cn.inkroom.web.quartz.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class AutoLoginInterceptor extends BaseInterceptor {

    @Autowired
    private AccountService accountService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getSession().getAttribute(Constants.KEY_SESSION_LOGIN) == null) {//未登录
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(Constants.KEY_COOKIES_STATUS)) {
                        try {
                            CookieUtil cookieUtil = new CookieUtil(cookie.getValue());
                            AccountBean accountBean = null;
//                            logger.info(cookieUtil);
                            if (!cookieUtil.isAuto()
                                    || (accountBean = this.accountService.login(EncryptUtil.xorInfo(EncryptUtil.urlDecode( cookieUtil.getAccount())),
                                    EncryptUtil.xorInfo(EncryptUtil.urlDecode(cookieUtil.getPassword())), false)) == null)
                                break;
                            RequestUtil.login(request, accountBean);
                        } catch (Exception e) {
                            throw new MessageException("request.common.information", e);
                        }
                    }
                }
            }
        }
        return true;
    }

}
