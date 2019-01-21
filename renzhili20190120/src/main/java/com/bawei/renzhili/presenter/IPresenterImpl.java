package com.bawei.renzhili.presenter;

import com.bawei.renzhili.model.IModelImpl;
import com.bawei.renzhili.model.MyCallBack;
import com.bawei.renzhili.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel=new IModelImpl();
    }

    @Override
    public void getRequeryData(String url, Class clazz) {
        iModel.getRequeryData(url, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                iView.showRequeryData(o);
            }
        });
    }

    @Override
    public void postRequeryData(String url, Map<String, String> map, Class clazz) {
        iModel.postRequeryData(url, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object o) {
                iView.showRequeryData(o);
            }
        });
    }
    public void onDatach(){
        if (iModel!=null){
            iModel=null;
        }
        if (iView!=null){
            iView=null;
        }
    }
}
