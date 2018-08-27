import cn.inkroom.web.quartz.dao.TestUploadDao;
import cn.inkroom.web.quartz.service.FileService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 墨盒
 * @Deate 18-8-7
 */


@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations = {"classpath:spring/spring-bean.xml", "classpath:spring/spring-file.xml"})
public class LocalFileToCos {


    @Autowired
    private TestUploadDao dao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void upload() {
        String accessKey = "AKIDjRArTxSWZ5reI2eCWikeaW9dwBF99RqH";
        String secretKey = "dBt7kIsJahgU8d7Zt36wd9TgjnnmaNSO";

        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
// 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-chengdu"));
// 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
// bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式


        String bucketName = "image-1252774288";


//        File[] files = new File("/media/inkbox/media/图片/").listFiles();
//        if (files != null)
//            for (int i = 0; i < files.length; i++) {
//
//                File[] f = files[i].listFiles();

        File file = new File("/media/inkbox/media/图片/罪恶王冠");


        String dir = file.getName();
        File f[] = file.listFiles();
        for (int j = 0; j < f.length; j++) {


            String key = "album/" + dir + "/" + f[j].getName();

            PutObjectRequest request = new PutObjectRequest(bucketName, key, f[j]);

            PutObjectResult result = cosclient.putObject(request);

            //拼接url
            String url = "https://image-1252774288.cos.ap-chengdu.myqcloud.com/" + key;
            if (dao.insert(url, 1, 47) != 1) {
                logger.info(key + "上传失败");
            } else {
                logger.info(key + "上传成功");
            }

        }


//            }


    }


    @Autowired
    private FileService fileService;

    @Test
    public void test() {

        System.out.println(fileService.get(1995L));


    }


    @Value("${cos.bucketName}")
    String buckname;
    @Autowired
    COSClient cosClient;

    @Value("${cos.prefix}")
    private String prefix;

    @Test
    public void updateMd5() {
        System.out.println(prefix);
        return;

//        try {
//            System.out.println(DigestUtils.md5Hex(new FileInputStream("/media/inkbox/media/图片/GOSICK/GOSICK02.jpg")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////
//        List<Map<String, Object>> list = dao.list();
//
//
//        int size = list.size();
//        for (int i = 0; i < size; i++) {
//            Map<String, Object> item = list.get(i);
//            String key = item.get("path").toString().replace("https://image-1252774288.cos.ap-chengdu.myqcloud.com/album/", "");
//
//
////            GetObjectRequest request = new GetObjectRequest(buckname, key);
////
////            ObjectMetadata cosObject = cosClient.getObjectMetadata(buckname,key);
////
////
////            System.out.println(cosObject.getETag());
////            System.out.println(cosObject.getContentMD5());
//////            System.out.println(item.get("id").getClass());
//            int count = 0;
//            try {
//                count = dao.update(Long.parseLong(item.get("id").toString()), DigestUtils.md5Hex(new FileInputStream(new File("/media/inkbox/media/图片/" + key))));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (count == 1) {
//                System.out.println(item.get("id") + "--更新成功");
//            } else {
//                System.out.println(item.get("id") + "--更新失败");
//            }
//
//        }

//        System.out.println(list.size());
//        System.out.println(list.get(0));


    }

}
