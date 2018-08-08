package cn.inkroom.web.quartz.bean;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/16
 * @Time 14:31
 * @Descorption
 */
public class ImageBean {
    private long id;
    private String createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "id=" + id +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
