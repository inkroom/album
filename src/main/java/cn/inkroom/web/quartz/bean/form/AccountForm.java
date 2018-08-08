package cn.inkroom.web.quartz.bean.form;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/9/11
 * @Time 10:05
 * @Descorption 注册用账号表单
 */
public class AccountForm {
    private String nickname;
    private String username;
    private String password;
    private String pardon;
    private String role;

    public String getPardon() {
        return pardon;
    }

    public void setPardon(String pardon) {
        this.pardon = pardon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
