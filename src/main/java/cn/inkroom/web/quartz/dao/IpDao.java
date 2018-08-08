package cn.inkroom.web.quartz.dao;

import cn.inkroom.web.quartz.bean.VisitBean;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/21
 * @Time 21:39
 * @Descorption
 */
public interface IpDao {
    @Select("call update_ip(#{v.ip})")
    VisitBean updateIp(@Param("v") VisitBean visit);

    @Delete("delete from ip where ip.last_time < #{time}")
    int deleteIp(@Param("time") Date time);

//    @Insert("insert into ip values(#{ip},1,now(),now());")
//    int insertIp(@Param("ip") String ip);
//
//    @Update("update ip set ip.count=ip.count+1,ip.last_time=now() where ip.ip=#{ip};")
//    int updateIp(@Param("ip") String ip);

    @Update("update ip set ip.count=ip.count+1,ip.last_time=now() where ip.ip_address=#{ip};")
    int updateCount(@Param("ip") String ip, @Param("now") Date now);
}
