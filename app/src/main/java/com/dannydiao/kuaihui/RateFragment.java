package com.dannydiao.kuaihui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<String> Title = new ArrayList<>();
    List<String> Current = new ArrayList<>();
    List<String> Hint = new ArrayList<>();
    RecyclerView recyclerView;


    private String mParam1;
    private String mParam2;



    public RateFragment() {
        // Required empty public constructor
    }

    public static RateFragment newInstance(String param1, String param2) {
        RateFragment fragment = new RateFragment();
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
        View v = inflater.inflate(R.layout.fragment_rate, container, false);
        initTitle();
        initCurrency();
        TextView refresh_time = v.findViewById(R.id.refresh_time_2);
        recyclerView = v.findViewById(R.id.recycler_view_2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RateListAdapter rateListAdapter = new RateListAdapter(Title,Current,Hint);
        recyclerView.setAdapter(rateListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));




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
        Current.add("0");
        Current.add("0");

    }


    }



