package cn.inkroom.web.quartz.bean;

import java.util.Date;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/21
 * @Time 21:38
 * @Descorption
 */
public class VisitBean {
    private String ip;
    private int count;
    private Date lastTime;
    private Date startTime;

    @Override
    public String toString() {
        return "VisitBean{" +
                "ip='" + ip + '\'' +
                ", count=" + count +
                ", lastTime=" + lastTime +
                ", startTime=" + startTime +
                '}';
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}
