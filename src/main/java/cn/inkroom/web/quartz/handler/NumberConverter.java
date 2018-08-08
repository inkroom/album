package cn.inkroom.web.quartz.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/10/19
 * @Time 11:24
 * @Descorption
 */
public class NumberConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String s) {
        Log log = LogFactory.getLog(getClass());
        try {
            log.info(" 元字符串是  " + s);
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            throw e;
        }
    }
}
