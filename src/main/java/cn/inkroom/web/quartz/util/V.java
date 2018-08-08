package cn.inkroom.web.quartz.util;

import cn.inkroom.web.quartz.annotions.Valid;
import cn.inkroom.web.quartz.enums.Result;
import cn.inkroom.web.quartz.handler.MessageException;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/16
 * @Time 13:12
 * @Descorption
 */
public class V {
    public static void checkForm(Object value) throws Exception {
        Field fields[] = value.getClass().getDeclaredFields();
//        System.out.println(V.class + "  fields  " + fields.length);
        for (Field field : fields) {
            Valid v = field.getAnnotation(Valid.class);
//            System.out.println(V.class + "  Valid = " + v);
            if (v != null) {
                try {
                    field.setAccessible(true);
                    Object obj = field.get(value);
//                    System.out.println(V.class + "  value =  " + obj);
                    if (v.emptyAbled() && isEmpty(obj)) {
                        return;
                    }
                    if (!v.emptyAbled() && isEmpty(obj)) {
//                        System.out.println(V.class + "  null  " + obj.toString());
                        throw new MessageException("param.not.null", new String[]{v.name()}, Result.PARAM_NOT_SUIT);
//                        throw new Exception(v.name() + "不能为空");
                    }

                    if (obj.toString().trim().length() > v.maxLength() || obj.toString().trim().length() < v.minLength()) {
//                        System.out.println(V.class + "  长度  " + obj.toString());
                        throw new MessageException("param.not.length", new String[]{v.name()}, Result.PARAM_NOT_SUIT);
//                        throw new Exception("长度不匹配");
                    }
                    if (!regularVerification(v.regex(), obj.toString().trim())) {
//                        System.out.println(V.class + "  正则  " + obj.toString());
                        throw new MessageException(v.message()[0], Result.PARAM_NOT_SUIT);
//                        throw new Exception(v.message()[0]);
                    }
//                    System.out.println(V.class + "  测试通过");
                } catch (IllegalAccessException e) {
                    throw e;
                }
            }
        }
    }

    public static boolean isEmpty(Object value) {
        return value == null || value.toString().trim().equals("");
    }

    /**
     * 正则表达式验证
     *
     * @param regex 正则表达式
     * @param str   要验证的字符串
     * @return 匹配返回TRUE 否则返回FALSE
     */
    public static boolean regularVerification(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
