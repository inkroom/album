package cn.inkroom.web.quartz.service.impl;

import cn.inkroom.web.quartz.bean.AlbumBean;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.dao.AlbumDao;
import cn.inkroom.web.quartz.dao.UploadDao;
import cn.inkroom.web.quartz.exception.upload.ExistException;
import cn.inkroom.web.quartz.exception.upload.NumberException;
import cn.inkroom.web.quartz.service.FileService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * 腾讯云cos文件存储
 *
 * @author 墨盒
 * @Deate 18-8-7
 */
public class CosFileService implements FileService {

    @Value("${cos.accessKey}")
    private String accessKey = "AKIDjRArTxSWZ5reI2eCWikeaW9dwBF99RqH";
    @Value("${cos.secretKey}")
    private String secretKey = "dBt7kIsJahgU8d7Zt36wd9TgjnnmaNSO";
    @Value("${cos.region}")
    private String region;

    private COSClient cosClient;

    @Value("${cos.prefix}")
    private String prefix;

    @Value("${cos.bucketName}")
    private String bucketName;

    @Autowired
    private UploadDao dao;
    @Autowired
    private AlbumDao albumDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String url_Name_prefix = "album/";

    public void setCosClient(COSClient cosClient) {
        this.cosClient = cosClient;
    }

    public COSClient getCosClient() {

        if (cosClient != null)
            return cosClient;


        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
// 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
// 3 生成cos客户端
        this.cosClient = new COSClient(cred, clientConfig);

        return cosClient;
    }

    @Override
    @Transactional
    public boolean delete(long id, long albumId, long ownerId) {

        String path = dao.getFile(id);

        String key = getKey(path);
        try {
            getCosClient().deleteObject(bucketName, key);


            if (albumDao.updateNumber(ownerId, albumId, -1) != 1) {
                return false;
            }
            if (dao.delete(id) != 1) {
                throw new RuntimeException("图片删除失败");
            }

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String upload(MultipartFile file, long albumId, long ownerId) throws Exception {


        int count = albumDao.updateNumber(ownerId, albumId, 1);
        if (count < 1)
            throw new NumberException();

        //更新最后修改时间
        count = albumDao.updateLastModify(ownerId, albumId, new Date());
        if (count < 1)
            throw new NumberException();

        AlbumBean album = albumDao.getOneAlbum(ownerId, albumId);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(Constants.RESPONSE_CONTENT_TYPE_IMAGE);
        metadata.setContentLength(file.getSize());
        metadata.setContentEncoding(Constants.CHARSET);

        String md5 = DigestUtils.md5Hex((file.getInputStream()));

        count = dao.selectMd5(md5);
        if (count > 0) {
            throw new ExistException();
        }


        String key = url_Name_prefix + album.getName() + "/" + UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        String url = prefix + key;

        if (dao.insertFile(url, albumId, md5) != 1) {
            throw new RuntimeException("上传图片失败");
        } else {
            PutObjectResult result = getCosClient().putObject(bucketName, key, file.getInputStream(), metadata);
            return url;
        }
    }

    @Override
    public void get(long id, HttpServletResponse response) {

        String url = dao.getFile(id);
        try {

            if (url == null) response.sendError(404);
            response.sendRedirect(encode(url));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }

    }


    private String encode(String url) throws UnsupportedEncodingException, URISyntaxException {
        //对url部分内容进行url编码
        URI uri = new URI(url);
        StringBuilder builder = new StringBuilder("https://" + uri.getHost());

        String s[] = uri.getPath().split("/");

        for (int i = 0; i < s.length; i++) {

            builder.append("/").append(URLEncoder.encode(s[i], "utf-8"));
        }
        return builder.toString().replaceAll("//", "/");
    }

    @Override
    public String get(long id) {
        try {
            return encode(dao.getFile(id));
        } catch (UnsupportedEncodingException | URISyntaxException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void download(HttpServletResponse response, long id) {
        String key = dao.getFile(id);
        try {
            if (key == null) {
                response.sendError(404);
                return;
            }

            key = getKey(key);
            //去除url前缀，得到key name
            logger.debug("获取到的key={}", key);

            GetObjectRequest request = new GetObjectRequest(bucketName, key);
            COSObject object = getCosClient().getObject(bucketName, key);
            //通知浏览器以下载的方式打开
            response.addHeader("Content-type", "application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + ((id + ".jpg")));

            writeObject(object.getObjectContent(), response.getOutputStream());

            cosClient.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 输出文件
     *
     * @param input cos文件输入流
     * @param out   http response 输出流
     */
    private void writeObject(InputStream input, OutputStream out) throws IOException {
        byte bytes[] = new byte[1024 * 5];
        int length = -1;
        while ((length = input.read(bytes)) != -1) {
            out.write(bytes, 0, length);
        }
        input.close();
        out.close();
    }

    /**
     * 去除url前缀，得到key name
     *
     * @param url
     * @return
     */
    private String getKey(String url) {
        return url.replaceAll(prefix, "");
    }
}
