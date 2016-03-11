package com.naivor.app.data.remote.ApiResponce;

/**
 * 登录请求返回的结果
 * <p/>
 * Created by tianlai on 16-3-8.
 */
public class LoginResponce extends BaseResponce {

    private String image;

    private String name;

    private String oppointForm;

    private String finishedForm;

    private String totalMoney;

    private boolean existComment;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setOppointForm(String oppointForm) {
        this.oppointForm = oppointForm;
    }

    public String getOppointForm() {
        return this.oppointForm;
    }

    public void setFinishedForm(String finishedForm) {
        this.finishedForm = finishedForm;
    }

    public String getFinishedForm() {
        return this.finishedForm;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotalMoney() {
        return this.totalMoney;
    }

    public void setExistComment(boolean existComment) {
        this.existComment = existComment;
    }

    public boolean getExistComment() {
        return this.existComment;
    }

}
