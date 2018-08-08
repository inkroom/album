package cn.inkroom.web.quartz.bean;

import cn.inkroom.web.quartz.bean.form.AlbumForm;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/16
 * @Time 10:38
 * @Descorption
 */
public class AlbumBean {
    private long id;
    private String name;
    private long authority;
    private long owner;
    private int cover;
    private int number;
    private long size;
    private String createTime;
    private String changeTime;
    private String content;
    private int type;

    public AlbumBean() {
    }

    public AlbumBean(AlbumForm form) {
        this.setAuthority(Long.parseLong(form.getAuthority()));
        this.setType(Integer.parseInt(form.getType()));
        this.setName(form.getName());
        this.setContent(form.getContent());
        this.setOwner(form.getOwner());
        this.setId(form.getId());
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAuthority() {
        return authority;
    }

    public void setAuthority(long authority) {
        this.authority = authority;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AlbumBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authority=" + authority +
                ", owner=" + owner +
                ", cover=" + cover +
                ", number=" + number +
                ", size=" + size +
                ", createTime='" + createTime + '\'' +
                ", changeTime='" + changeTime + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
