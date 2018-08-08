package cn.inkroom.web.quartz.interceptors;

import cn.inkroom.web.quartz.bean.VisitBean;
import cn.inkroom.web.quartz.handler.MessageException;
import cn.inkroom.web.quartz.service.ConfigService;
import cn.inkroom.web.quartz.service.IpService;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/21
 * @Time 21:35
 * @Descorption
 */
public class IpInterceptor extends BaseInterceptor {
    @Autowired
    private IpService ipService;
    @Autowired
    private ConfigService configService;
    private int maxCount;
    private long sec;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

    logger.info("contextPath = "+request.getServletContext().getContextPath());
//        logger.debug(" url = " + request.getRequestURL() + "    method = " + o.getClass());
//        boolean isNeedCheck = false;
//        if (o instanceof ResourceHttpRequestHandler) {//静态资源请求
////            response.setContentType(Constants.RESPONSE_CONTENT_TYPE_JSON);
//            if (request.getHeader("Referer") == null)//直接访问静态资源
//                isNeedCheck = true;
//        } else if (o instanceof HandlerMethod) {//action
//            isNeedCheck = true;
//        }
////        logger.debug("ip拦截器 now = " + now.getTime() + "   startTime=" + visit.getStartTime().getTime()
////                + "   redis = " + configService.getLongConfig(Constants.KEY_REDIS_VISIT_MAX_TIME)
////                + "   visit count = " + visit.getCount() + "   redis = " + configService.getLongConfig(Constants.KEY_REDIS_VISIT_MAX_COUNT));
////        if (UrlUtil.isResource(request.getRequestURI())
////                && request.getHeader("Referer") != null) {//从页面发出的静态资源请求
////
////        } else if (UrlUtil.isResource(request.getRequestURI())
////                && request.getHeader("Referer") == null) {//直接访问静态资源
////            isNeedCheck = true;
////        } else {//访问action
////            isNeedCheck = true;
////        }
//        if (isNeedCheck) {
//            VisitBean visit = ipService.updateIp(RequestUtil.getIp(request));
//            logger.debug(visit);
//            Date now = new Date();
//            logger.debug("时间  " + (now.getTime() - visit.getStartTime().getTime()) + "   config = "
//                    + configService.getLongConfig(Constants.KEY_REDIS_VISIT_MAX_TIME) + "    count = " + visit.getCount()
//                    + "   config = " + configService.getLongConfig(Constants.KEY_REDIS_VISIT_MAX_COUNT));
//            /*
//             * 在限制时间内，访问次数大于最高次数，则限制访问
//             */
//            if ((now.getTime() - visit.getStartTime().getTime()) <
//                    configService.getLongConfig(Constants.KEY_REDIS_VISIT_MAX_TIME)//在限制时间内
//                    && visit.getCount() >
//                    configService.getLongConfig(Constants.KEY_REDIS_VISIT_MAX_COUNT)) {//不允许访问
//                logger.debug("  访问频率过高  ");
//                throw new MessageException("request.visit.rate");
////                request.setAttribute(Constants.KEY_REQUEST_MESSAGE, "访问频率过高");
////                request.getRequestDispatcher(Constants.URL_MESSAGE_ALL).forward(request, response);
////                return false;
//
//            } else {
//                ipService.updateCount(RequestUtil.getIp(request), now);
//                return true;
//            }
//        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        logger.debug("   访问频率过高   exception = " + (e == null ? "null" : e.getMessage()));
    }
}
