package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import cn.leancloud.LCFile;
import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.LeanCloud;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_FILE_CODE = 1000;
    TextView tv;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LeanCloud.initialize(this, "p7232w1eMUztjeT6h5BGUKPE-9Nh9j0Va", "kLGRWL03Uj8eiHKB5OmB6TBT", "https://p7232w1e.lc-cn-e1-shared.com");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.editTextTextPersonName);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFile();
            }
        });

        imageView = findViewById(R.id.imageView);
        Glide.with(MainActivity.this).load("http://lc-p7232w1e.cn-e1.lcfile.com/DswBHPJDJc8EwRfApaSTEGSoiAy6npwc/IMG_20210829_035528.jpg").skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
        tv.setText("????????????");

//        LCQuery<LCObject> query = new LCQuery<>("_File");
//        query.whereNotEqualTo("url", "");
//        query.findInBackground().subscribe(new Observer<List<LCObject>>() {
//            public void onSubscribe(Disposable disposable) {}
//            public void onNext(List<LCObject> students) {
//                // students ???????????????????????? Student ???????????????
//
//                for (int i=0;i<students.size();i++){
//                    Toast.makeText(MainActivity.this, students.get(i).toJSONObject().getString("url"), Toast.LENGTH_SHORT).show();
//                    Glide.with(MainActivity.this).load(students.get(i).toJSONObject().getString("url")).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
//                }
////                Glide.with(MainActivity.this).load(students.get(5).toJSONObject().getString("url")).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
//            }
//            public void onError(Throwable throwable) {}
//            public void onComplete() {}
//        });

    }

    // ??????????????????????????????
    public void OpenFile() {
        // ????????????
        String[] mimeTypes = {"*/*"};
        // String[] mimeTypes = {"application/octet-stream"}; // ??????bin??????
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        StringBuilder mimeTypesStr = new StringBuilder();
        for (String mimeType : mimeTypes) {
            mimeTypesStr.append(mimeType).append("|");
        }
        intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), REQUEST_FILE_CODE);
    }


    // ???????????????????????????

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FILE_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Toast.makeText(this, "uri:"+uri, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, getPath(this,uri), Toast.LENGTH_SHORT).show();

            PickUtils pickUtils =new PickUtils();
            Path findpath =new Path();
//            Toast.makeText(this, pickUtils.getPath(this,uri), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
            String path=findpath.getFileAbsolutePath(this,uri);

//            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
//            path = path.substring(0,path.lastIndexOf("/")) + "/" + getFileNameByUri(uri);
//            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            tv.setText(path);


            try {
                LCFile file = LCFile.withAbsoluteLocalPath(getFileNameByUri(uri), path);
                file.saveInBackground().subscribe(new Observer<LCFile>() {
                    public void onSubscribe(Disposable disposable) {}
                    public void onNext(LCFile file) {
//                        System.out.println("?????????????????????URL???" + file.getUrl());
                        Toast.makeText(MainActivity.this, "?????????????????????URL???" + file.getUrl(), Toast.LENGTH_SHORT).show();

                        LCObject testObject = new LCObject("userup");
                        testObject.put("url", file.getUrl());
                        testObject.put("username", "qdj");
                        testObject.saveInBackground().subscribe(new Observer<LCObject>() {
                            public void onSubscribe(Disposable disposable) {}
                            public void onNext(LCObject todo) {
                                // ???????????????????????????????????????
//                                System.out.println("???????????????objectId???" + todo.getObjectId());
                                Toast.makeText(MainActivity.this, "???????????????objectId???" + todo.getObjectId(), Toast.LENGTH_SHORT).show();
                            }
                            public void onError(Throwable throwable) {
                                // ????????????
                            }
                            public void onComplete() {}
                        });

                        tv.setText("?????????");
                    }

                    public void onError(Throwable throwable) {
                        // ?????????????????????????????????????????????????????????????????????????????????
                        Toast.makeText(MainActivity.this, "?????????????????????????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                        tv.setText("?????????");
                    }
                    public void onComplete() {}
                });
            } catch (FileNotFoundException e) {
                tv.setText("?????????????????????");
                tv.setText(e.toString());
                e.printStackTrace();
            }


            //String filePath = PickUtils.getPath(this, uri);
            //textView.setText(filePath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ???????????????
     * @param uri uri
     * @return
     */
    public String getFileNameByUri(Uri uri) {
        String filename = "";
        Cursor returnCursor = getContentResolver().query(uri, null,
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