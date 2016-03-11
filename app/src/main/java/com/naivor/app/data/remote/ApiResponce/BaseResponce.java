package com.naivor.app.data.remote.ApiResponce;

/**
 * BaseResponce 请求返回的基类，包含公共部分，比如返回码，返回消息等等
 *
 * Created by tianlai on 16-3-8.
 */
public  class BaseResponce {
    protected int respCode;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }
}
