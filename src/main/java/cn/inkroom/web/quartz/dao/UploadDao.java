package cn.inkroom.web.quartz.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/9/1
 * @Time 20:08
 * @Descorption
 */
public interface UploadDao {
    @Insert("insert into upload (path,owner,create_time,status) values(#{path},#{owner},now(),1)")
    int insertFile(@Param("path") String path, @Param("owner") long albumId) throws Exception;

    @Select("select path from upload where id=#{id} and status=1")
    String getFile(@Param("id") long id);


    @Update("update upload set status=0 where id = #{id}")
    int delete(@Param("id") long imgId);

}
