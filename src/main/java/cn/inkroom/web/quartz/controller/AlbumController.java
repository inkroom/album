package cn.inkroom.web.quartz.controller;

import cn.inkroom.web.quartz.annotions.Authority;
import cn.inkroom.web.quartz.bean.AjaxBean;
import cn.inkroom.web.quartz.bean.AlbumBean;
import cn.inkroom.web.quartz.bean.form.AlbumForm;
import cn.inkroom.web.quartz.enums.Result;
import cn.inkroom.web.quartz.service.AlbumService;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/21
 * @Time 13:03
 * @Descorption 相册
 */
@Controller
@RequestMapping("/" + Constants.PREFIX_IMAGE + "/{ownerId:[1-9]*[0-9]+}")
public class AlbumController extends BaseController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public String index(@PathVariable(value = "ownerId") long ownerId) throws Exception {
        List<AlbumBean> albums = albumService.getAlbums(ownerId);
//        if (albums == null) {
////            request.setAttribute(Constants.KEY_REQUEST_MESSAGE, "没有这个用户");
//            return "forward:" + Constants.URL_404_ALL;
//        }
//        request.setAttribute(Constants.KEY_REQUEST_IS_ME, ownerId == (RequestUtil.getLogin(request) == null ? -1 : RequestUtil.getLogin(request).getId()));
//        if (RequestUtil.getLogin(request).getId())
//        File[] files = new File(this.pathConfig.getImageBasePath()).listFiles();
        this.request.setAttribute(Constants.KEY_REQUEST_ALBUMS, albums);
        return "album";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxBean getAlbum(@PathVariable(value = "ownerId") long ownerId) throws Exception {
        List<AlbumBean> albums = albumService.getAlbums(ownerId);
        AjaxBean ajax = new AjaxBean(Result.SUCCESS);
        ajax.put(Constants.KEY_JSON_ALBUMS, albums);
        return ajax;
    }

    @RequestMapping(value = {"/createAlbum"}, method = {RequestMethod.POST})
    @ResponseBody
    @Authority(type = Authority.Type.ME)
    public AjaxBean create(AlbumForm form) throws Exception {
        checkForm(form);
//        if (!checkForm(form)) {
//            return new AjaxBean(Result.PARAM_NOT_SUIT, message);
//        }
        AlbumBean album;
        form.setOwner(RequestUtil.getLogin(request).getId());
        if ((album = albumService.createAlbum(form)) != null) {
            AjaxBean ajax = new AjaxBean(Result.SUCCESS);
            ajax.put(Constants.KEY_JSON_ALBUM, album);
            return ajax;
        }
        return new AjaxBean(Result.FAIL);
    }

    // TODO: 2017/9/12 提供一个个人统计接口，结算一共有多少相册，多少图片，占用空间等等

}
