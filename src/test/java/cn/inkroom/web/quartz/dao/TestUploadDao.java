package cn.inkroom.web.quartz.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author 墨盒
 * @Deate 18-8-7
 */
public interface TestUploadDao {


    @Insert("insert into upload (path,status,owner) values (#{path},#{status},#{owner})")
    int insert(@Param("path") String path,@Param("status") int status, @Param("owner") int owner);





}
