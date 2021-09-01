package com.example.photo_1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.BeautyViewHolder> {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据集合
     */
    private List<Beauty> data;


    public DemoAdapter(Context context,List<Beauty> data) {
        this.data = data;
        this.mContext = context;
    }

    @Override
    public BeautyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载item 布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_item, parent, false);
        return new BeautyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BeautyViewHolder holder, int position) {
        //将数据设置到item上
        Beauty beauty = data.get(position);
//        holder.beautyImage.setImageResource(beauty.getImageId());
        Glide.with(this.mContext).load(beauty.getImageId()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.beautyImage);

        holder.nameTv.setText(beauty.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class BeautyViewHolder extends RecyclerView.ViewHolder {
        ImageView beautyImage;
        TextView nameTv;




        public BeautyViewHolder(View itemView) {
            super(itemView);
//            beautyImage = itemView.findViewById(R.id.imageView);
            nameTv = itemView.findViewById(R.id.textView);
            beautyImage = itemView.findViewById(R.id.imageView);

            int width = ((Activity) beautyImage.getContext()).getWindowManager().getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams params = beautyImage.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = width/3;
            params.height =  (int) (200 + Math.random() * 400) ;
            beautyImage.setLayoutParams(params);

        }
    }
}
