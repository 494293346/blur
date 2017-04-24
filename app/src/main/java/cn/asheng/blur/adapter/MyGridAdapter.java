package cn.asheng.blur.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.HashMap;

import cn.asheng.blur.R;
import cn.asheng.blur.base.BaseRecyclerAdapter;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class MyGridAdapter extends BaseRecyclerAdapter<String, MyGridAdapter.ViewHolder> {

    private Context context;

    public MyGridAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_my_grid, parent, false);
        return new ViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position, mDatas.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        TextView tvTitle;
        ImageView imgHead;
        View viewEmpty;
        View viewAll;
        String model;
        int position;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            imgHead = (ImageView) itemView.findViewById(R.id.imgHead);
            viewEmpty = itemView.findViewById(R.id.viewEmpty);
            viewAll = itemView.findViewById(R.id.view);
        }

        public void bind(int position, String model) {
            this.position = position;
            this.model = model;
            if (position == 0) {
                viewEmpty.setVisibility(View.VISIBLE);
            } else {
                viewEmpty.setVisibility(View.GONE);
            }
        }


        @Override
        public void onClick(View v) {
        }
    }

}

