package cn.inkroom.web.quartz.handler;

import cn.inkroom.web.quartz.bean.AjaxBean;
import cn.inkroom.web.quartz.enums.Result;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.util.RequestUtil;
import cn.inkroom.web.quartz.util.ResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/16
 * @Time 16:48
 * @Descorption
 */
public class ExceptionHandler extends SimpleMappingExceptionResolver {
    private Log logger = LogFactory.getLog(getClass());

    private String defaultKey = "request.common.information";

    private String message;

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        logger.debug("  o = " + o.getClass() + "   exception = " + e);
//        e.printStackTrace();
        RequestContext requestContext = new RequestContext(httpServletRequest);
        MessageException messageException = null;
//        try {
        if (e instanceof MessageException) {
            messageException = (MessageException) e;
            if (messageException.getException() != null) {
                messageException.getException().printStackTrace();
                logger.error(messageException.getException());
            }
            message = requestContext.getMessage(messageException.getMessage());
            if (messageException.getOther() != null) {//添加额外信息
                String[] other = messageException.getOther();
                for (int i = 0; i < other.length; i++) {
                    message = message.replaceAll("\\[" + i + "\\]", other[i]);
                }
            }
        } else {
            e.printStackTrace();
            message = requestContext.getMessage(defaultKey);
        }
//        } catch (NoSuchMessageException e1) {
//            message = e.getMessage();
//        }
        if (o instanceof HandlerMethod) {
            if (RequestUtil.isAjax(httpServletRequest)) {
                try {
                    ResponseUtil.outJson(httpServletResponse,
                            new AjaxBean(messageException == null ?
                                    Result.EXCEPTION : messageException.getResult(), message).toString());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                return null;
            } else {
                ModelAndView modelAndView = new ModelAndView(Constants.URL_MESSAGE);
                modelAndView.addObject(Constants.KEY_REQUEST_MESSAGE, message + "\n<br/>   " + e.getMessage() + "    " + e);
                return modelAndView;
            }
        }
        return null;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
