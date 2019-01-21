package com.bawei.renzhili;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.renzhili.bean.AddCartBean;
import com.bawei.renzhili.bean.DetailsBean;
import com.bawei.renzhili.presenter.IPresenterImpl;
import com.bawei.renzhili.view.IView;
import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity implements IView {
    @BindView(R.id.xbanner)
    XBanner xbanner;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.add_cart)
    TextView addCart;
    @BindView(R.id.purchase)
    TextView purchase;
    private IPresenterImpl iPresenter;
    private String pid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        //请求数据
        Map<String, String> map = new HashMap<>();
        map.put("pid", pid);
        iPresenter.postRequeryData(Apis.SHOP_URL, map, DetailsBean.class);
    }

    private void initView() {

    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof DetailsBean) {
            DetailsBean detailsBean = (DetailsBean) o;
            if (detailsBean != null && detailsBean.isSuccess()) {
                List<String> image= new ArrayList<>();
                String[] split = detailsBean.getData().getImages().split("\\|");
                for (int i =0;i<split.length;i++){
                    image.add(split[i]);
                }
                xbanner.setData(image,null);
                xbanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        String s = (String) model;
                        Glide.with(DetailsActivity.this).load(s).into((ImageView) view);
                    }
                });
                title.setText(detailsBean.getData().getTitle());
                price.setText("¥"+detailsBean.getData().getPrice());

            }
            Toast.makeText(DetailsActivity.this, detailsBean.getMsg(), Toast.LENGTH_SHORT).show();
        }else if(o instanceof AddCartBean){
            AddCartBean addCartBean = (AddCartBean) o;
            Toast.makeText(DetailsActivity.this, addCartBean.getMsg(), Toast.LENGTH_SHORT).show();
        } else if (o instanceof String) {
            String s = (String) o;
            Toast.makeText(DetailsActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.add_cart, R.id.purchase})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_cart:
                Map<String,String> map = new HashMap<>();
                map.put("uid",String.valueOf(71));
                map.put("pid",pid);
                iPresenter.postRequeryData(Apis.ADD_CART_URL,map,AddCartBean.class);
                break;
            case R.id.purchase:
                break;
        }
    }
}
