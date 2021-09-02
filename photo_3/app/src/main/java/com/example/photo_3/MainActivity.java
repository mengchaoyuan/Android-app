package com.example.photo_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cn.leancloud.LCUser;
import cn.leancloud.LeanCloud;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    Button b1,b2;
    TextView t1,t2;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LeanCloud.initialize(this, "p7232w1eMUztjeT6h5BGUKPE-9Nh9j0Va", "kLGRWL03Uj8eiHKB5OmB6TBT", "https://p7232w1e.lc-cn-e1-shared.com");

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        t1 = findViewById(R.id.username);
        t2 = findViewById(R.id.password);
        pb = findViewById(R.id.progressBar);

        pb.setVisibility(View.GONE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(login.this, "注册...", Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.VISIBLE);
                // 创建实例
                LCUser user = new LCUser();
                // 等同于 user.put("username", "Tom")
                String name = t1.getText().toString();
                String password = t2.getText().toString();

                if(name.equals("")||password.equals("")){
                    Toast.makeText(MainActivity.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                }
                else{
                    user.setUsername(name);
                    user.setPassword(password);
                    // 可选
//                user.setEmail("tom@leancloud.rocks");
//                user.setMobilePhoneNumber("+8618200008888");

                    // 设置其他属性的方法跟 LCObject 一样
                    user.put("gender", "secret");

                    user.signUpInBackground().subscribe(new Observer<LCUser>() {
                        public void onSubscribe(Disposable disposable) {}
                        public void onNext(LCUser user) {
                            // 注册成功
                            Toast.makeText(MainActivity.this, "注册成功。objectId：" + user.getObjectId(), Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                        }
                        public void onError(Throwable throwable) {
                            // 注册失败（通常是因为用户名已被使用）
                            Toast.makeText(MainActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                        }
                        public void onComplete() {}
                    });
                }

            }
        });

        b2.setOnClickListener(onclick);

    }


    OnClick onclick=new OnClick();


    class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            t1 = findViewById(R.id.username);
            t2 = findViewById(R.id.password);

            String name = t1.getText().toString();
            String password = t2.getText().toString();
//            Toast.makeText(login.this, "登录...", Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.VISIBLE);

            if(name.equals("")||password.equals("")){
                Toast.makeText(MainActivity.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                pb.setVisibility(View.GONE);
            }
            else{
                LCUser.logIn(name, password).subscribe(new Observer<LCUser>() {
                    public void onSubscribe(Disposable disposable) {}
                    public void onNext(LCUser user) {
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        Intent intent=new Intent(MainActivity.this, usewindow.class);
                        startActivity(intent);
                        // 登录成功
                    }
                    public void onError(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);

                        // 登录失败（可能是密码错误）
                    }
                    public void onComplete() {}
                });
            }

        }
    }
}