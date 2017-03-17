package com.naivor.app.domain.rxjava;

/**
 * 网络请求后台返回的错误处理
 *
 * Created by tianlai on 17-3-16.
 */

public class ApiException extends RuntimeException {

    private String msg;

    private int code;

    public ApiException(String message,int code) {
        super(message);

        this.msg=message;

        this.code=code;
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
