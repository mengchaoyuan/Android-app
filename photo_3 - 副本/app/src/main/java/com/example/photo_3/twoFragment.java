package com.example.photo_3;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.FileNotFoundException;

import cn.leancloud.LCFile;
import cn.leancloud.LCObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link twoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class twoFragment extends Fragment {

    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_FILE_CODE = 1000;
    String path="";
    Uri uri;

    ImageView imageView;
    TextView textView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public twoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment twoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static twoFragment newInstance(String param1, String param2) {
        twoFragment fragment = new twoFragment();
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
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = getView().findViewById(R.id.textView2);
        Button btn = (Button) getView().findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFile();
            }
        });

        Button button = getView().findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LCFile file = LCFile.withAbsoluteLocalPath(getFileNameByUri(uri), path);
                    file.saveInBackground().subscribe(new Observer<LCFile>() {
                        public void onSubscribe(Disposable disposable) {}
                        public void onNext(LCFile file) {
//                        System.out.println("文件保存完成。URL：" + file.getUrl());
                            Toast.makeText(getContext(), "文件保存完成。URL：" + file.getUrl(), Toast.LENGTH_SHORT).show();

                            LCObject testObject = new LCObject("userup");
                            testObject.put("url", file.getUrl());
                            testObject.put("username", "qdj");
                            testObject.saveInBackground().subscribe(new Observer<LCObject>() {
                                public void onSubscribe(Disposable disposable) {}
                                public void onNext(LCObject todo) {
                                    // 成功保存之后，执行其他逻辑
//                                System.out.println("保存成功。objectId：" + todo.getObjectId());
                                    Toast.makeText(getContext(), "保存成功。objectId：" + todo.getObjectId(), Toast.LENGTH_SHORT).show();
                                    textView.setText("上传成功");
                                }
                                public void onError(Throwable throwable) {
                                    // 异常处理
                                }
                                public void onComplete() {}
                            });

                        }

                        public void onError(Throwable throwable) {
                            // 保存失败，可能是文件无法被读取，或者上传过程中出现问题
                            Toast.makeText(getContext(), "保存失败，可能是文件无法被读取，或者上传过程中出现问题", Toast.LENGTH_SHORT).show();
                            textView.setText("上传失败");

                        }
                        public void onComplete() {}
                    });
                } catch (FileNotFoundException e) {

                    e.printStackTrace();
                }
            }
        });

        imageView = getView().findViewById(R.id.imageView4);
        Glide.with(getActivity()).load(path).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);

//        getView().findViewById(R.id.recylerview);


    }

    // 打开系统的文件选择器
    public void OpenFile() {
        // 指定类型
        String[] mimeTypes = {"*/*"};
        // String[] mimeTypes = {"application/octet-stream"}; // 指定bin类型
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        StringBuilder mimeTypesStr = new StringBuilder();
        for (String mimeType : mimeTypes) {
            mimeTypesStr.append(mimeType).append("|");
        }
        intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), REQUEST_FILE_CODE);
    }


    // 获取文件的真实路径

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FILE_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            Toast.makeText(getActivity(), "uri:"+uri, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, getPath(this,uri), Toast.LENGTH_SHORT).show();

            PickUtils pickUtils =new PickUtils();
            Path findpath =new Path();
//            Toast.makeText(this, pickUtils.getPath(this,uri), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
            path=findpath.getFileAbsolutePath(getActivity(),uri);
            textView.setText(path);
            Glide.with(getActivity()).load(path).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取文件名
     * @param uri uri
     * @return
     */
    public String getFileNameByUri(Uri uri) {
        String filename = "";
        Cursor returnCursor = getContext().getContentResolver().query(uri, null,
                null, null, null);
        if (returnCursor != null) {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            filename = returnCursor.getString(nameIndex);
            returnCursor.close();
        }
        return filename;
    }




}