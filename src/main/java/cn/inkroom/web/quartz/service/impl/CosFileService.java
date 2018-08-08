package cn.inkroom.web.quartz.service.impl;

import cn.inkroom.web.quartz.bean.AlbumBean;
import cn.inkroom.web.quartz.dao.AlbumDao;
import cn.inkroom.web.quartz.dao.UploadDao;
import cn.inkroom.web.quartz.service.FileService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
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
    public String upload(MultipartFile file, long albumId, long ownerId) {


        try {


            int count = albumDao.updateNumber(ownerId, albumId, 1);
            if (count < 1)
                throw new RuntimeException();

            AlbumBean album = albumDao.getOneAlbum(ownerId, albumId);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            metadata.setContentLength(file.getSize());
            metadata.setContentEncoding("utf-8");


            String key = "album/" + album.getName() + "/" + UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            String url = "https://image-1252774288.cos.ap-chengdu.myqcloud.com/" + key;

            if (dao.insertFile(url, albumId) != 1) {
                throw new RuntimeException("上传图片失败");
            } else {
                PutObjectResult result = getCosClient().putObject(bucketName, key, file.getInputStream(), metadata);
                return url;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void get(long id, HttpServletResponse response) {

        String url = dao.getFile(id);
        try {

            //对url部分内容进行url编码

            URI uri = new URI(url);
            StringBuilder builder = new StringBuilder("https://" + uri.getHost());

            String s[] = uri.getPath().split("/");

            for (int i = 0; i < s.length; i++) {

                builder.append("/").append(URLEncoder.encode(s[i], "utf-8"));
            }
            response.sendRedirect(builder.toString().replace("//", "/"));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }

    }

    @Override
    public String get(long id) {
        return dao.getFile(id);
    }

    private String getKey(String url) {
        return url.replaceAll(prefix, "");
    }
}
