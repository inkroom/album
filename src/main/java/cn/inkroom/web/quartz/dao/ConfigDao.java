package cn.inkroom.web.quartz.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/15
 * @Time 23:49
 * @Descorption
 */
public interface ConfigDao {
    @Select("select path from upload where id=#{id}")
    String getFile(@Param("id") long id);

    @Select("select path from config where type=#{code}")
    String getConfig(@Param("code") int code);
}
