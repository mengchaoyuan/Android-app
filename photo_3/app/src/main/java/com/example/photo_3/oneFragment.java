package com.example.photo_3;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link oneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class oneFragment extends Fragment {


    FloatingActionButton button;
    RecyclerView recyclerView;
    ImageView imageView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public oneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment oneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static oneFragment newInstance(String param1, String param2) {
        oneFragment fragment = new oneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Random rand = new Random();

        List<Beauty> students = new ArrayList<>();
        List<Beauty> datalist = new ArrayList<>();
        List<Integer> givenList = new ArrayList<>();
        recyclerView = (RecyclerView) getView().findViewById(R.id.recylerview);
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
        DemoAdapter adapter=new DemoAdapter(getContext(),students);
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




        button = getView().findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast.makeText(window.this, "我被点击了", Toast.LENGTH_SHORT).show();
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
                    try {
                        Glide.with((Activity) oneFragment.this.clone()).resumeRequests();//恢复Glide加载图片
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        Glide.with((Activity) oneFragment.this.clone()).pauseRequests();//禁止Glide加载图片
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}