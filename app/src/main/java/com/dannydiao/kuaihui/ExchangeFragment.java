package com.dannydiao.kuaihui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ExchangeFragment extends Fragment {
    List<String> Title = new ArrayList<>();
    List<String> Current = new ArrayList<>();
    Spinner spinner;
    RecyclerView recyclerView;
    String CurrencySelected = "CNY";
    Button exchangeButton;
    EditText editText;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public ExchangeFragment() {
        // Required empty public constructor
    }


    public static ExchangeFragment newInstance(String param1, String param2) {
        ExchangeFragment fragment = new ExchangeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exchange, container, false);

        //初始化数据
        initTitle();
        initCurrency();
        TextView refresh_time = v.findViewById(R.id.refresh_time);
        //初始化RecyclerView
        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ExchangeListAdapter exchangeListAdapter = new ExchangeListAdapter(Title, Current);
        recyclerView.setAdapter(exchangeListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));


        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    exchangeListAdapter.notifyDataSetChanged();
                    refresh_time.setText(msg.obj.toString());
                }
            }
        };
        //绑定edittext
        editText = v.findViewById(R.id.huobi_count);
        editText.clearFocus();
        //绑定按钮
        exchangeButton = v.findViewById(R.id.exchange_button);
        //监听点击事件
        exchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取兑换货币数量
                editText.clearFocus();
                String huobi_count = editText.getText().toString();
                float huobi_count_1 = Float.valueOf(huobi_count);

                String url = "http://api.k780.com/?app=finance.rate&scur=" + CurrencySelected + "&tcur=CNY,USD,HKD,EUR,JPY,GBP,KRW,CAD,AUD,TWD" +
                        "&appkey=42125&sign=bcb58eb83ab21f84f80881c1f36be84e";
                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();

                        //解析出汇率结果
                        List<String> rate_final = new ArrayList<>();
                        String[] rate_split = result.split("\"rate\"");
                        int size = rate_split.length;

                        for (int i = 1; i < 11; i++) {
                            rate_final.add(rate_split[i].substring(2, 8));
                        }

                        for (int i = 0; i < 10; i++) {
                            Current.remove(i);
                            if (rate_final.get(i).equals("1\",\"up")) {
                                Current.add(i, "1");
                            } else {
                                Current.add(i, rate_final.get(i));
                            }
                        }
                        float temp;

                        for (int i = 0; i < 10; i++) {
                            temp = (float)Float.valueOf(Current.get(i)) * huobi_count_1;
                            Current.remove(i);
                            //保留两位小数
                            temp = (float)(Math.round(temp*100))/100;
                            Current.add(i,String.valueOf(temp));

                        }

                        //解析出刷新时间
                        String[] refresh_time_split = result.split("\"update\":\"");
                        String refresh_time = refresh_time_split[1].substring(0,19);
                        //发送消息到主线程，通知刷新UI
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.obj = refresh_time;
                        handler.sendMessage(message);

                    }
                });
            }
        });


        //适配Spinner
        spinner = v.findViewById(R.id.Currency_Selector);
        String CurrencyItems[] = getResources().getStringArray(R.array.Currency_Selector);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, CurrencyItems);
        spinner.setAdapter(Adapter);

        //监听Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        CurrencySelected = "CNY";
                        break;
                    case 1:
                        CurrencySelected = "USD";
                        break;
                    case 2:
                        CurrencySelected = "HKD";
                        break;
                    case 3:
                        CurrencySelected = "EUR";
                        break;
                    case 4:
                        CurrencySelected = "JPY";
                        break;
                    case 5:
                        CurrencySelected = "GBP";
                        break;
                    case 6:
                        CurrencySelected = "KRW";
                        break;
                    case 7:
                        CurrencySelected = "CAD";
                        break;
                    case 8:
                        CurrencySelected = "AUD";
                        break;
                    case 9:
                        CurrencySelected = "TWD";
                        break;
                }
                Log.d("a", CurrencySelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }

    public void initTitle() {
        Title.add("人民币 CNY");
        Title.add("美元 USD");
        Title.add("港币 HKD");
        Title.add("欧元 EUR");
        Title.add("日元 JPY");
        Title.add("英镑 GBP");
        Title.add("韩元 KRW");
        Title.add("加拿大元 CAD");
        Title.add("澳大利亚元 AUD");
        Title.add("新台币 TWD");

    }

    public void initCurrency() {
        Current.add("0");
        Current.add("0");
        Current.add("0");
        Current.add("0");
        Current.add("0");
        Current.add("0");
        Current.add("0");
        Current.add("0");
        Current.add("0");
        Current.add("0");

    }


}
