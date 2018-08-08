package cn.inkroom.web.quartz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/15
 * @Time 21:04
 * @Descorption
 */
@Configuration
public class MessageConfig {
    @Value("${message_common_exception}")
    private String exceptionMessage;
    @Value("${message_login_exist}")
    private String loginExists;
    @Value("${message_login_fail}")
    private String loginFail;
    @Value("${message_file_not_exists}")
    private String fileNotExists;
    @Value("${message_album_not_exists}")
    private String albumNotExists;

    @Value("${message_authority_album_private}")
    private String authorityAlbumPrivate;

//    public String getAuthorityAlbumPrivate() {
//        return authorityAlbumPrivate;
//    }
//
//    public String getExceptionMessage() {
//        return exceptionMessage;
//    }
//
//    public String getLoginFail() {
//        return loginFail;
//    }
//
//    public String getFileNotExists() {
//        return fileNotExists;
//    }
//
//    public String getAlbumNotExists() {
//        return albumNotExists;
//    }
//
//    public String getLoginExists() {
//        return loginExists;
//    }
}
