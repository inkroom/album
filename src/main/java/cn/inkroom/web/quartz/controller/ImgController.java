package cn.inkroom.web.quartz.controller;

import cn.inkroom.web.quartz.annotions.Authority;
import cn.inkroom.web.quartz.bean.form.AlbumForm;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.enums.Result;
import cn.inkroom.web.quartz.handler.MessageException;
import cn.inkroom.web.quartz.service.AlbumService;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.inkroom.web.quartz.bean.AjaxBean;
import cn.inkroom.web.quartz.bean.AlbumBean;
import cn.inkroom.web.quartz.bean.ImageBean;
import cn.inkroom.web.quartz.bean.QuestionBean;
import cn.inkroom.web.quartz.service.FileService;
import cn.inkroom.web.quartz.service.UploadService;
import cn.inkroom.web.quartz.util.RequestUtil;
import cn.inkroom.web.quartz.util.V;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + Constants.PREFIX_IMAGE + "/{ownerId:[1-9]*[0-9]+}/{albumId:[1-9]*[0-9]+}")
public class ImgController extends BaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private HttpServletResponse response;
    //    @Autowired
//    private UploadService uploadService;
    @Autowired
    private FileService fileService;

    @RequestMapping(value = {"/removeAlbum"}, method = {RequestMethod.GET})
    @ResponseBody
    @Authority(type = Authority.Type.ME)
    public AjaxBean removeAlbum() throws Exception {
//        AlbumBean album = albumService.getOneAlbum(ownerId, albumId);
        AlbumBean album = (AlbumBean) request.getAttribute(Constants.KEY_REQUEST_VISIT_ALBUM);
        if (album == null) {
            throw new MessageException("data.album.not.exists");
//            return new AjaxBean(Result.FILE_NOT_EXISTS, messageConfig.getAlbumNotExists());
        }
//        System.out.println(" removeAlbum  album  " + album);
        return new AjaxBean(albumService.removeAlbum(album.getOwner(), album.getId(), album.getSize()) ? Result.SUCCESS : Result.FAIL);
    }

    @RequestMapping(value = {"/changeAlbum"})
    @ResponseBody
    @Authority(type = Authority.Type.ME)
    public AjaxBean changeAlbum(AlbumForm form, @PathVariable long albumId) throws Exception {
        checkForm(form);
        form.setOwner(RequestUtil.getLogin(request).getId());
        form.setId(albumId);
        AlbumBean album = albumService.changeAlbum(form);
        if (album == null) {
            return new AjaxBean(Result.FAIL);
        } else {
            AjaxBean ajax = new AjaxBean(Result.SUCCESS);
            ajax.put(Constants.KEY_JSON_ALBUM, album);
            return ajax;
        }
    }

    @RequestMapping(value = {"/{imageId:[1-9]*[0-9]+}/removeImg"}, method = {RequestMethod.GET})
    @ResponseBody
    @Authority(type = Authority.Type.ME)
    public AjaxBean removeImage(@PathVariable(value = "imageId") long imageId) throws Exception {
        AlbumBean album = (AlbumBean) request.getAttribute(Constants.KEY_REQUEST_VISIT_ALBUM);
        boolean result = fileService.delete(imageId, album.getId(), RequestUtil.getLogin(request).getId());
//        if (path == null) {
//            throw new MessageException("data.file.not.exists");
//            return new AjaxBean(Result.FILE_NOT_EXISTS, messageConfig.getFileNotExists());
//        }

        logger.info(" 要删除的相册=" + album);
        return new AjaxBean(result ? Result.SUCCESS : Result.FAIL);
    }

    /**
     * 移动图片到另一个相册
     *
     * @param imageId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/{imageId:[1-9]*[0-9]+}/moveImg/{otherAlbumId:[1-9]*[0-9]+}"}, method = {RequestMethod.GET})
    @ResponseBody
    @Authority(type = Authority.Type.ME)
    public AjaxBean moveImage(@PathVariable(value = "imageId") long imageId, @PathVariable(value = "otherAlbumId") long otherAlbumId) throws Exception {
        AlbumBean album = (AlbumBean) request.getAttribute(Constants.KEY_REQUEST_VISIT_ALBUM);
        boolean result = albumService.moveImage(album, imageId, otherAlbumId);
        return new AjaxBean(result ? Result.SUCCESS : Result.FAIL);
    }

    @RequestMapping(value = {"/{imageId:[1-9]*[0-9]+}/setCover"}, method = {RequestMethod.GET})
    @ResponseBody
    @Authority(type = Authority.Type.ME)
    public AjaxBean setCover(@PathVariable long imageId) throws Exception {
        String path = fileService.get(imageId);
        if (path == null) {
            throw new MessageException("data.file.not.exists");
//            return new AjaxBean(Result.FILE_NOT_EXISTS, messageConfig.getFileNotExists());
        }
        AlbumBean album = (AlbumBean) request.getAttribute(Constants.KEY_REQUEST_VISIT_ALBUM);
//        AlbumBean album = albumService.getOneAlbum(ownerId, albumId);
        if (album == null) {
            throw new MessageException("data.album.not.exists");
//            return new AjaxBean(Result.ALBUM_NOT_EXISTS, messageConfig.getAlbumNotExists());
        }
        return new AjaxBean(albumService.setCover(album.getId(), imageId) ? Result.SUCCESS : Result.FAIL);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String toAlbum() throws Exception {
//        request.setAttribute(Constants.KEY_REQUEST_IS_ME, ownerId == (RequestUtil.getLogin(request) == null ? -1 : RequestUtil.getLogin(request).getId()));
//        AlbumBean album = albumService.getOneAlbum(ownerId, albumId);
        AlbumBean album = (AlbumBean) request.getAttribute(Constants.KEY_REQUEST_VISIT_ALBUM);
        logger.info("获取的相册=" + album);
        if (album == null) {
            return "forward:" + Constants.URL_404_ALL;
        }
        List<ImageBean> images = albumService.getImages(album.getId());
        request.setAttribute(Constants.KEY_REQUEST_ALBUM, album);
        request.setAttribute(Constants.KEY_REQUEST_IMAGES, images);
        return "images";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public AjaxBean getAlbum() throws Exception {
        AlbumBean album = (AlbumBean) request.getAttribute(Constants.KEY_REQUEST_VISIT_ALBUM);
        if (album == null) {
            throw new MessageException("param.album.id.not.exists");
        }
        AjaxBean ajax = new AjaxBean(Result.SUCCESS);
        ajax.put(Constants.KEY_JSON_ALBUM, album);
        return ajax;
    }

    @RequestMapping(value = {"/{questionId:[1-9]*[0-9]+}/getQuestion"})
    @ResponseBody
    public AjaxBean getQuestion(@PathVariable long questionId) throws Exception {
        QuestionBean question = albumService.getQuestion(questionId);
        if (question == null)
            return new AjaxBean(Result.FAIL);
        request.getSession().setAttribute(Constants.KEY_SESSION_QUESTION, question);
        AjaxBean ajax = new AjaxBean(Result.SUCCESS);
        ajax.put(Constants.KEY_JSON_QUESTION, question.getQuestion());
        return ajax;
    }

    @RequestMapping(value = {"/{questionId:[1-9]*[0-9]+}/checkQuestion"})
    @ResponseBody
    public AjaxBean check(@PathVariable long questionId, String answer) throws Exception {
        if (V.isEmpty(answer)) {
            throw new Exception("request.not.answer.question");
//            return new AjaxBean(Result.PARAM_NOT_SUIT, "未回答问题");
        }

        QuestionBean question = (QuestionBean) request.getSession().getAttribute(Constants.KEY_SESSION_QUESTION);
        if (question != null) {
            if (questionId == question.getId()) {
                if (answer.equals(question.getAnswer())) {
                    request.getSession().setAttribute(Constants.KEY_SESSION_ANSWER, answer);
                    return new AjaxBean(Result.SUCCESS);
                } else {
                    return new AjaxBean(Result.FAIL);
                }
            }
        }
        return new AjaxBean(Result.ANSWER_ERROR);
    }
}
