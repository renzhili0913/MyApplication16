package com.bawei.renzhili.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.renzhili.R;
import com.bawei.renzhili.adapter.ChildeAdapter;
import com.bawei.renzhili.bean.CartBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubAddView extends LinearLayout {
    @BindView(R.id.sub)
    TextView sub;
    @BindView(R.id.num)
    TextView numtext;
    @BindView(R.id.add)
    TextView add;
    private Context context;
    private int num;
    private ChildeAdapter childeAdapter;
    private int i;
    private List<CartBean.DataBean.ListBean> list;
    public SubAddView(Context context) {
        super(context);
        init(context);
    }

    public SubAddView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SubAddView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.num_item, null);
        ButterKnife.bind(this,view);
        addView(view);
    }

    @OnClick({R.id.sub, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sub:
                if (num>1){
                    num--;
                }else{
                    Toast.makeText(context,"最少为1",Toast.LENGTH_SHORT).show();
                }
                list.get(i).setNum(num);
                childeAdapter.notifyDataSetChanged();
                numtext.setText(num+"");
                if (listener!=null){
                    listener.callBack();
                }
                break;
            case R.id.add:
                num++;
                list.get(i).setNum(num);
                childeAdapter.notifyDataSetChanged();
                numtext.setText(num+"");
                if (listener!=null){
                    listener.callBack();
                }
                break;
        }
    }
    public void setData(ChildeAdapter childeAdapter, List<CartBean.DataBean.ListBean> list, int i){
        this.childeAdapter=childeAdapter;
        this.list=list;
        this.i=i;
        num=list.get(i).getNum();
        numtext.setText(num+"");
    }
    HttpListener listener;
    public void setHttpListener(HttpListener listener){
        this.listener=listener;
    }
    public interface HttpListener{
        void callBack();
    }
}
