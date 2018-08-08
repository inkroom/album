package cn.inkroom.web.quartz.controller;

import cn.inkroom.web.quartz.util.V;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/15
 * @Time 21:08
 * @Descorption
 */
public class BaseController {
    Log logger = LogFactory.getLog(getClass());

    /**
     * 检测表单
     *
     * @param value 表单
     * @return 如果不通过返回false
     */
    protected void checkForm(Object value) throws Exception {
        V.checkForm(value);
//        } catch (Exception e) {
//            e.printStackTrace();
//            message = e.getMessage();
//            return false;
//        }
//        return true;
    }
}
