package cn.inkroom.web.quartz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/16
 * @Time 21:47
 * @Descorption
 */
public class UrlUtil {
    public static long getOwnerId(String url) {
//        Pattern pattern = Pattern.compile("^/[^/]+/([1-9]*[0-9])/");
        Pattern pattern = Pattern.compile("/[^/]+/([1-9]*[0-9]+)/");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return -1;
    }


    public static void main(String[] args) {
        System.out.println(getAlbumId("/image/album/2/32/861/removeImg"));
    }

    public static long getAlbumId(String url) {
        Pattern pattern = Pattern.compile("/[^/]+/[1-9]*[0-9]+/([1-9]*[0-9]+)/");
//        Pattern pattern = Pattern.compile("^/[^/]+/[1-9]*[0-9]/([1-9]*[0-9])/");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }
        return -1;
    }


    public static boolean isGetQuestion(String url) {
        return url.endsWith("getQuestion");
    }

    public static boolean isCheckAnswer(String url) {
        return url.endsWith("checkQuestion");
    }

    public static boolean isRemove(String url) {
        return url.endsWith("removeImg") || url.endsWith("removeAlbum");
    }

    public static boolean isImage(String url) {
        return url.endsWith("/res");
    }
}
