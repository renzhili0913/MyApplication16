package com.bawei.renzhili.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.renzhili.Apis;
import com.bawei.renzhili.R;
import com.bawei.renzhili.adapter.ShopAdapter;
import com.bawei.renzhili.bean.CartBean;
import com.bawei.renzhili.presenter.IPresenterImpl;
import com.bawei.renzhili.view.IView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CarFragment extends Fragment implements IView {
    @BindView(R.id.shop_recyclerview)
    RecyclerView shopRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.all_check)
    CheckBox allCheck;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.total_num)
    TextView totalNum;
    private IPresenterImpl iPresenter;
    private ShopAdapter shopAdapter;
    private CartBean cartBean;
    private List<CartBean.DataBean> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_view, container, false);
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
        Map<String, String> map = new HashMap<>();
        map.put("uid", String.valueOf(71));
        iPresenter.postRequeryData(Apis.CART_URL, map, CartBean.class);
    }

    private void initView() {
        iPresenter = new IPresenterImpl(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shopRecyclerview.setLayoutManager(linearLayoutManager);
        //创建适配器
        shopAdapter = new ShopAdapter(getActivity());
        shopRecyclerview.setAdapter(shopAdapter);
        shopAdapter.setHttpListener(new ShopAdapter.HttpListener() {
            @Override
            public void callBack(List<CartBean.DataBean> list) {
                double total_price = 0;
                int total_num = 0;
                int num = 0;
                for (int i = 0; i < list.size(); i++) {
                    List<CartBean.DataBean.ListBean> listbean = list.get(i).getList();
                    for (int j = 0; j < listbean.size(); j++) {
                        total_num += listbean.get(j).getNum();
                        if (listbean.get(j).isIscheck()) {
                            num += listbean.get(j).getNum();
                            total_price += listbean.get(j).getNum() * listbean.get(j).getPrice();
                        }
                    }
                }
                if (num < total_num) {
                    allCheck.setChecked(false);
                } else {
                    allCheck.setChecked(true);
                }
                totalNum.setText("结算（" + num + ")");
                totalPrice.setText("合计：¥" + total_price);
            }
        });

    }

    @Override
    public void showRequeryData(Object o) {
        if (o instanceof CartBean) {
            cartBean = (CartBean) o;
            if (cartBean != null && cartBean.isSuccess()) {
                //传值适配器
                data = cartBean.getData();
                data.remove(0);
                shopAdapter.setList(data);
            }
            Toast.makeText(getActivity(), cartBean.getMsg(), Toast.LENGTH_SHORT).show();
        } else if (o instanceof String) {
            String s = (String) o;
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.all_check)
    public void onViewClicked() {
        seelect(allCheck.isChecked());
        shopAdapter.notifyDataSetChanged();
    }

    private void seelect(boolean checked) {
        double total_price=0;
        int num=0;
        for (int i=0;i<data.size();i++){
            data.get(i).setIscheck(checked);
            List<CartBean.DataBean.ListBean> listbean = data.get(i).getList();
            for (int j=0;j<listbean.size();j++){
                listbean.get(j).setIscheck(checked);
                total_price+=listbean.get(j).getPrice()*listbean.get(j).getNum();
                num+=listbean.get(j).getNum();
            }
        }
        if (checked){
            totalNum.setText("结算（" + num + ")");
            totalPrice.setText("合计：¥" + total_price);
        }else{
            totalNum.setText("结算（0)");
            totalPrice.setText("合计：¥0.0" );
        }
    }
}
