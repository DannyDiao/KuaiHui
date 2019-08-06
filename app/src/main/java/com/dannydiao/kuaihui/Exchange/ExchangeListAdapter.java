package com.dannydiao.kuaihui.Exchange;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dannydiao.kuaihui.R;

import java.util.List;

public class ExchangeListAdapter extends RecyclerView.Adapter<ExchangeListAdapter.VH> {

    public static class VH extends RecyclerView.ViewHolder{
        public final TextView Title;
        public final TextView Current;
        public final ImageView Pic;
        public VH(View v){
            super(v);
            Title = v.findViewById(R.id.List_Title);
            Current = v.findViewById(R.id.List_Current);
            Pic = v.findViewById(R.id.List_Pic);

        }
    }

    private List<String> TitleArray;
    private List<String> CurrentArray;

    ExchangeListAdapter(List<String> data1, List<String> data2){
        this.TitleArray = data1;
        this.CurrentArray = data2;
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.Title.setText(TitleArray.get(i));
        vh.Current.setText(CurrentArray.get(i));
        switch (i){
            case 0:vh.Pic.setImageResource(R.drawable.cny);
            break;
            case 1:vh.Pic.setImageResource(R.drawable.usd);
                break;
            case 2:vh.Pic.setImageResource(R.drawable.hkd);
                break;
            case 3:vh.Pic.setImageResource(R.drawable.eur);
                break;
            case 4:vh.Pic.setImageResource(R.drawable.jpy);
                break;
            case 5:vh.Pic.setImageResource(R.drawable.gbp);
                break;
            case 6:vh.Pic.setImageResource(R.drawable.krw);
                break;
            case 7:vh.Pic.setImageResource(R.drawable.cad);
                break;
            case 8:vh.Pic.setImageResource(R.drawable.aud);
                break;
            case 9:vh.Pic.setImageResource(R.drawable.twd);
                break;
            case 10:vh.Pic.setImageResource(R.drawable.vnd);
                break;
            case 11:vh.Pic.setImageResource(R.drawable.nzd);
                break;
            case 12:vh.Pic.setImageResource(R.drawable.chf);
                break;
            case 13:vh.Pic.setImageResource(R.drawable.php);
                break;
        }


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
