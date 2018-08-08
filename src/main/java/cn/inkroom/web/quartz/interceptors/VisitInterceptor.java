package cn.inkroom.web.quartz.interceptors;

import cn.inkroom.web.quartz.bean.AccountBean;
import cn.inkroom.web.quartz.bean.AjaxBean;
import cn.inkroom.web.quartz.bean.AlbumBean;
import cn.inkroom.web.quartz.bean.QuestionBean;
import cn.inkroom.web.quartz.config.MessageConfig;
import cn.inkroom.web.quartz.entity.Size;
import cn.inkroom.web.quartz.enums.Result;
import cn.inkroom.web.quartz.service.AccountService;
import cn.inkroom.web.quartz.service.AlbumService;
import cn.inkroom.web.quartz.service.ConfigService;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.util.RequestUtil;
import cn.inkroom.web.quartz.util.ResponseUtil;
import cn.inkroom.web.quartz.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/16
 * @Time 18:18
 * @Descorption 访问某个用户的相册获取个人信息，判断是否是自己的相册
 */
public class VisitInterceptor extends BaseInterceptor {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AlbumService albumService;
    //    @Autowired
//    private ConfigService configService;
    @Autowired
    private MessageConfig messageConfig;


    private int count = 0;

    private long ownerId;
    private long isMe;//判断是否访问的是自己的相册，-1则不是，否则为自己的id
    private AlbumBean album;
//    private AccountBean

    /**
     * 判断账号404
     *
     * @param request  请求
     * @param response 响应
     * @return 如果404则返回false，否则返回true
     * @throws Exception 各种异常
     */
    private boolean checkAccount404(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = (request.getRequestURI());
//        logger.debug(url+"     "+"VisitInterceptor  url = " + url);
//        logger.debug(url+"     "+getClass() + "    检测account 404");
        ownerId = (UrlUtil.getOwnerId(url));
        logger.debug(url + "  账号ID= " + ownerId);
        if (ownerId != -1) {
            AccountBean visitAccount = RequestUtil.getLogin(request);
            if (visitAccount == null || visitAccount.getId() != ownerId) {//不是访问自己
                visitAccount = accountService.getAccount(ownerId);
                isMe = -1;
            } else {
                isMe = visitAccount.getId();
            }
//            isMe = RequestUtil.getLogin(request) == null ? -1 : RequestUtil.getLogin(request).getId() == ownerId ? ownerId : -1;
//            ownerId == (RequestUtil.getLogin(request) == null ? -1 : RequestUtil.getLogin(request).getId()));
            request.setAttribute(Constants.KEY_REQUEST_IS_ME, isMe);
            logger.debug(url + "     " + "VisitInterceptor  account  " + visitAccount);
            if (visitAccount == null) {//不存在指定账号
                response.sendError(404);
//                ResponseUtil.out404(request, response, configService.getStringConfig(Constants.KEY_REDIS_IMAGE_404));
                return false;
            }
            request.setAttribute(Constants.KEY_REQUEST_VISIT_ACCOUNT, visitAccount);
//            return true;
        }
        return true;
    }

    /**
     * 判断相册404
     *
     * @param request  请求
     * @param response 响应
     * @return 404返回false，否则返回true
     * @throws Exception 异常
     */
    private boolean checkAlbum404(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = (request.getRequestURI());
        long albumId = UrlUtil.getAlbumId(url);
        logger.debug(url + "     " + "获取 album Id = " + albumId);
        if (albumId != -1) {//进入某个相册，对某个相册进行操作
//            logger.debug(url+"     "+getClass() + "    检测album 404");
            album = albumService.getOneAlbum(ownerId, albumId);
            logger.info(url + "     " + "VisitInterceptor  album  " + album);
            if (album == null) {
                if (RequestUtil.isAjax(request)) {
                    ResponseUtil.outJson(response, new AjaxBean(Result.ALBUM_NOT_EXISTS).toString());
                    return false;
                }
                response.sendError(404);
//                ResponseUtil.out404(request, response, configService.getStringConfig(Constants.KEY_REDIS_IMAGE_404));
                return false;
            }
            request.setAttribute(Constants.KEY_REQUEST_VISIT_ALBUM, album);
        }
        return true;
    }

    /**
     * 判断相册访问权限
     *
     * @param request  请求
     * @param response 响应
     * @return 结果
     * @throws Exception 异常
     */
    private boolean check403(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //如果album为null，可能是404，也可能是没有访问具体相册，两种情况均在上一层处理了
        String url = (request.getRequestURI());
        logger.debug(url + "     " + count++ + "        " + request.getRequestURL() + "     " + album);
        long albumId = UrlUtil.getAlbumId(url);
        logger.debug(url + "    url = " + albumId + "   数据库= " + (album == null ? -1 : album.getId()));
        if (album != null && albumId != -1 && album.getId() != albumId) {
            album = albumService.getOneAlbum(ownerId, albumId);
        }
        if (album != null && album.getId() == albumId) {
            if (album.getAuthority() == 0) return true;//所有人可见
            else if (album.getAuthority() == 1) {//仅自己可见
//            logger.debug(url+"     "+getClass() + "    检测album 403");
                if (isMe != -1) {
//                request.setAttribute(Constants.KEY_REQUEST_VISIT_ALBUM, album);
                    return true;
                } else {
                    if (RequestUtil.isAjax(request)) {
                        ResponseUtil.outJson(response, new AjaxBean(Result.NO_AUTHORITY).toString());
                        logger.debug(url + "     " + count++ + "        " + request.getRequestURL() + "     " + 1);
                        return false;
                    }
//                return ResponseUtil.out404(request, response);
                    if (UrlUtil.isImage(url)) {
                        response.sendError(403);
//                ResponseUtil.out404(request, response, configService.getStringConfig(Constants.KEY_REDIS_IMAGE_403));
//                    logger.debug(url+"     "+count++ + "        " + request.getRequestURL() + "     " + 2);
//                    logger.debug(url+"     "+getClass() + "  图片403");
//                    response.setContentType(Constants.RESPONSE_CONTENT_TYPE_IMAGE);
//                    response.setStatus(403);
                        return false;
                    }
//                logger.debug(url+"     "+getClass() + "  文本403");
                    logger.debug(url + "     " + count++ + "        " + request.getRequestURL() + "     " + 3);
//                    request.setAttribute(Constants.KEY_REQUEST_MESSAGE, messageConfig.getAuthorityAlbumPrivate());

                    request.setAttribute(Constants.KEY_REQUEST_MESSAGE, "request.album.authority");
                    request.getRequestDispatcher(Constants.URL_AUTHORITY_ALL).forward(request, response);
                    return false;
                }
            } else if (album.getAuthority() > 1) {//回答问题
                if (isMe != -1)
                    return true;
//            logger.debug(url+"     "+getClass() + "   回答问题");
                if (UrlUtil.isGetQuestion(url) || UrlUtil.isCheckAnswer(url) || UrlUtil.isRemove(url)) {//获取答案，验证答案，删除操作,允许通行
                    return true;
                }
                QuestionBean question = (QuestionBean) request.getSession().getAttribute(Constants.KEY_SESSION_QUESTION);
                logger.debug("   问题是   " + question);
                if (question != null) {
                    String answer = RequestUtil.getAnswer(request);
                    logger.debug(url + "   回答正确    " + answer + "     " + question + "     " + album + "     ");
                    if (answer != null && album.getAuthority() == question.getId()) {//存储了问题和答案，并且问题是相册的问题
                        if (answer.equals(question.getAnswer())) {//回答正确
                            return true;
                        } else {
                            if (RequestUtil.isAjax(request)) {
//                            logger.debug(url+"     "+getClass() + "   相册ajax 403");
                                logger.debug(url + "     " + count++ + "        " + request.getRequestURL() + "     " + 4);
                                ResponseUtil.outJson(response, new AjaxBean(Result.ANSWER_ERROR).toString());
                            } else if (UrlUtil.isImage(url)) {
                                logger.debug(url + "     " + "   相册图片403");
                                logger.debug(url + "     " + count++ + "        " + request.getRequestURL() + "     " + 5);

//                                response.sendError(403);
                                ResponseUtil.outImage(response, "http://static.inkroom.cn/image/img/no.jpg",
                                        new Size(Constants.DEFAULT_SIZE_WIDTH, Constants.DEFAULT_SIZE_HEIGHT));
//                                ResponseUtil.outImage(response, configService.getStringConfig(Constants.KEY_REDIS_IMAGE_403),
//                                        new Size(Constants.DEFAULT_SIZE_WIDTH, Constants.DEFAULT_SIZE_HEIGHT));
                            } else {
                                logger.debug(url + "     " + count++ + "        " + request.getRequestURL() + "     " + 6);
//                            logger.debug(url+"     "+getClass() + "   相册网页 403");
                                request.setAttribute(Constants.KEY_REQUEST_MESSAGE, "回答错误");
                                request.getRequestDispatcher(Constants.URL_MESSAGE_ALL).forward(request, response);
                            }
                            return false;
                        }
                    }
                }
                if (UrlUtil.isImage(url)) {
                    logger.debug(url + "    是图片  " + count++ + "        " + request.getRequestURL() + "     " + 7);
//                    response.sendError(403);
                    ResponseUtil.outImage(response, "http://static.inkroom.cn/image/img/no.jpg",
                            new Size(Constants.DEFAULT_SIZE_WIDTH, Constants.DEFAULT_SIZE_HEIGHT));
//                    ResponseUtil.outImage(response, configService.getStringConfig(Constants.KEY_REDIS_IMAGE_403),
//                            new Size(Constants.DEFAULT_SIZE_WIDTH, Constants.DEFAULT_SIZE_HEIGHT));
                    return false;
                }
                logger.debug(url + "     " + "没有存储问题和答案，回到页面从新回答");
                logger.debug(url + "     " + count++ + "        " + request.getRequestURL() + "     " + 8);
                response.sendRedirect("/" + Constants.PREFIX_IMAGE + "/" + album.getOwner() + "/?id=" + album.getId());
                return false;
            }
        }

//                    logger.debug(url+"     "+getClass() + "   set  KEY_REQUEST_VISIT_ALBUM");
        return true;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        count = 0;
//        url = (url == null ? request.getRequestURI() : url);
//        logger.debug(url+"     "+"VisitInterceptor  before  url = " + url + "   " + request.getRequestURL() + "    result =  " + request.getRequestURL().toString().endsWith(request.getRequestURI()));
        boolean result = checkAccount404(request, response);
        if (!result) {//判断账号404
//            logger.debug(url+"     "+"账号404");
            return false;
        }
        result = checkAlbum404(request, response);
        if (!result) {//判断相册404
//            logger.debug(url+"     "+"相册404");
            return false;
        }
        //判断相册访问权限，如果没有访问某个相册，直接返回true
        return check403(request, response);
    }

}
