package com.example.photo_3;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
    Dialog dia;
    public int xpath;

    public DemoAdapter(Context context, List<Beauty> data) {
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

        Glide.with(this.mContext).load(beauty.getImageId()).into(holder.beautyImage);
//        Glide.with(this.mContext).load(beauty.getImageId()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.beautyImage);

        holder.nameTv.setText(beauty.getName());
        holder.beautyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dia = new Dialog(mContext, R.style.edit_AlertDialog_style);
                dia.setContentView(R.layout.activity_start_dialog);
                ImageView imageView = (ImageView) dia.findViewById(R.id.imageView2);
                Glide.with(mContext).load(beauty.getImageId()).into(imageView);
//                imageView.setBackgroundResource(R.mipmap.iv_android);
                dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
                Window w = dia.getWindow();
                WindowManager.LayoutParams lp = w.getAttributes();
                lp.x = 0;
                lp.y = 40;
                dia.onWindowAttributesChanged(lp);
                imageView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dia.dismiss();
                            }
                        });
                dia.show();
                Toast.makeText(mContext, position+"", Toast.LENGTH_SHORT).show();
            }
        });
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
//            params.height =  400 ;

            beautyImage.setLayoutParams(params);


        }
    }



}
