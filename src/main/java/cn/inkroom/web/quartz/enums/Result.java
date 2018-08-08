package cn.inkroom.web.quartz.enums;

public enum Result {
    SUCCESS,
    FAIL,
    FILE_EXISTS,
    ALBUM_NOT_EXISTS,
    DIR_EXISTS,
    PARAM_NOT_SUIT,//参数格式不正确
    LOGIN_NOT,//登陆失效或者未登录
    NO_AUTHORITY,//没有操作权限
    LOGIN_FAIL,
    LOGIN_EXISTS,//已经异地登陆
    FILE_NOT_EXISTS,
    EXCEPTION,//发生异常
    ANSWER_ERROR;//答案错误

    private Result() {
    }
}
