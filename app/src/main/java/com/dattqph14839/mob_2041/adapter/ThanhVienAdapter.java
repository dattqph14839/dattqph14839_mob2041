package com.dattqph14839.mob_2041.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dattqph14839.mob_2041.R;
import com.dattqph14839.mob_2041.click.ThanhVienClick;
import com.dattqph14839.mob_2041.dao.ThanhVienDao;
import com.dattqph14839.mob_2041.model.ThanhVien;

import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    private Context context;
    private List<ThanhVien> list;
    private ThanhVienDao thanhVienDao;
    private ThanhVienClick thanhVienClick;
    public ThanhVienAdapter(Context context, List<ThanhVien> list) {
        this.context = context;
        this.list = list;
        thanhVienDao=new ThanhVienDao(context);
    }

    public void setThanhVienClick(ThanhVienClick thanhVienClick) {
        this.thanhVienClick = thanhVienClick;
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_thanhvien,parent,false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ThanhVienViewHolder holder, int position) {
        ThanhVien vien=list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhVienClick.onClick(list.get(position));
            }
        });
        holder.ma.setText("Mã thành viên: "+vien.getMaTV());
        holder.hoTen.setText("Họ tên: "+vien.getHoTen());
        holder.ns.setText("Năm sinh: "+vien.getNamSinh());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setTitle("Bạn có muốn xóa không")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (thanhVienDao.delete(vien.getMaTV())>0){
                                    Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show();
                                    list.clear();list.addAll(thanhVienDao.getAll());
                                    notifyDataSetChanged();
                                }else {
                                    Toast.makeText(context,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("CANNEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                Dialog dialog=builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder{
        public TextView hoTen,ns,ma;
        public ImageView delete;
        public ThanhVienViewHolder(@NonNull  View itemView) {
            super(itemView);
            ma=itemView.findViewById(R.id.tv_maTV);
            hoTen=itemView.findViewById(R.id.tv_hotenTV);
            ns=itemView.findViewById(R.id.tv_namsinh);
            delete=itemView.findViewById(R.id.img_delete_thanhvien);
        }
    }
}
