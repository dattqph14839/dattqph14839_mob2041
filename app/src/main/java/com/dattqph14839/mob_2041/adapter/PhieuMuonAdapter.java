package com.dattqph14839.mob_2041.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dattqph14839.mob_2041.R;
import com.dattqph14839.mob_2041.click.PhieuMuonClick;
import com.dattqph14839.mob_2041.dao.PhieuMuonDao;
import com.dattqph14839.mob_2041.dao.SachDao;
import com.dattqph14839.mob_2041.dao.ThanhVienDao;
import com.dattqph14839.mob_2041.model.PhieuMuon;
import com.dattqph14839.mob_2041.model.Sach;
import com.dattqph14839.mob_2041.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    private Context context;
    private SachDao sachDao;
    private ThanhVienDao thanhVienDao;
    private List<PhieuMuon> list;
    private PhieuMuonDao phieuMuonDao;
    private PhieuMuonClick phieuMuonClick;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonAdapter(Context context, List<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        phieuMuonDao = new PhieuMuonDao(context);
        sachDao = new SachDao(context);
        thanhVienDao = new ThanhVienDao(context);
    }

    public void setPhieuMuonClick(PhieuMuonClick phieuMuonClick) {
        this.phieuMuonClick = phieuMuonClick;
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phieumuon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        Sach sach = sachDao.getID(String.valueOf(phieuMuon.getMaSach()));
        ThanhVien thanhVien=thanhVienDao.getID(String.valueOf(phieuMuon.getMaTV()));
        holder.ma.setText("Mã phiếu: " + phieuMuon.getMaPM());
        holder.tv.setText("Thành viên: "+thanhVien.getHoTen());
        holder.ten.setText("Tên sách: "+sach.getTenSach());
        holder.tien.setText("Tiền thuê: "+phieuMuon.getTienThue());
        holder.ngay.setText("Ngày thuê: "+sdf.format(phieuMuon.getNgay()));
        if (phieuMuon.getTraSach()==1){
            holder.trasach.setTextColor(Color.BLUE);
            holder.trasach.setText("Đã trả sách");
        }else {
            holder.trasach.setTextColor(Color.RED);
            holder.trasach.setText("Chưa trả sách");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phieuMuonClick.onClick(list.get(position));
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setTitle("Bạn có muốn xóa không")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (phieuMuonDao.delete(phieuMuon.getMaPM()) > 0) {
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    list.clear();
                                    list.addAll(phieuMuonDao.getAll());
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("CANNEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        public TextView ma, tv, ten, tien, trasach, ngay;
        public ImageView delete;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.tv_maphieu);
            tv = itemView.findViewById(R.id.tv_tenthanhvien);
            ten = itemView.findViewById(R.id.tv_tensach);
            tien = itemView.findViewById(R.id.tv_tenthue);
            trasach = itemView.findViewById(R.id.tv_trasach);
            ngay = itemView.findViewById(R.id.tv_ngaythue);
            delete = itemView.findViewById(R.id.img_delete_phieumuon);
        }
    }
}
