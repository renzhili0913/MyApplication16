package com.bawei.renzhili.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bawei.renzhili.R;
import com.bawei.renzhili.bean.CartBean;
import com.bawei.renzhili.view.SubAddView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildeAdapter extends RecyclerView.Adapter<ChildeAdapter.ViewHolder> {


    private Context context;
    private List<CartBean.DataBean.ListBean> list;

    public ChildeAdapter(Context context, List<CartBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_childe_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String replace = list.get(i).getImages().split("\\|")[0].replace("https", "http");
        Uri uri = Uri.parse(replace);
        viewHolder.image.setImageURI(uri);
        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.price.setText("¥"+list.get(i).getPrice());
        viewHolder.subaddview.setData(this,list,i);
        viewHolder.subaddview.setHttpListener(new SubAddView.HttpListener() {
            @Override
            public void callBack() {
                if (listener!=null){
                    listener.callBack();
                }
            }
        });
        viewHolder.childeCheck.setChecked(list.get(i).isIscheck());
        viewHolder.childeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(i).setIscheck(isChecked);
                if (listener!=null){
                    listener.callBack();
                }
            }
        });
    }
    public void select(boolean bool){
        for (int i =0;i<list.size();i++){
            list.get(i).setIscheck(bool);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.childe_check)
        CheckBox childeCheck;
        @BindView(R.id.image)
        SimpleDraweeView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.subaddview)
        SubAddView subaddview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    HttpListener listener;
    public void setHttpListener(HttpListener listener){
        this.listener=listener;
    }
    public interface HttpListener{
        void callBack();
    }
}
