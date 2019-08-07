package com.dannydiao.kuaihui.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.dannydiao.kuaihui.R;

public class LoadingView extends AppCompatActivity {


    Context context = this;
    @Override
    public void finish() {
        super.finish();
        ((Activity)context).overridePendingTransition(0,0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie);
        lottieAnimationView.setSpeed((float)1.8);
    }
}
