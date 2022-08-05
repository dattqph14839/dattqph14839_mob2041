package com.dattqph14839.mob_2041.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dattqph14839.mob_2041.R;
import com.dattqph14839.mob_2041.adapter.ThanhVienAdapter;
import com.dattqph14839.mob_2041.click.ThanhVienClick;
import com.dattqph14839.mob_2041.dao.ThanhVienDao;
import com.dattqph14839.mob_2041.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienFragment extends Fragment {
    private ThanhVienDao thanhVienDao;
    private List<ThanhVien> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    private ThanhVienAdapter adapter;
    private ThanhVien vien;
    private EditText  ht, ns, ht_ed, ns_ed;
    private Button them, huy, sua, huysua;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        thanhVienDao = new ThanhVienDao(getContext());
        list = thanhVienDao.getAll();
        adapter = new ThanhVienAdapter(getContext(), list);
        recyclerView = view.findViewById(R.id.rv_data_thanhvien);
        btnAdd = view.findViewById(R.id.btn_fl_them_thanhvien);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        adapter.setThanhVienClick(new ThanhVienClick() {
            @Override
            public void onClick(ThanhVien thanhVien) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dia_log_thanhvienedit, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                ht_ed = view.findViewById(R.id.ed_edit_hotentv);
                ns_ed = view.findViewById(R.id.ed_edit_namsinh);
                sua = view.findViewById(R.id.btn_edit_save_thanhvien);
                huysua = view.findViewById(R.id.btn_edit_huy_thanhvien);
                ht_ed.setText(thanhVien.getHoTen());
                ns_ed.setText(thanhVien.getNamSinh());
                sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thanhVien.setHoTen(ht_ed.getText().toString());
                        thanhVien.setNamSinh(ns_ed.getText().toString());
                        if (ht_ed.getText().length() == 0 || ns_ed.getText().length() == 0) {
                            Toast.makeText(getContext(), "Không để trống", Toast.LENGTH_SHORT).show();
                        } else {
                            if (thanhVienDao.update(thanhVien) > 0) {
                                Toast.makeText(getContext(), "Sửa thành viên thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(thanhVienDao.getAll());
                                adapter.notifyDataSetChanged();
                                ht_ed.setText("");
                                ns_ed.setText("");
                            } else {
                                Toast.makeText(getContext(), "Sửa thành viên thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                huysua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }


    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dia_log_thanhvien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        ht = view.findViewById(R.id.ed_hotentv);
        ns = view.findViewById(R.id.ed_namsinh);
        them = view.findViewById(R.id.btn_save_thanhvien);
        huy = view.findViewById(R.id.btn_huy_thanhvien);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vien = new ThanhVien();
                vien.setHoTen(ht.getText().toString());
                vien.setNamSinh(ns.getText().toString());
                if (ht.getText().length() == 0 || ns.getText().length() == 0) {
                    Toast.makeText(getContext(), "Không để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (thanhVienDao.insert(vien) > 0) {
                        Toast.makeText(getContext(), "Thêm thành viên thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list.addAll(thanhVienDao.getAll());
                        adapter.notifyDataSetChanged();
                        ht.setText("");
                        ns.setText("");
                    } else {
                        Toast.makeText(getContext(), "Thêm thành viên thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}