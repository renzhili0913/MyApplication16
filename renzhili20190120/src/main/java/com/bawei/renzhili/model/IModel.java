package com.bawei.renzhili.model;

import java.util.Map;

public interface IModel {
    void getRequeryData(String url,Class clazz,MyCallBack myCallBack);
    void postRequeryData(String url, Map<String,String> map,Class clazz, MyCallBack myCallBack);
}
