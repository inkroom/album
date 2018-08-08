package cn.inkroom.web.quartz.annotions;

import java.lang.annotation.*;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/9/2
 * @Time 21:26
 * @Descorption 权限
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Authority {
    Type type() default Type.LOGIN;


    enum Type {
        LOGIN,//需要登录
        ME//自己访问
    }
}
