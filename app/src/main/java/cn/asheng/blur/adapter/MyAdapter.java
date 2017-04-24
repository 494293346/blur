package cn.asheng.blur.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.asheng.blur.R;
import cn.asheng.blur.base.BaseAdapter;
import cn.asheng.blur.model.MyModel;
import cn.asheng.blur.utils.Utils;
import jp.wasabeef.glide.transformations.BlurTransformation;


public class MyAdapter extends BaseAdapter<MyModel> {
    private float marginLeft;//距离左边距离

    public MyAdapter(Context context) {
        super(context);
        init();
        marginLeft = Utils.dip2px(context, 179);
    }

    @Override
    public void init() {

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_my, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyModel bean = getItem(position);
        //设置图片
        dealWithImg(holder, bean.getBg());
        //设置适配器
        dealWithLinear(holder, bean);
        //处理模糊效果
        dealWithMohu(holder, position);

        return convertView;
    }

    private void dealWithLinear(ViewHolder holder, MyModel bean) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        if (bean.getItems()!= null) {
            MyGridAdapter gzhHomeGridAdapter = new MyGridAdapter(context);
            holder.recyclerView.setAdapter(gzhHomeGridAdapter);
            gzhHomeGridAdapter.updateItems(bean.getItems());
        }
    }

    private void dealWithImg(ViewHolder holder, int bean) {
        Glide
                .with(context)
                .load(bean)
                .placeholder(R.mipmap.loading_spinner_long)
                .crossFade()
                .into(holder.imgBg);
        Glide
                .with(context)
                .load(bean)
                .placeholder(R.mipmap.loading_spinner_long)
                .crossFade()
                .bitmapTransform(new BlurTransformation(context,25))
                .into(holder.imgBgMohu);
    }

    private void dealWithMohu(final ViewHolder holder, final int position) {
        //透明度为0
        holder.imgBgMohu.setAlpha(0.0f);
        holder.recyclerView.setTag(position);
        holder.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDx = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDx -= dx;
                int pos = (int) recyclerView.getTag();
                float move = Math.abs(totalDx);
                if (position == pos && move < marginLeft && move > 1) {
                    float alpha = move / 500;
                    holder.imgBgMohu.setAlpha(Math.min(alpha, 1));
                }
            }
        });
    }

    class ViewHolder {
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.imgBg)
        ImageView imgBg;
        @BindView(R.id.imgBgMohu)
        ImageView imgBgMohu;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
