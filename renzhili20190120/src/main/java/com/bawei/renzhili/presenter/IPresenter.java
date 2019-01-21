package com.bawei.renzhili.presenter;

import com.bawei.renzhili.model.MyCallBack;

import java.util.Map;

public interface IPresenter {
    void getRequeryData(String url, Class clazz);
    void postRequeryData(String url, Map<String, String> map, Class clazz);
}
