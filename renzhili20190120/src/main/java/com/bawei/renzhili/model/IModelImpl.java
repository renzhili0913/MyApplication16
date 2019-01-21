package com.bawei.renzhili.model;

import com.bawei.renzhili.utils.RetrofitUtils;
import com.google.gson.Gson;

import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void getRequeryData(String url, final Class clazz, final MyCallBack myCallBack) {
        RetrofitUtils.getmInsaner().get(url, new RetrofitUtils.HttpListener() {
            @Override
            public void success(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack!=null){
                    myCallBack.setData(o);
                }
            }

            @Override
            public void failde(String error) {
                if (myCallBack!=null){
                    myCallBack.setData(error);
                }
            }
        });
    }

    @Override
    public void postRequeryData(String url, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {
        RetrofitUtils.getmInsaner().post(url, map, new RetrofitUtils.HttpListener() {
            @Override
            public void success(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack!=null){
                    myCallBack.setData(o);
                }
            }

            @Override
            public void failde(String error) {
                if (myCallBack!=null){
                    myCallBack.setData(error);
                }
            }
        });
    }
}
