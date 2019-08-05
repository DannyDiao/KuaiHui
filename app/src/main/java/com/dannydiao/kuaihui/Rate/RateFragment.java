package com.dannydiao.kuaihui.Rate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dannydiao.kuaihui.AboutActivity;
import com.dannydiao.kuaihui.Utils.DividerItemDecoration;
import com.dannydiao.kuaihui.R;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RateFragment extends Fragment {
    String MainUrl = "https://diaosudev.cn:3500/KuaiHuiRate";
    String GetRefreshTimeUrl = "https://diaosudev.cn:3500/KuaiHuiRateTime";

    List<String> Title = new ArrayList<>();
    List<String> Current = new ArrayList<>();
    List<String> Hint = new ArrayList<>();
    RecyclerView recyclerView;


    public RateFragment() {
        // Required empty public constructor
    }

    public static RateFragment newInstance(String param1, String param2) {
        RateFragment fragment = new RateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Call AssembleCall(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return okHttpClient.newCall(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rate, container, false);

        //绑定关于TextView
        FloatingActionButton fab_info = v.findViewById(R.id.float_button_info);
        //TextView about = v.findViewById(R.id.about);
        //about.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        about.setOnClickListener(v12 -> {
//            Intent intent = new Intent(getActivity(),AboutActivity.class);
//            startActivity(intent);
//        });
        fab_info.setOnClickListener(v12 -> {
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
        });

        initTitle();
        initCurrency();
        TextView refresh_time = v.findViewById(R.id.refresh_time_2);
        recyclerView = v.findViewById(R.id.recycler_view_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RateListAdapter rateListAdapter = new RateListAdapter(Title, Current, Hint);
        recyclerView.setAdapter(rateListAdapter);
        if (getActivity() != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        }
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    rateListAdapter.notifyDataSetChanged();
                    if (msg.obj != null) {
                        refresh_time.setText(msg.obj.toString());

                    }
                }
            }
        };

        Call call_final = AssembleCall(MainUrl);
        call_final.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                long StartTime = System.currentTimeMillis();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Current.remove(i);
                        Current.add(i, jsonArray.getString(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                    //解析出刷新时间
//                    String[] refresh_time_split = result.split("\"update\":\"");
//                    String refresh_time1 = refresh_time_split[1].substring(0, 19);
                //发送消息到主线程，通知刷新UI
                Message message = handler.obtainMessage();
                message.what = 1;
//                    message.obj = refresh_time1;
                handler.sendMessage(message);
                long EndTime = System.currentTimeMillis();
                long RunningTime = EndTime - StartTime;

                Log.d("RunningTime_0", String.valueOf(StartTime));
                Log.d("RunningTime_1", String.valueOf(EndTime));
                Log.d("RunningTime_2", String.valueOf(RunningTime));

            }
        });

        Call GetRefreshTimeCall = AssembleCall(GetRefreshTimeUrl);
        GetRefreshTimeCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String refresh_time = response.body().string();
                //发送消息到主线程，通知刷新UI
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = refresh_time;
                handler.sendMessage(message);
            }
        });

        FloatingActionButton floatingActionButton = v.findViewById(R.id.float_button);
        floatingActionButton.setOnClickListener(v1 -> {
            Call refresh_call = AssembleCall(MainUrl);
            refresh_call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();

                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Current.remove(i);
                            Current.add(i, jsonArray.getString(i));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    //解析出刷新时间
//                    String[] refresh_time_split = result.split("\"update\":\"");
//                    String refresh_time1 = refresh_time_split[1].substring(0, 19);
                    //发送消息到主线程，通知刷新UI
                    Message message = handler.obtainMessage();
                    message.what = 1;
//                    message.obj = refresh_time1;
                    handler.sendMessage(message);
                }
            });
        });


        return v;
    }

    public void initTitle() {

        Title.add("美元 USD");
        Title.add("港币 HKD");
        Title.add("欧元 EUR");
        Title.add("日元 JPY");
        Title.add("英镑 GBP");
        Title.add("韩元 KRW");
        Title.add("加拿大元 CAD");
        Title.add("澳大利亚元 AUD");
        Title.add("新台币 TWD");
        Title.add("新加坡元 SGD");
        Title.add("泰铢 THB");
        Title.add("澳门元 MOP");
        Title.add("越南盾 VND");
        Title.add("新西兰元 NZD");
        Title.add("瑞士法郎 CHF");
        Title.add("菲律宾比索 PHP");

    }

    public void initCurrency() {

        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");
        Current.add("");

    }


}



