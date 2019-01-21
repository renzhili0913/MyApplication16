package com.bawei.renzhili.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bawei.renzhili.R;
import com.bawei.renzhili.bean.CartBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private Context context;
    private List<CartBean.DataBean> list;

    public ShopAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<CartBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_shop_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(list.get(i).getSellerName());
        viewHolder.shopcheck.setChecked(list.get(i).isIscheck());
        //创建商品适配器
        getItemView(viewHolder,i);
    }

    private void getItemView(final ViewHolder viewHolder, final int i) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.cRecyclerview.setLayoutManager(linearLayoutManager);
        //创建适配器
        final ChildeAdapter childeAdapter = new ChildeAdapter(context,list.get(i).getList());
        viewHolder.cRecyclerview.setAdapter(childeAdapter);
        viewHolder.shopcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(i).setIscheck(viewHolder.shopcheck.isChecked());
                childeAdapter.select(viewHolder.shopcheck.isChecked());
            }
        });
        childeAdapter.setHttpListener(new ChildeAdapter.HttpListener() {
            @Override
            public void callBack() {
                if (listener!=null){
                    listener.callBack(list);
                }
                List<CartBean.DataBean.ListBean> listbean = ShopAdapter.this.list.get(i).getList();
                boolean isbool=true;
                for (int i =0;i<listbean.size();i++){
                    if (!listbean.get(i).isIscheck()){
                        isbool=false;
                        break;
                    }
                }
                viewHolder.shopcheck.setChecked(isbool);
                list.get(i).setIscheck(isbool);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shopcheck)
        CheckBox shopcheck;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.c_recyclerview)
        RecyclerView cRecyclerview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    HttpListener listener;
    public void setHttpListener(HttpListener listener){
        this.listener=listener;
    }
    public interface HttpListener{
        void callBack(List<CartBean.DataBean> list);
    }
}
