package com.dannydiao.kuaihui.Rate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dannydiao.kuaihui.R;

import java.util.List;

public class RateListAdapter extends RecyclerView.Adapter<RateListAdapter.VH> {

    public static class VH extends RecyclerView.ViewHolder{
        public final TextView Title;
        public final TextView Current;
        public final ImageView Pic;
        public final TextView Hint;
        public VH(View v){
            super(v);
            Title = v.findViewById(R.id.List_Title_2);
            Current = v.findViewById(R.id.List_Current_2);
            Pic = v.findViewById(R.id.List_Pic_2);
            Hint = v.findViewById(R.id.hint);

        }
    }

    private List<String> TitleArray;
    private List<String> CurrentArray;
    private List<String> Hint;


    public RateListAdapter(List<String> data1,List<String> data2,List data3){
        this.TitleArray = data1;
        this.CurrentArray = data2;
        this.Hint = data3;
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.Title.setText(TitleArray.get(i));
        vh.Current.setText(CurrentArray.get(i));
        vh.Hint.setText("1" + TitleArray.get(i) + "/人民币");
        switch (i){
            case 0:vh.Pic.setImageResource(R.drawable.usd);
                break;
            case 1:vh.Pic.setImageResource(R.drawable.hkd);
                break;
            case 2:vh.Pic.setImageResource(R.drawable.eur);
                break;
            case 3:vh.Pic.setImageResource(R.drawable.jpy);
                break;
            case 4:vh.Pic.setImageResource(R.drawable.gbp);
                break;
            case 5:vh.Pic.setImageResource(R.drawable.krw);
                break;
            case 6:vh.Pic.setImageResource(R.drawable.cad);
                break;
            case 7:vh.Pic.setImageResource(R.drawable.aud);
                break;
            case 8:vh.Pic.setImageResource(R.drawable.twd);
                break;
            case 9:vh.Pic.setImageResource(R.drawable.sgd);
                break;
            case 10:vh.Pic.setImageResource(R.drawable.thb);
                break;
            case 11:vh.Pic.setImageResource(R.drawable.mop);
                break;
            case 12:vh.Pic.setImageResource(R.drawable.vnd);
                break;
            case 13:vh.Pic.setImageResource(R.drawable.nzd);
                break;
            case 14:vh.Pic.setImageResource(R.drawable.chf);
                break;
            case 15:vh.Pic.setImageResource(R.drawable.php);
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rate_list,viewGroup,false);
        return new VH(v);
    }
}

