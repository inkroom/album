package cn.inkroom.web.quartz.annotions;

import java.lang.annotation.*;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/16
 * @Time 13:12
 * @Descorption
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Valid {
    String regex() default ".*";

    int minLength() default 0;
    int maxLength() default 10;

    String name() default "";//前台name

    boolean emptyAbled() default false;

    String[] message() default "";
}
