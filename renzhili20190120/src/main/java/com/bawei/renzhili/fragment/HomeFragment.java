package com.bawei.renzhili.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.renzhili.Apis;
import com.bawei.renzhili.DetailsActivity;
import com.bawei.renzhili.R;
import com.bawei.renzhili.adapter.ClassAdapter;
import com.bawei.renzhili.adapter.JDAdapter;
import com.bawei.renzhili.bean.HomeBean;
import com.bawei.renzhili.presenter.IPresenterImpl;
import com.bawei.renzhili.view.IView;
import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements IView {
    @BindView(R.id.xbanner)
    XBanner xbanner;
    @BindView(R.id.h_recyclerview)
    RecyclerView hRecyclerview;
    @BindView(R.id.m_recyclerview)
    RecyclerView mRecyclerview;
    Unbinder unbinder;
    private IPresenterImpl iPresenter;
    private ClassAdapter classAdapter;
    private JDAdapter jdAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        //请求数据
     iPresenter.getRequeryData(Apis.HOME_URL,HomeBean.class);
    }

    private void initView() {
        iPresenter=new IPresenterImpl(this);
        //获取分类布局
        getFenlenView();
        //获取京东秒杀
        getJDView();
    }

    private void getJDView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerview.setLayoutManager(gridLayoutManager);
        //创建适配器
        jdAdapter = new JDAdapter(getActivity());
        mRecyclerview.setAdapter(jdAdapter);
        //点击事件
        jdAdapter.setOnClickListener(new JDAdapter.Click() {
            @Override
            public void onClick(int pid) {
                Intent intent = new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("pid",String.valueOf(pid));
                startActivity(intent);
                //startActivityForResult(intent,100);
            }
        });
    }

    private void getFenlenView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        hRecyclerview.setLayoutManager(gridLayoutManager);
        //创建适配器
        classAdapter = new ClassAdapter(getActivity());
        hRecyclerview.setAdapter(classAdapter);
    }

    @Override
    public void showRequeryData(Object o) {
        if(o instanceof  HomeBean){
            HomeBean homeBean = (HomeBean) o;
            if (homeBean!=null&&homeBean.isSuccess()){
                //传值到banner
                xbanner.setData(homeBean.getData().getBanner(),null);
                xbanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        HomeBean.DataBean.BannerBean bannerBean = (HomeBean.DataBean.BannerBean) model;
                        Glide.with(getActivity()).load(bannerBean.getIcon()).into((ImageView) view);
                    }
                });
                //TODO 产值适配器
                classAdapter.setList(homeBean.getData().getFenlei());
                jdAdapter.setList(homeBean.getData().getMiaosha().getList());
            }
            Toast.makeText(getActivity(),homeBean.getMsg(),Toast.LENGTH_SHORT).show();
        }else if(o instanceof String){
            String s= (String) o;
            Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null) {
            unbinder.unbind();
        }
        iPresenter.onDatach();
    }


}
