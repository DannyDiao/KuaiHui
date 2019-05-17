package com.dannydiao.kuaihui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    ExchangeFragment exchangeFragment = ExchangeFragment.newInstance("Context","Context");
    RateFragment rateFragment = RateFragment.newInstance("Context","Context");
    Fragment currentFragment = exchangeFragment;

    public int Count = 0;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_exchange:
                    showFragment(exchangeFragment);
                    Count++;
                    return true;
                case R.id.bottom_rate:
                    showFragment(rateFragment);
                    return true;

            }

            return false;
        }
    };

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction1 = fragmentManager.beginTransaction();
        transaction1.add(R.id.fragment_layout,exchangeFragment);
        transaction1.commit();

        Boolean net = isNetworkConnected(getApplicationContext());
        if (net == false){
            Toast.makeText(getApplicationContext(),"无网络连接，请重试后重启应用",Toast.LENGTH_LONG).show();
        }
    }

    private void showFragment(Fragment fg){

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //如果之前没有添加过
        if(!fg.isAdded()){
            transaction
                    .add(R.id.fragment_layout,fg)
                    .hide(currentFragment);
        }else{
            transaction
                    .hide(currentFragment)
                    .show(fg);
        }

        //全局变量，记录当前显示的fragment
        currentFragment = fg;
        //transaction.addToBackStack(null);
        transaction.commit();

    }

}
