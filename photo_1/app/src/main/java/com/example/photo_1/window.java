package com.example.photo_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.LeanCloud;

public class window extends AppCompatActivity {

    FloatingActionButton button;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);


        List<Beauty> students = new ArrayList<>();

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

        for(int i=0;i<10;i++){
//            students.add(new Beauty("不知道", "https://api.ixiaowai.cn/gqapi/gqapi.php"));
            students.add(new Beauty("不知道", "https://api.dongmanxingkong.com/suijitupian/acg/1080p/index.php"));
            students.add(new Beauty("不知道", "https://www.chaojiying.com/include/code/code.php?u=1"));
        }

        button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(window.this, "我被点击了", Toast.LENGTH_SHORT).show();
                students.clear();

                for(int i=0;i<10;i++){
//            students.add(new Beauty("不知道", "https://api.ixiaowai.cn/gqapi/gqapi.php"));
                    students.add(new Beauty("不知道", "https://api.dongmanxingkong.com/suijitupian/acg/1080p/index.php"));
                    students.add(new Beauty("不知道", "https://www.chaojiying.com/include/code/code.php?u=1"));
                }
                adapter.notifyDataSetChanged();
            }
        });





//        recyclerView =findViewById(R.id.recylerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,  StaggeredGridLayoutManager.VERTICAL));
//        DemoAdapter adapter=new DemoAdapter(this,students);
//        recyclerView.setAdapter(adapter);
    }







}