package cn.inkroom.web.quartz.bean;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/15
 * @Time 17:05
 * @Descorption
 */
public class AccountBean {
    private long id;
    private String salt;
    private String nickname;
    private String username;
    private String password;
    private String role;
    private long size;
    private long capacity;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {

        return "AccountBean{" +
                "id=" + id +
                ", salt='" + salt + '\'' +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", size=" + size +
                ", capacity=" + capacity +
                '}';
    }
}
