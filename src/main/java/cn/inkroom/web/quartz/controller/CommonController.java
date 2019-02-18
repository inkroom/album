package cn.inkroom.web.quartz.controller;

import cn.inkroom.web.quartz.annotions.Authority;
import cn.inkroom.web.quartz.bean.AccountBean;
import cn.inkroom.web.quartz.bean.AjaxBean;
import cn.inkroom.web.quartz.config.PathConfig;
import cn.inkroom.web.quartz.entity.Size;
import cn.inkroom.web.quartz.enums.Result;
import cn.inkroom.web.quartz.exception.upload.ExistException;
import cn.inkroom.web.quartz.exception.upload.NumberException;
import cn.inkroom.web.quartz.handler.MessageException;
import cn.inkroom.web.quartz.service.ConfigService;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.service.FileService;
import cn.inkroom.web.quartz.service.UploadService;
import cn.inkroom.web.quartz.util.RequestUtil;
import cn.inkroom.web.quartz.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Calendar;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/16
 * @Time 14:37
 * @Descorption 通用，404等等
 */
@Controller
@RequestMapping(Constants.PREFIX_URL_COMMON)
public class CommonController extends BaseController {
    @Autowired
    private ConfigService config;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ConfigService configService;
    //    @Autowired
//    private UploadService uploadService;
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private FileService fileService;

    @Autowired
    private PathConfig pathConfig;

//    @RequestMapping(value = "404")
//    public String to404() {
//        return "404";
//    }

//    @RequestMapping(value = "403")
//    public String to403() {
//        return "403";
//    }

//    @RequestMapping(value = "message")
//    public String toMessage() {
//        return "message";
//    }

    @RequestMapping(value = {"{ownerId:[1-9]*[0-9]+}/{albumId:[1-9]*[0-9]+}/{id:[1-9]*[0-9]+}/res"})
    @ResponseBody
    public void getResource(@PathVariable(value = "id") long id,
                            Size size) throws Exception {

        fileService.get(id, response);

//        String path = fileService.get(id);
//        if (path == null) {
//            path = configService.getStringConfig(Constants.KEY_REDIS_IMAGE_404);
//        }
//        logger.debug(request.getRequestURL() + "   资源 id= " + id + "   文件路径 == " + path);
//        checkForm(size);
//
//
//        ResponseUtil.outImage(response, path, size);
    }

    @RequestMapping(value = {"upload"}, method = {RequestMethod.POST})
    @ResponseBody
    @Authority(type = Authority.Type.ME, open = false)
    public AjaxBean upload(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "album") String album, @RequestParam(value = "owner") String owner) throws Exception {
        long albumId;
        long ownerId;
        try {
            albumId = Long.parseLong(album);
            try {
                ownerId = Long.parseLong(owner);
            } catch (NumberFormatException e) {
                throw new MessageException("param.account.id.not.number");
            }
        } catch (NumberFormatException e) {
            throw new MessageException("param.album.id.not.number");
//            return new AjaxBean(Result.PARAM_NOT_SUIT, "非法的相册id");
        }
        AccountBean account = RequestUtil.getLogin(request);
        if (account.getSize() + file.getSize() > account.getCapacity()) {
            //相册容量不足
            throw new MessageException("request.upload.full");
        }
        logger.debug(" 相册名  album =" + album);

        try {
            String url = fileService.upload(file, albumId, ownerId);
            if (url != null) {
                return new AjaxBean(Result.SUCCESS);
            } else {
                return new AjaxBean(Result.FAIL);
            }
        } catch (ExistException e) {
            logger.debug("捕获 已存在异常");
            return new AjaxBean(Result.FILE_EXISTS, e.getMessage());
        } catch (NumberException e) {
            logger.debug("捕获 数字异常");
            return new AjaxBean(Result.FAIL);
        } catch (Exception e) {
            return new AjaxBean(Result.EXCEPTION);
        }

//        Calendar c = Calendar.getInstance();
//        File targetFile = new File(config.getStringConfig(Constants.KEY_REDIS_UPLOAD_BASE_PATH), c.get(Calendar.YEAR) + "-"
//                + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + "_"
//                + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + ":" + c.get(Calendar.MILLISECOND));
////        File targetFile = new File(this.pathConfig.getImageBasePath() + File.separator + path + File.separator, System.currentTimeMillis() + fileName);
//        if ((targetFile.exists())) {
//            return new AjaxBean(Result.FAIL);
//        }
////        AccountBean visit= request.getAttribute(Constants.KEY_REQUEST_VISIT_ACCOUNT)
////        try {
//        boolean result = uploadService.insertFile(targetFile.getAbsolutePath(), albumId, ownerId);
//        if (!result) {
//            throw new MessageException("param.album.id.not.exists");
//        }
//        logger.info("上传文件路径=" + targetFile.getAbsolutePath());
////            return new AjaxBean(Result.PARAM_NOT_SUIT, "非法的相册id");
//        file.transferTo(targetFile);
//        //更新session域存储的size
//        account.setSize(account.getSize() + file.getSize());
//        return new AjaxBean(Result.SUCCESS);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new AjaxBean(Result.FAIL);
//        }
    }


    @RequestMapping("{ownerId:[1-9]*[0-9]+}/{albumId:[1-9]*[0-9]+}/{id:[1-9]*[0-9]+}/download")
    @ResponseBody
    public void download(@PathVariable(value = "id") long id) {
        fileService.download(request.getScheme() + "://" + request.getHeader("Host"), response, id);
    }
}
