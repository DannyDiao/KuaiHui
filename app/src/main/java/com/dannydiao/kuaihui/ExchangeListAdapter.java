package com.dannydiao.kuaihui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

public class ExchangeListAdapter extends RecyclerView.Adapter<ExchangeListAdapter.VH> {

    public static class VH extends RecyclerView.ViewHolder{
        public final TextView Title;
        public final TextView Current;
        public VH(View v){
            super(v);
            Title = (TextView)v.findViewById(R.id.List_Title);
            Current = (TextView)v.findViewById(R.id.List_Current);

        }
    }

    private List<String> TitleArray;
    private List<String> CurrentArray;

    public ExchangeListAdapter(List<String> data1,List<String> data2){
        this.TitleArray = data1;
        this.CurrentArray = data2;
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.Title.setText(TitleArray.get(i));
        vh.Current.setText(CurrentArray.get(i));
    }

    @Override
    public int getItemCount() {
        return TitleArray.size();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exchange_list,viewGroup,false);
        return new VH(v);
    }
}