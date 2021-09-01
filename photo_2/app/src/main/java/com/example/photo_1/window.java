package com.example.photo_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.LeanCloud;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class window extends AppCompatActivity {

    FloatingActionButton button;
    RecyclerView recyclerView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        Random rand = new Random();

        List<Beauty> students = new ArrayList<>();
        List<Beauty> datalist = new ArrayList<>();
        List<Integer> givenList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //线性布局Manager
//        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
//        recyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //网络布局Manager
//        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(this, 3);
        //给recyclerView设置LayoutManager
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        DemoAdapter adapter=new DemoAdapter(this,students);
        //设置adapter
        recyclerView.setAdapter(adapter);

        LCQuery<LCObject> query = new LCQuery<>("_File");
        query.whereNotEqualTo("url", "");
        query.findInBackground().subscribe(new Observer<List<LCObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<LCObject> findstudents) {
                // students 是包含满足条件的 Student 对象的数组

                for (int i=0;i<findstudents.size();i++){
                    datalist.add(new Beauty(""+i,findstudents.get(i).toJSONObject().getString("url")));
                    adapter.notifyDataSetChanged();
                    if(i<10){
                        students.add(datalist.get(i));
                    }
                }
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {}
        });




        button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(window.this, "我被点击了", Toast.LENGTH_SHORT).show();
                students.clear();
                givenList.clear();
                for(int i=0;i<10;i++){
                    int n = rand.nextInt(datalist.size());
                    if (givenList.contains(n)){
                        i--;
                        continue;
                    }
                    givenList.add(n);
                    students.add(datalist.get(n));
                }

                for (int i=0;i<10;i++){
                    students.add(datalist.get(givenList.get(i)));
                }

                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(window.this).resumeRequests();//恢复Glide加载图片
                }else {
                    Glide.with(window.this).pauseRequests();//禁止Glide加载图片
                }
            }
        });

    }








}