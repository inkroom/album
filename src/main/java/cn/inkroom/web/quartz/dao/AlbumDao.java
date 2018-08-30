package cn.inkroom.web.quartz.dao;

import cn.inkroom.web.quartz.bean.AlbumBean;
import cn.inkroom.web.quartz.bean.ImageBean;
import cn.inkroom.web.quartz.bean.QuestionBean;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/16
 * @Time 10:47
 * @Descorption
 */
public interface AlbumDao {
    @Select("SELECT id,name,authority,owner,cover,number,size,create_time as createTime," +
            "change_time as changeTime,content,type" +
            " FROM album where owner = #{ownerId} order by last_modify desc")
    List<AlbumBean> getAlbums(@Param("ownerId") long ownerId) throws Exception;

    @Select("SELECT upload.id,upload.create_time as createTime " +
            "FROM upload where upload.owner = #{albumId} and upload.status = 1 order by id desc")
    List<ImageBean> getImages(@Param("albumId") long albumId) throws Exception;

    @Select("SELECT id,name,authority,owner,cover,number,size,create_time AS createTime," +
            "change_time AS changeTime,content,type" +
            " FROM album WHERE owner = #{ownerId} and id = #{albumId} LIMIT 1")
    AlbumBean getOneAlbum(@Param("ownerId") long ownerId, @Param("albumId") long albumId) throws Exception;

    @Update("update account,album set account.size=account.size-#{size,javaType=long,jdbcType=INTEGER}," +
            "album.size=album.size-#{size,javaType=long,jdbcType=INTEGER}" +
            "  where account.id=#{ownerId} and album.id=#{albumId}" +
            " and album.owner = account.id")
    int updateSize(@Param("ownerId") long ownerId, @Param("albumId") long albumId, @Param("size") long size) throws Exception;

    @Delete("delete from upload where id = #{imageId}")
    int deleteRes(@Param("imageId") long imageId) throws Exception;

    @Delete("delete from album where album.id = #{albumId} and album.owner = #{ownerId} limit 1 ")
    int deleteAlbum(@Param("ownerId") long ownerId, @Param("albumId") long albumId) throws Exception;

    @Update("update album set cover = #{cover} where id = #{albumId} limit 1")
    int updateCover(@Param("albumId") long albumId, @Param("cover") long cover);

    @Update("update album set album.number =album.number + #{number} where album.id = #{albumId} and album.owner = #{ownerId} limit 1")
    int updateNumber(@Param("ownerId") long ownerId, @Param("albumId") long albumId, @Param("number") int number) throws Exception;

    @Update("update album set last_modify =#{last,typeHandler=cn.inkroom.web.quartz.handler.DateHandler} where album.id = #{albumId} and album.owner = #{ownerId}  limit 1")
    int updateLastModify(@Param("ownerId") long ownerId, @Param("albumId") long albumId, @Param("last")Date time) throws Exception;

    @Insert("insert into album (name,authority,owner,content) values(#{a.name},#{a.authority},#{a.owner},#{a.content})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "a.id")
    long insertAlbum(@Param("a") AlbumBean album) throws Exception;

    @Select("select id,content as question,answer from question where question.id = #{id} limit 1")
    QuestionBean getQuestion(@Param("id") long questionId) throws Exception;

    @Insert("insert into question (content,answer,album_id) values(#{q.question},#{q.answer},#{q.owner})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "q.id")
    int insertQuestion(@Param("q") QuestionBean question) throws Exception;

    @Update("update question  set content = #{q.question},answer= #{q.answer}")
    int updateQuestion(@Param("q") QuestionBean question) throws Exception;

    @Update("update album set name = #{a.name},authority=#{a.authority},content=#{a.content}" +
            " where id = #{a.id} and owner = #{a.owner}")
    int updateAlbum(@Param("a") AlbumBean album) throws Exception;

    @Update(
            "update upload set owner = #{otherAlbumId} where id = #{imgId}"
    )
    int updateImageOwner(@Param("imgId") long imgId, @Param("otherAlbumId") long otherAlbumId) throws Exception;
}
