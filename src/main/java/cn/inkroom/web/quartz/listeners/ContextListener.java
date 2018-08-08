package cn.inkroom.web.quartz.listeners;

import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.redis.JedisClientPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/12
 * @Time 21:44
 * @Descorption
 */
public class ContextListener implements ServletContextListener {
    Log logger = LogFactory.getLog(getClass());

    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute(Constants.KEY_CONTEXT_PATH, event.getServletContext().getContextPath());
        //key 是账号username，value是session Id
        HashMap<String, Object> sessions = new HashMap<String, Object>();
        event.getServletContext().setAttribute(Constants.KEY_CONTEXT_SESSION, sessions);
    }

    public void contextDestroyed(ServletContextEvent event) {
        //获取bean
//        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
//        JedisClientPool pool = applicationContext.getBean(JedisClientPool.class);
//        //清空redis
////        pool.set(Constants.KEY_REDIS_INIT_INTERVAL, temp, String.valueOf(temp));
//        Map<String, Integer> map = Constants.getMap();
//        map.forEach(new BiConsumer<String, Integer>() {
//            @Override
//            public void accept(String s, Integer integer) {
//                try {
//                    pool.del(s);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    logger.error(e);
//                }
//            }
//        });
////        map.forEach((String s, Integer i) -> {
//            try {
//                pool.del(s);
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error(e);
//            }
//        });
//        Iterator<String> stringIterator = map.keySet().iterator();
//        while (stringIterator.hasNext()) {
//            String key = stringIterator.next();
//
////            logger.debug(" 初始化配置  key =  " + key);
////            if (!key.equals(Constants.KEY_REDIS_INIT_INTERVAL)) {
////
////                pool.set(key, temp, configDao.getConfig(map.get(key)));
////            }
//        }
    }
}
