package cn.inkroom.web.quartz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Value("${user_default_account}")
    private String account;
    @Value("${user_default_password}")
    private String password;

    public String getAccount() {
        return this.account;
    }

    public String getPassword() {
        return this.password;
    }
}
