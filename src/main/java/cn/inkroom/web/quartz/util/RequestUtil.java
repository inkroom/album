package cn.inkroom.web.quartz.util;

import cn.inkroom.web.quartz.bean.AccountBean;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.entity.Size;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/15
 * @Time 21:26
 * @Descorption 登陆工具类，包括登陆成功，登陆失效，注销登陆
 */
public class RequestUtil {
    static Log logger = LogFactory.getLog(RequestUtil.class);

    public static void login(HttpServletRequest request, AccountBean account) {
        request.getSession().setAttribute(Constants.KEY_SESSION_LOGIN, account);
        ((Map) request.getSession().getServletContext().getAttribute(Constants.KEY_CONTEXT_SESSION))
                .put(account.getUsername(), request.getSession().getId());
        account.setSalt(null);
        account.setPassword(null);
    }

    public static void logout(HttpServletRequest request) {
        ((Map) request.getSession().getServletContext().getAttribute(Constants.KEY_CONTEXT_SESSION))
                .remove(((AccountBean) request.getSession().getAttribute(Constants.KEY_SESSION_LOGIN)).getUsername());
        request.getSession().invalidate();
    }

    public static void remove(HttpSession session) {
        ((Map) session.getServletContext().getAttribute(Constants.KEY_CONTEXT_SESSION))
                .remove(session.getAttribute(Constants.KEY_SESSION_LOGIN));
    }

    public static AccountBean getLogin(HttpServletRequest request) {
        return (AccountBean) request.getSession().getAttribute(Constants.KEY_SESSION_LOGIN);
    }

    public static boolean isExists(HttpServletRequest request) {
        boolean result = ((Map) request.getServletContext().getAttribute(Constants.KEY_CONTEXT_SESSION))
                .get(RequestUtil.getLogin(request).getUsername())
                .equals(request.getSession().getId());

        //获取存储的sessionId
        logger.debug(request.getServletContext().getAttribute(Constants.KEY_CONTEXT_SESSION));
        logger.debug(request.getSession().getId());
        return result;
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static Size getSize(HttpServletRequest request) {
        String width = request.getParameter("width");
        String height = request.getParameter("height");
        Size size;
        if (width == null || height == null) {
            return new Size(Constants.DEFAULT_SIZE_WIDTH, Constants.DEFAULT_SIZE_HEIGHT);
        } else {
            try {
                size = new Size();
                size.setWidth(Integer.parseInt(width));
                size.setHeight(Integer.parseInt(height));
            } catch (NumberFormatException e) {
                return new Size(Constants.DEFAULT_SIZE_WIDTH, Constants.DEFAULT_SIZE_HEIGHT);
            }
        }
        return size;
    }

    public static String getAnswer(HttpServletRequest request) {
        String answer = request.getParameter(Constants.KEY_PARAMETER_ANSWER);
        if (answer != null)
            return answer;
        answer = (String) request.getSession().getAttribute(Constants.KEY_SESSION_ANSWER);
        if (answer != null)
            return answer;
        return null;
    }

    public static String getIp(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
//            System.out.println("ip = "+request.getRemoteAddr()+"  host = "+request.getRemoteHost());
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}
