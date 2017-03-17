package com.naivor.app.data.remote.ApiResponce;

import java.util.List;

/**
 * Home 页面的返回结果
 * Created by naivor on 16-5-21.
 */
public class HomeData {
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HomeData{" +
                "list=" + list +
                '}';
    }
}
