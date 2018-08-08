package cn.inkroom.web.quartz.dao;

import cn.inkroom.web.quartz.bean.AccountBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/15
 * @Time 17:04
 * @Descorption 登陆注册dao
 */
public interface AccountDao {
    @Select("SELECT * FROM account WHERE username = #{username} limit 1")
    AccountBean login(@Param("username") String username) throws Exception;

    @Select("SELECT id,nickname FROM account WHERE id = #{id} limit 1")
    AccountBean getAccount(@Param("id") long id) throws Exception;

    @Insert("insert into account (nickname,username,password,salt,role,create_time) " +
            "values(#{a.nickname},#{a.username},#{a.password},#{a.salt},#{a.role},now())")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "a.id")
    int register(@Param("a") AccountBean account) throws Exception;

    @Select("select count(*) from account where account.username = #{a} limit 1")
    int checkAccount(@Param("a") String account) throws Exception;
}
