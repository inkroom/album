package cn.inkroom.web.quartz.service;

import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.dao.ConfigDao;
import cn.inkroom.web.quartz.redis.JedisClientPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/15
 * @Time 23:50
 * @Descorption
 */
@Service
public class ConfigService {
    Log logger = LogFactory.getLog(getClass());
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private JedisClientPool pool;

//    private int interval = Constants.DEFAULT_REDIS_INIT_TIME;

    public ConfigService() {
//        initRedis();
//        Integer temp = getIntegerConfig(Constants.KEY_REDIS_INIT_INTERVAL);
//        if (temp != -1)
//            interval = temp;
//        pool.set(Constants.KEY_REDIS_INIT_INTERVAL, interval, interval + "");
    }

    /**
     * 初始化redis存储数据
     */
    public void initRedis() throws Exception {
//        Integer temp = null;
//        try {
//            logger.debug(" 开始 init");
//            temp = Integer.parseInt(configDao.getConfig(Constants.getCode(Constants.KEY_REDIS_INIT_INTERVAL)));
//            logger.debug("初始时间== " + temp);
//        } catch (NumberFormatException e) {
//            logger.error("   初始化 配置数据 失败，，默认数据更新时间不是数字。");
//            temp = Integer.MAX_VALUE;
//        }
//        pool.set(Constants.KEY_REDIS_INIT_INTERVAL, temp, String.valueOf(temp));
//        Map<String, Integer> map = Constants.getMap();
//        logger.debug("map 的内容是= " + map);
//        Iterator<String> stringIterator = map.keySet().iterator();
//        while (stringIterator.hasNext()) {
//            String key = stringIterator.next();
//            logger.debug(" 初始化配置  key =  " + key);
//            if (!key.equals(Constants.KEY_REDIS_INIT_INTERVAL)) {
//
//                logger.debug("  get 的结果 = " + pool.get(Constants.KEY_REDIS_INIT_INTERVAL)
////                        + "   class = " + pool.get(Constants.KEY_REDIS_INIT_INTERVAL).getClass()
//                                + "   insr  的结果 = " + pool.incr(Constants.KEY_REDIS_INIT_INTERVAL)
////                        + "   class = " + pool.get(Constants.KEY_REDIS_INIT_INTERVAL).getClass()
//                );
//                pool.set(key, temp, configDao.getConfig(map.get(key)));
//            }
//        }
//        logger.debug("  初始化完成  key = " + Constants.KEY_REDIS_DELETE_IP_INTERVAL + "   ttl = " + pool.ttl(Constants.KEY_REDIS_DELETE_IP_INTERVAL) + "  value = " + pool.get(Constants.KEY_REDIS_DELETE_IP_INTERVAL)
//                + "\n key = " + Constants.KEY_REDIS_IMAGE_403 + "   ttl = " + pool.ttl(Constants.KEY_REDIS_IMAGE_403) + "  value = " + pool.get(Constants.KEY_REDIS_IMAGE_403)
//                + "\n key = " + Constants.KEY_REDIS_UPLOAD_BASE_PATH + "   ttl = " + pool.ttl(Constants.KEY_REDIS_UPLOAD_BASE_PATH) + "  value = " + pool.get(Constants.KEY_REDIS_UPLOAD_BASE_PATH)
//                + "\n key = " + Constants.KEY_REDIS_IMAGE_404 + "   ttl = " + pool.ttl(Constants.KEY_REDIS_IMAGE_404) + "  value = " + pool.get(Constants.KEY_REDIS_IMAGE_404)
//                + "\n key = " + Constants.KEY_REDIS_IMAGE_406 + "   ttl = " + pool.ttl(Constants.KEY_REDIS_IMAGE_406) + "  value = " + pool.get(Constants.KEY_REDIS_IMAGE_406)
//                + "\n key = " + Constants.KEY_REDIS_INIT_INTERVAL + "   ttl = " + pool.ttl(Constants.KEY_REDIS_INIT_INTERVAL) + "  value = " + pool.get(Constants.KEY_REDIS_INIT_INTERVAL)
//                + "\n key = " + Constants.KEY_REDIS_VISIT_MAX_COUNT + "   ttl = " + pool.ttl(Constants.KEY_REDIS_VISIT_MAX_COUNT) + "  value = " + pool.get(Constants.KEY_REDIS_VISIT_MAX_COUNT)
//                + "\n key = " + Constants.KEY_REDIS_VISIT_MAX_TIME + "   ttl = " + pool.ttl(Constants.KEY_REDIS_VISIT_MAX_TIME) + "  value = " + pool.get(Constants.KEY_REDIS_VISIT_MAX_TIME)
//                + "\n key = " + Constants.KEY_REDIS_UPLOAD_MAX_LENGTH + "   ttl = " + pool.ttl(Constants.KEY_REDIS_UPLOAD_MAX_LENGTH) + "  value = " + pool.get(Constants.KEY_REDIS_UPLOAD_MAX_LENGTH)
//        );
    }

//    public String getFile(long id) {
//        return configDao.getFile(id);
//    }

    public String getStringConfig(String key) throws Exception {
        if ((pool.get(key)) == null) {
            logger.debug("开始init");
            initRedis();
        }
        return pool.get(key);
    }

    public Long getLongConfig(String key) throws Exception {
        if (pool.get(key) == null) {
            logger.debug("开始init");
            initRedis();
        } else {
            logger.debug("  不是空");
        }
        try {
            return Long.parseLong(pool.get(key));
        } catch (NumberFormatException e) {
            logger.error("the value that code is " + Constants.getCode(key) + " is not a number ");
            return -1L;
        }
    }

    public Integer getIntegerConfig(String key) throws Exception {
        if ((pool.get(key)) == null) {
            logger.debug("开始init");
            initRedis();
        }
        try {
            return Integer.parseInt(pool.get(key));
        } catch (NumberFormatException e) {
            logger.error("the value that code is " + Constants.getCode(key) + " is not a number ");
            return -1;
        }
    }
}
