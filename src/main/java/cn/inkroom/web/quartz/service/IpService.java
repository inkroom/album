package cn.inkroom.web.quartz.service;

import cn.inkroom.web.quartz.bean.VisitBean;
import cn.inkroom.web.quartz.dao.IpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/21
 * @Time 21:52
 * @Descorption
 */
@Service
public class IpService {
    @Autowired
    private IpDao ipDao;

    public VisitBean updateIp(String ip) {
        VisitBean visit = new VisitBean();
        visit.setIp(ip);
        return ipDao.updateIp(visit);
    }

    public boolean updateCount(String ip, Date now) {
        return ipDao.updateCount(ip, now) == 1;
    }

//    public VisitBean getVisitBean(String ip){
//        int count = ipDao.updateIp(ip);
//        if (count)
//    }

//    public void deleteIp()

    public int deleteIp(long sec) {
        return ipDao.deleteIp(new Date(new Date().getTime() - sec));
    }
}
