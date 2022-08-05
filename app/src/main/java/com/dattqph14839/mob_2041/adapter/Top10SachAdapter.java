package com.dattqph14839.mob_2041.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dattqph14839.mob_2041.R;
import com.dattqph14839.mob_2041.model.Top;

import java.util.List;

public class Top10SachAdapter extends RecyclerView.Adapter<Top10SachAdapter.Top10SachViewHolder> {
    private Context context;
    private List<Top> list;

    public Top10SachAdapter(Context context, List<Top> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Top10SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_top10sach,parent,false);
        return new Top10SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top10SachViewHolder holder, int position) {
        Top top = list.get(position);
        holder.ten.setText("Tên sách: "+top.tenSach);
        holder.sl.setText("Số lượng: "+top.soLuong);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Top10SachViewHolder extends RecyclerView.ViewHolder {
        public TextView ten, sl;
        public Top10SachViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.tv_top10_ten);
            sl = itemView.findViewById(R.id.tv_soluongsach);
        }
    }
}
