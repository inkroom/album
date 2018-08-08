import cn.inkroom.web.quartz.dao.TestUploadDao;
import cn.inkroom.web.quartz.service.FileService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * @author 墨盒
 * @Deate 18-8-7
 */


@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations = {"classpath:spring/spring-bean.xml","classpath:spring/spring-file.xml"})
public class LocalFileToCos {


    @Autowired
    private TestUploadDao dao;

    private Logger logger= LoggerFactory.getLogger(getClass());

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
    public void test(){

        System.out.println(fileService.get(1995L));


    }

}
