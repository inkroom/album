import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.redis.JedisClientPool;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class Test {
//    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-redis.xml");
//        JedisClientPool pool = context.getBean(JedisClientPool.class);
//        try {
//            System.out.println(pool.get(Constants.KEY_REDIS_UPLOAD_BASE_PATH));
//
//            pool.set(Constants.KEY_REDIS_UPLOAD_BASE_PATH, "/home/project/upload/image/");
//
//
//            System.out.println(pool.get(Constants.KEY_REDIS_UPLOAD_BASE_PATH));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @org.junit.Test
    public void testURIEncode() {
        try {
            URI uri = new URI("https://image-1252774288.cos.ap-chengdu.myqcloud.com/album/壁纸/壁纸282.jpg");


            System.out.println(uri.getPath());


            System.out.println(uri.getHost());
            StringBuilder builder = new StringBuilder("https://" + uri.getHost());

            String s[] = uri.getPath().split("/");

            for (int i = 0; i < s.length; i++) {

                builder.append("/").append(URLEncoder.encode(s[i], "utf-8"));
            }

            System.out.println(builder.toString().replace("//","/"));

            System.out.println(URLEncoder.encode(uri.getPath(), "utf-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
