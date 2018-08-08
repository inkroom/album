package cn.inkroom.web.quartz.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 实现自定义contextPath
 */
public class ContextPathAop implements MethodInterceptor {
    private String contextPath;
    Log logger = LogFactory.getLog(getClass());

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        logger.info(methodInvocation.getMethod().toString());
        logger.info(methodInvocation.getThis().toString());
        if (contextPath != null)
            return contextPath;
        return methodInvocation.proceed();
    }
}
