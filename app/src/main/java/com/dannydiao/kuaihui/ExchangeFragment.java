package com.dannydiao.kuaihui;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
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
        exchangeListAdapter.notifyDataSetChanged();


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
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        //绑定按钮
        exchangeButton = v.findViewById(R.id.exchange_button);
        //监听点击事件
        exchangeButton.setOnClickListener(v1 -> {
            //获取兑换货币数量
            String huobi_count = editText.getText().toString();

            if (huobi_count.equals("")) {
                Toast.makeText(getActivity(), "请输入有效数字", Toast.LENGTH_SHORT).show();
            } else {
                float huobi_count_1 = Float.valueOf(huobi_count);

                String url = "https://sapi.k780.com/?app=finance.rate&scur=" + CurrencySelected + "&tcur=CNY,USD,HKD,EUR,JPY,GBP,KRW,CAD,AUD,TWD,VND,NZD,CHF,PHP" +
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
                        long StartTime = System.currentTimeMillis();

                        String temp;
                        Float temp_1;
                        String JsonRefreshTime_2="";
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONObject JsonResult = jsonObject.getJSONObject("result");
                            JSONArray JsonList = JsonResult.getJSONArray("lists");
                            for (int i = 0; i < JsonList.length(); i++) {
                                Current.remove(i);
                                temp = JsonList.getJSONObject(i).getString("rate");
                                temp_1 = Float.valueOf(temp);
                                temp_1 = huobi_count_1 * temp_1;
                                temp = String.valueOf(temp_1);
                                Current.add(i, temp);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        //解析出刷新时间
                        try {
                            JSONObject JsonRefreshTime = new JSONObject(result);
                            JsonRefreshTime = JsonRefreshTime.getJSONObject("result");
                            JSONArray JSONRefreshTime_1 = JsonRefreshTime.getJSONArray("lists");
                            JsonRefreshTime_2 = JSONRefreshTime_1.getJSONObject(0).getString("update");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //发送消息到主线程，通知刷新UI
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.obj = JsonRefreshTime_2;
                        handler.sendMessage(message);


                        long EndTime = System.currentTimeMillis();
                        long RunningTime = EndTime - StartTime;

                        Log.d("RunningTime_0", String.valueOf(StartTime));
                        Log.d("RunningTime_1", String.valueOf(EndTime));
                        Log.d("RunningTime_2", String.valueOf(RunningTime));
                    }
                });
            }
        });


        //适配Spinner
        spinner = v.findViewById(R.id.Currency_Selector);
        String[] CurrencyItems = getResources().getStringArray(R.array.Currency_Selector);
        if (getActivity() != null) {
            ArrayAdapter<String> Adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, CurrencyItems);
            spinner.setAdapter(Adapter);
        }
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
                    case 10:
                        CurrencySelected = "VND";
                        break;
                    case 11:
                        CurrencySelected = "NZD";
                        break;
                    case 12:
                        CurrencySelected = "CHF";
                        break;

                }

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

    }


}
