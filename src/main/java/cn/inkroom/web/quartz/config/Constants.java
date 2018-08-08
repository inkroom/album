package cn.inkroom.web.quartz.config;

import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/12
 * @Time 21:51
 * @Descorption 静态常量
 */
public class Constants {
    //session域的key
    public static final String KEY_SESSION_LOGIN = "_KEY_SESSION_LOGIN_";//session中存储的登录key
    public static final String KEY_SESSION_QUESTION = "_KEY_SESSION_QUESTION_";//存放问题
    public static final String KEY_SESSION_ANSWER = "_KEY_SESSION_ANSWER_";//存放回答的答案
    //context域的key
    public static final String KEY_CONTEXT_SESSION = "_KEY_CONTEXT_SESSION_";//servletContext中存储的sessionId的map的key
    public static final String KEY_CONTEXT_PATH = "path";//基础路径
    //cookie的key
    public static final String KEY_COOKIES_STATUS = "_KEY_COOKIES_STATUS_";//自动登陆的cookie name
    //request域的key
    public static final String KEY_REQUEST_MESSAGE = "_KEY_REQUEST_MESSAGE_";//request中存储message
    public static final String KEY_REQUEST_ALBUM = "_KEY_REQUEST_ALBUM_";//存储单个相册
    public static final String KEY_REQUEST_IMAGES = "_KEY_REQUEST_IMAGES_";//存储相册里的所有图片
    public static final String KEY_REQUEST_ALBUMS = "_KEY_REQUEST_ALBUMS_";//存储所有相册
    public static final String KEY_REQUEST_IS_ME = "_KEY_REQUEST_IS_ME_";//是否是自己的相册
    public static final String KEY_REQUEST_VISIT_ACCOUNT = "_KEY_REQUEST_VISIT_ACCOUNT_";//要访问的相册的账号
    public static final String KEY_REQUEST_VISIT_ALBUM = "_KEY_REQUEST_VISIT_ALBUM_";//要访问的相册
    public static final String KEY_REQUEST_AUTHORITY = "_KEY_REQUEST_AUTHORITY_";//权限提示信息

    //redis的key，也是配置信息

    private static final String PRE_REDIS_KEY = "_IMAGE_-";


    public static final String KEY_REDIS_IMAGE_404 = PRE_REDIS_KEY + "_KEY_REDIS_FILE_404_";//redis中存储图片404的路径的key，key=404
    public static final String KEY_REDIS_IMAGE_403 = PRE_REDIS_KEY + "_KEY_REDIS_IMAGE_403_";//，key=403
    public static final String KEY_REDIS_IMAGE_406 = PRE_REDIS_KEY + "_KEY_REDIS_IMAGE_406_";//，key=40
    public static final String KEY_REDIS_DELETE_IP_INTERVAL = PRE_REDIS_KEY + "_KEY_REDIS_DELETE_IP_INTERVAL_";//清空数据库存储的ip的时间间隔，key=1
    public static final String KEY_REDIS_VISIT_MAX_COUNT = PRE_REDIS_KEY + "_KEY_REDIS_MAX_VISIT_COUNT_";//最高访问次数，key=2
    public static final String KEY_REDIS_INIT_INTERVAL = PRE_REDIS_KEY + "_KEY_REDIS_INIT_INTERVAL_";//redis存储数据的最长保存时间，key=4
    public static final String KEY_REDIS_VISIT_MAX_TIME = PRE_REDIS_KEY + "_KEY_REDIS_VISIT_MAX_TIME_";//限制访问的时间，单位毫秒，key=3
    public static final String KEY_REDIS_UPLOAD_BASE_PATH = PRE_REDIS_KEY + "_KEY_REDIS_UPLOAD_BASE_PATH_";//图片上传路径，key=5
    public static final String KEY_REDIS_UPLOAD_MAX_LENGTH = PRE_REDIS_KEY + "_KEY_REDIS_IMAGE_MAX_LENGTH_";//图片最大大小，key=6

    private static HashMap<String, Integer> map;

    //前后缀
    public static final String PREFIX_URL_COMMON = "/common/";
    public static final String PREFIX_IMAGE = "album";
    public static final String PREFIX_VIEW = "/WEB-INF/view/";
    public static final String SUFFIX_VIEW = ".jsp";


    //返回的json数据的key
    public static final String KEY_JSON_QUESTION = "question";//返回的json中的可以
    public static final String KEY_JSON_ALBUMS = "albums";
    public static final String KEY_JSON_ALBUM = "album";
    public static final String KEY_JSON_ACCOUNT_ID = "id";
    //获取前台数据的key
    public static final String KEY_PARAMETER_ANSWER = "answer";//获取参数的key
    //一些常用的url
//    public static final String URL_PREFIX_COMMON = "/common/";
    public static final String URL_404_ALL = PREFIX_VIEW + "/404" + SUFFIX_VIEW;
    public static final String URL_AUTHORITY_ALL = PREFIX_VIEW + "/403" + SUFFIX_VIEW;
    public static final String URL_MESSAGE = "/message";//直接指向jsp文件
    public static final String URL_MESSAGE_ALL = PREFIX_VIEW + URL_MESSAGE + SUFFIX_VIEW;
    public static final String URL_LOGIN = "/login";

    public static final String CHARSET = "utf-8";//字符集
    public static final String IMAGE_TYPE = "jpeg";
    public static final String RESPONSE_CONTENT_TYPE_JSON = "text/html;charset=" + CHARSET;

    public static final String RESPONSE_CONTENT_TYPE_IMAGE = "image/" + IMAGE_TYPE;

    public static final int DEFAULT_SIZE_WIDTH = 400;
    public static final int DEFAULT_SIZE_HEIGHT = 300;

    public static final int DEFAULT_REDIS_INIT_TIME = Integer.MAX_VALUE;

    private static boolean isShouldInit() {
        return map == null || map.size() < 9;
    }

    public static int getCode(String key) {
        if (isShouldInit()) {
            map = new HashMap<>();
            map.put(KEY_REDIS_IMAGE_404, 404);
            map.put(KEY_REDIS_IMAGE_403, 403);
            map.put(KEY_REDIS_IMAGE_406, 406);
            map.put(KEY_REDIS_DELETE_IP_INTERVAL, 1);
            map.put(KEY_REDIS_VISIT_MAX_COUNT, 2);
            map.put(KEY_REDIS_VISIT_MAX_TIME, 3);
            map.put(KEY_REDIS_INIT_INTERVAL, 4);
            map.put(KEY_REDIS_UPLOAD_BASE_PATH, 5);
            map.put(KEY_REDIS_UPLOAD_MAX_LENGTH, 6);
        }
        LogFactory.getLog(Constants.class).info("  map = " + map + "   key = " + key);
        return map.get(key);
    }

    public static Map<String, Integer> getMap() {
        if (isShouldInit())
            getCode("");
        return map;
    }

//    public static Integer[] getCodes() {
//        Iterator<String> stringIterator = map.keySet().iterator();
//        while (stringIterator.hasNext()) {
//        }
//        if (map == null || map.size() < 7)
//            getCode("");
//        Integer[] codes = new Integer[7];
//        return map.values().toArray(codes);
//    }
}
