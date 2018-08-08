package cn.inkroom.web.quartz.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class JedisClientPool {
    Log logger = LogFactory.getLog(getClass());

    public Jedis jedis;


    @Autowired
    private JedisPool jedisPool;

    public String set(String key, String value) throws Exception {
//        if (jedis != null && jedis.isConnected()) {
//        Jedis jedis = null;
        try {
//            jedis = getJedis();
            String temp = jedis.set(key, value);
            logger.debug("set(String key, String value)  " + temp.getClass());
            return temp;
        } catch (ClassCastException e) {
            logger.error(e);
        }
//        finally {
//            if (jedis != null)
//                jedis.close();
//        }
        logger.debug("   set(String key, String value)   报异常了，");

//        }
        return null;

//        jedis.close();
//        return result;
    }

    public String set(String key, int seconds, String value) throws Exception {
//        Jedis jedis = null;
        try {
//            jedis = getJedis();
            logger.debug("   set(String key, int seconds, String value)  " + jedis.setex(key, seconds, value).getClass());
        } catch (ClassCastException e) {
            logger.error(e);
        }
//        finally {
//            if (jedis != null)
//                jedis.close();
//        }
        logger.debug("   set(String key, int seconds, String value)  报异常了，");

//        }
        return null;
    }


    public String get(String key) throws Exception {
//        if (jedis != null && jedis.isConnected()) {


//        }
//        Jedis jedis = null;
        try {
//            jedis = getJedis();
//            logger.debug("   get  " + jedis.get(key));
//            logger.debug("exists  class = " + jedis.exists(key).getClass());
            return jedis.get(key);
        } catch (ClassCastException e) {
            logger.error(e);
        }
//        finally {
//            if (jedis != null)
//                jedis.close();
//        }
        logger.debug("    get(String key)  报异常了，");
        return null;

//        jedis.close();
//        return result;
    }

    public Boolean exists(String key) throws Exception {
//        if (jedis != null && jedis.isConnected()) {
//        Jedis jedis = null;
        try {
//            jedis = getJedis();
            logger.debug("exists  class = " + jedis.exists(key).getClass());
            return jedis.exists(key);
        } catch (ClassCastException e) {
            logger.error(e);
        }
//        finally {
//            if (jedis != null)
//                jedis.close();
//        }
        return false;
//        }
//        return null;

//        jedis.close();
//        return result;
    }

//    public Long expire(String key, int seconds) throws Exception {
////        if (jedis != null && jedis.isConnected()) {
//            return jedis.expire(key, seconds);
////        }
////        return null;
//
////        jedis.close();
////        return result;
//    }

    public void push(String key, String... values) throws Exception {
//        Jedis jedis = getJedis();
//        if (jedis != null && jedis.isConnected()) {
        logger.debug("   铺设 后的返回值 = " + jedis.lpush(key, values));
//        jedis.close();
//        }
    }

    public List<String> pop(String key) throws Exception {
//        Jedis jedis = getJedis();
        if (jedis != null && jedis.isConnected()) {
            logger.debug("   brpop  开始");
            List<String> result = jedis.brpop(0, key);
            logger.debug("  结束的结果 =  " + result);
            jedis.close();
            return result;
        }
//        jedis.close();
        return null;
    }

    public Long ttl(String key) throws Exception {
//        if (jedis != null && jedis.isConnected()) {
//        Jedis jedis = null;
        try {
//            jedis = getJedis();
            Object obj = jedis.ttl(key);
            logger.debug("   ttl   value = " + obj + "    class = " + obj.getClass());
            return jedis.ttl(key);
        } catch (ClassCastException e) {
            logger.error(e);
        }
//        finally {
//            if (jedis != null)
//                jedis.close();
//        }
        return -1L;

//        }
//        return null;

//        jedis.close();
//        return result;
    }

    public Long incr(String key) throws Exception {
//        if (jedis != null && jedis.isConnected()) {
//        Jedis jedis = null;
        try {
//            jedis = getJedis();
            return jedis.incr(key);
        } catch (ClassCastException e) {
            logger.error(e);
        }
//        finally {
//            if (jedis != null)
//                jedis.close();
//        }
        return -1L;
//        }
//        return null;
//        jedis.close();
//        return result;
    }

    public Long hset(String key, String field, String value) throws Exception {
//        if (jedis != null && jedis.isConnected()) {
//        Long result = jedis.hset(key, field, value);
//        jedis.close();
//        Jedis jedis = getJedis();
        Long result = jedis.hset(key, field, value);
//        jedis.close();
        return result;
//        }
//        return null;
    }

    public String hget(String key, String field) throws Exception {
//        if (jedis != null && jedis.isConnected()) {
//            String result = jedis.hget(key, field);
//            jedis.close();
//        Jedis jedis = getJedis();
        return jedis.hget(key, field);
//        } else {
//            logger.error(" jedis  null");
//        }
//        return null;
    }

    public Long hdel(String key, String... field) throws Exception {
//        if (jedis != null && jedis.isConnected()) {
//            Long result = jedis.hdel(key, field);
//            jedis.close();
//        Jedis jedis = getJedis();
        return jedis.hdel(key, field);
//        } else {
//            logger.error(" jedis  null");
//        }
//        return null;
    }

    public Boolean del(String key) throws Exception {
//        if (jedis != null && jedis.isConnected()) {
//        Jedis jedis = getJedis();
        Long result = jedis.del(key);
//        jedis.close();
        return true;
//        } else {
//            logger.error(" jedis  null");
//        }
//        return false;
    }

    public Jedis getJedis() throws Exception {
        logger.debug("   初始化之前   " + (jedis == null ? "为空" : (jedis.isConnected() + "")));
//        if (jedis == null || jedis.isConnected()) {
        try {
//            jedisPool.returnBrokenResource(jedis);
            jedis = jedisPool.getResource();
//                logger.debug("   初始化之后   " + (jedis == null ? "为空" : (jedis.isConnected() + "")));
//                jedis.close();
//                logger.debug("   关闭之后   " + (jedis == null ? "为空" : (jedis.isConnected() + "")));
//                jedis = jedisPool.getResource();
            return jedis;
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
//                logger.debug(" finally close this ");
//            if (jedis != null)
//                jedis.close();
        }
//        }
        return null;
    }
}
