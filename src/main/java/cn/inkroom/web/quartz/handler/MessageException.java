package cn.inkroom.web.quartz.handler;

import cn.inkroom.web.quartz.enums.Result;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/9/1
 * @Time 21:55
 * @Descorption 消息传递异常
 */
public class MessageException extends Exception {
    private Exception exception;
    private Result result = Result.EXCEPTION;//消息类型
    private String[] other;//其余附带信息

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Exception exception) {
        super(message);
        this.exception = exception;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public MessageException(String message, Result result) {
        super(message);
        this.result = result;
    }

    public MessageException(String message, Exception exception, String[] other) {
        this(message, exception);
        this.other = other;
    }

    public MessageException(String message, String[] other,Result result) {
        this(message,result);
        this.other = other;
    }

    public String[] getOther() {
        return other;
    }


    public Exception getException() {
        return exception;
    }
}
