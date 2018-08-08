package cn.inkroom.web.quartz.controller;

import cn.inkroom.web.quartz.bean.AccountBean;
import cn.inkroom.web.quartz.bean.AjaxBean;
import cn.inkroom.web.quartz.bean.form.AccountForm;
import cn.inkroom.web.quartz.enums.Result;
import cn.inkroom.web.quartz.handler.MessageException;
import cn.inkroom.web.quartz.service.AccountService;
import cn.inkroom.web.quartz.config.Constants;
import cn.inkroom.web.quartz.util.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.inkroom.web.quartz.util.EncryptUtil;
import cn.inkroom.web.quartz.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController extends BaseController {


    @Autowired
    private HttpServletResponse response;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = {"/test"}, method = {RequestMethod.GET})
    public String test(Integer value) throws Exception {
        logger.info("value = " + value);
        logger.info("contextPath="+request.getServletContext().getContextPath());
//        if (value.equals("1")) {
//            pool.push("1", "1", "2");
//        } else {
//            pool.pop("1");
//        }
        return "message";
    }

//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView exceptionResolver(Exception ex, HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("ex", ex);
//        logger.info("exception  " + ex);
//        mav.setViewName("/index");
//
//        return mav;
//    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    public String toLogin() throws Exception {
        Cookie[] cookies = this.request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.KEY_COOKIES_STATUS)) {
                    try {
                        CookieUtil cookieUtil = new CookieUtil(cookie.getValue());
                        this.request.setAttribute("auto", cookieUtil.isAuto());
                        this.request.setAttribute("account", EncryptUtil.xorInfo(EncryptUtil.urlDecode(cookieUtil.getAccount())));
                        this.request.setAttribute("password", EncryptUtil.xorInfo(EncryptUtil.urlDecode(cookieUtil.getPassword())));
                        break;
                    } catch (Exception e) {
                        throw new MessageException("request.cookie.not.suit", e);
//                        request.setAttribute(Constants.KEY_REQUEST_MESSAGE, messageConfig.getExceptionMessage());
//                        return "message";
                    }
                }
            }
        }
        return "login";
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    @ResponseBody
    public AjaxBean login(@RequestParam(value = "account") String account,@RequestParam(value = "password") String password, @RequestParam(value = "remember",defaultValue = "false") Boolean remember,
                          @RequestParam(value = "auto",defaultValue = "false") Boolean auto) throws Exception {
        AccountBean accountBean = null;
        try {
            logger.info("开始登陆");
            if ((accountBean = this.accountService.login(account, password, false)) != null) {
                if ((remember) || (auto)) {
                    String value = "auto=" + auto + "&account=" +
                            EncryptUtil.urlEncode(EncryptUtil.xorInfo(account)) + "&password=" + EncryptUtil.urlEncode(EncryptUtil.xorInfo(password));
                    Cookie cookie = new Cookie(Constants.KEY_COOKIES_STATUS, value);
                    logger.info("value = " + value);
                    cookie.setMaxAge(864000);
                    this.response.addCookie(cookie);
                } else {
                    Cookie cookies[] = request.getCookies();
                    if (cookies != null)
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals(Constants.KEY_COOKIES_STATUS)) {
//                            response.r
                            }
                        }
                }
                RequestUtil.login(request, accountBean);
                AjaxBean ajax = new AjaxBean(Result.SUCCESS);
                ajax.put("id", accountBean.getId());
                return ajax;
            } else {
                return new AjaxBean(Result.FAIL);
            }
        } catch (Exception e) {
            throw new MessageException("request.common.information", e);
//            e.printStackTrace();
//            return new AjaxBean(Result.FAIL, messageConfig.getExceptionMessage());
        }
    }


    @RequestMapping(value = {"logout"}, method = {RequestMethod.GET})
    @ResponseBody
    public AjaxBean logout() {
        RequestUtil.logout(request);
        return new AjaxBean(Result.SUCCESS);
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String toRegister() {
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public AjaxBean register(AccountForm form) throws Exception {
        checkForm(form);
        if (!form.getPardon().equals(form.getPassword()))//前后密码不一致
            throw new MessageException("request.login.pardon");
        if (accountService.checkAccount(form.getUsername())) {//账号已存在
            throw new MessageException("request.login.account.exists");
        }
        AccountBean account = accountService.register(form.getNickname(), form.getUsername(), form.getPassword(), "1");
        if (account != null) {//返回创建的id
            AjaxBean ajaxBean = new AjaxBean(Result.SUCCESS);
            ajaxBean.put(Constants.KEY_JSON_ACCOUNT_ID, account.getId());
            //直接登录
            RequestUtil.login(request, account);
            return ajaxBean;
        }
        return new AjaxBean(Result.FAIL);
    }
}
