package cn.inkroom.web.quartz.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件操作接口
 * 兼容本地或者cos云存储
 */
public interface FileService {
    /**
     * 删除文件
     *
     * @param id 文件id，
     * @return 结果
     */
    boolean delete(long id,long albumId,long ownerId);

    /**
     * 文件上传
     *
     * @param file    文件
     * @param albumId 相册id
     * @return 可直接使用的url
     */
    String upload(MultipartFile file, long albumId,long ownerId) throws Exception;

    /**
     * 获取文件
     *
     * @param id       文件id
     * @param response 响应
     */
    void get(long id, HttpServletResponse response);

    /**
     * 获取文件url
     * @param id 文件id
     * @return url
     */
    String get(long id);

    /**
     * 通过流下载文件
     * @param response 响应
     * @param id 文件id
     */
    void download(HttpServletResponse response,long id);

}
