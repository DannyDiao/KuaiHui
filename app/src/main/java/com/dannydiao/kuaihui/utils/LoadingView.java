package com.dannydiao.kuaihui.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.dannydiao.kuaihui.R;

public class LoadingView extends AppCompatActivity {

    //获取当前Activity实例
    Context context = this;
    @Override
    public void finish() {
        super.finish();
        //取消LoadingView的退出动画
        ((Activity)context).overridePendingTransition(0,0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);

        //设置等待时间
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie);
        lottieAnimationView.setSpeed((float) 2.3);
        new Handler().postDelayed(this::finish, 1500);    //延时1s执行


    }
}
