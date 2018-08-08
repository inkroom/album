package cn.inkroom.web.quartz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/12
 * @Time 21:01
 * @Descorption cookie工具类
 */
public class CookieUtil {
    private boolean isAuto;
    private String account;
    private String password;

    public CookieUtil(String value) {
        Pattern pattern = Pattern.compile("auto=(.*)&account");
        Matcher matcher = pattern.matcher(value);
        try {
            if (matcher.find())
                setAuto(Boolean.parseBoolean(matcher.group(1)));
            pattern = Pattern.compile("&account=(.*)&");
            matcher = pattern.matcher(value);
            if (matcher.find())
                setAccount(matcher.group(1));

            pattern = Pattern.compile("&password=(.*)");
            matcher = pattern.matcher(value);
            if (matcher.find())
                setPassword(matcher.group(1));
        } catch (Exception e) {
//            e.printStackTrace();
            setAuto(false);
        }
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CookieUtil{" +
                "isAuto=" + isAuto +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
