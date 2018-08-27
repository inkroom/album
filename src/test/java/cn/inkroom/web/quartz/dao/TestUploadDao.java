package cn.inkroom.web.quartz.dao;

import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 墨盒
 * @Deate 18-8-7
 */
public interface TestUploadDao {


    @Insert("insert into upload (path,status,owner) values (#{path},#{status},#{owner})")
    int insert(@Param("path") String path, @Param("status") int status, @Param("owner") int owner);


    @Select("select * from upload")
    @ResultType(HashMap.class)
    List<Map<String, Object>> list();

    @Update("update upload set md5=#{md5} where id = #{id}")
    int update(@Param("id") long id, @Param("md5") String md5);


}
