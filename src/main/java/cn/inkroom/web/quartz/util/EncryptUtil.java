package cn.inkroom.web.quartz.util;

import cn.inkroom.web.quartz.config.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/15
 * @Time 20:41
 * @Descorption 加密类
 */
public class EncryptUtil {
    private static final byte key = 7;

    /**
     * 功能的描述: 将明文信息进行128位MD5加密
     *
     * @param lainText 需要加密的明文信息
     * @param salt     盐
     * @return 返回32位小写字母的md5码
     */
    public static String parseStrToMd5L32(String lainText, String salt) {
//		此处加入了固定的盐，需要更改，删除此处
        lainText += salt;
//        System.outJson.println(lainText);
        String resultStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(lainText.getBytes());
            StringBuilder stringBuffer = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                int bt = bytes[i] & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            resultStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 功能的描述: 将明文(密码)信息加盐后进行128位MD5加密,返回两个值：加密结果和盐
     *
     * @param password 需要加密的明文信息
     * @return str[0]返回32位小写字母的md5码, str[1] 盐 Create Date:2016-4-13
     */
    public static String[] parsePassToMd5(String password) {
        String[] result = new String[2];
        String salt = getRandomString(8);
        result[0] = parseStrToMd5L32(password, salt);
        result[1] = salt;
        return result;
    }

    /**
     * 功能的描述: 将明文密码+盐进行Md5运算后，与在数据库中保存的md5值进行比较
     *
     * @param pass    明文密码
     * @param salt    盐
     * @param md5Pass 加密后的md5密码
     * @return true，表示比较结果相同，说明验证通过，false 验证不同过 Create Date:2016-4-13
     */
    public static boolean comparePass(String pass, String salt, String md5Pass) {
        return parseStrToMd5L32(pass, salt).equals(md5Pass);
    }

    /**
     * 功能的描述: 将隐私信息进行xor简答加密，解密只需再次调用该方法
     *
     * @param info 隐私信息
     * @return Create Date:2016-4-13
     */
    public static String xorInfo(String info) {
        byte[] infoBytes = info.getBytes();
        for (int i = 0; i < infoBytes.length; i++) {
            infoBytes[i] = (byte) (infoBytes[i] ^ key);
        }

        return new String(infoBytes);
    }

    /**
     * 功能的描述: 生成指定长度的随机字符串
     *
     * @param length
     * @return Create Date:2016-4-13
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * url编码
     * @param value
     * @return
     */
    public static String urlEncode(String value){
        try {
            return URLEncoder.encode(value, Constants.CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * url 解码
     * @param value
     * @return
     */
    public static String urlDecode(String value){
        try {
            return URLDecoder.decode(value,Constants.CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String test = "inkbox.123";
        String[] result = parsePassToMd5(test);
        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println((comparePass("inkbox","jk9blnkc", "79190a6239e63cb82953d3583cbcf703")));
    }
}
