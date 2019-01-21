package com.bawei.renzhili.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bawei.renzhili.R;
import com.bawei.renzhili.bean.HomeBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JDAdapter extends RecyclerView.Adapter<JDAdapter.ViewHolder> {

    private Context context;
    private List<HomeBean.DataBean.MiaoshaBean.ListBean> list;

    public JDAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<HomeBean.DataBean.MiaoshaBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_jd_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        String replace = list.get(0).getImages().split("\\|")[0].replace("https", "http");
        Uri uri = Uri.parse(replace);
        viewHolder.image.setImageURI(uri);
        viewHolder.price.setText(list.get(0).getPrice()+"");
        viewHolder.bargainPrice.setText(list.get(0).getBargainPrice()+"");
        viewHolder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click!=null){
                    click.onClick(list.get(i).getPid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        SimpleDraweeView image;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.bargainPrice)
        TextView bargainPrice;
        @BindView(R.id.layout_item)
        LinearLayout layout_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    Click click;
    public void setOnClickListener(Click click){
        this.click=click;
    }
    public interface Click{
        void onClick(int pid);
    }
}
