package cn.inkroom.web.quartz.aop;

import cn.inkroom.web.quartz.redis.JedisClientPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/24
 * @Time 22:06
 * @Descorption
 */
public class RedisAop implements AfterReturningAdvice, MethodBeforeAdvice {
    Log logger = LogFactory.getLog(getClass());

    public void afterReturning(Object returnValue, Method method, Object[] args, Object obj) throws Throwable {
//        logger.debug("method = " + method);
        if (!method.getName().contains("getJedis")) {
            JedisClientPool pool = (JedisClientPool) obj;
//            logger.debug(" jedis 为 " + pool);
            if (pool != null && pool.jedis != null && pool.jedis.isConnected()) {
////                    logger.debug("关闭 jedis");
////                pool.j
                try {
                    pool.jedis.close();
                } catch (Exception e) {
                    logger.error(e.getCause());
                    logger.error(e.getMessage());
                }

            }
        }
    }

    public void before(Method method, Object[] objects, Object obj) throws Throwable {
//        logger.debug("method = " + method);
        if (!method.getName().contains("getJedis")) {
            JedisClientPool pool = (JedisClientPool) obj;
            if (pool != null) {
//                logger.debug(" before jedis 为 " + pool.jedis);
                pool.getJedis();
//                logger.debug("key 是 " + objects[0] + "   ttl = "
//                        + pool.ttl(objects[0].toString()) + "   exists = "
//                        + pool.exists(objects[0].toString()) + "   value="
//                        + pool.get(objects[0].toString())
//                );
            }
        }
    }
}
