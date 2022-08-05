package com.dattqph14839.mob_2041.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dattqph14839.mob_2041.R;
import com.dattqph14839.mob_2041.adapter.LoaiSachAdapter;
import com.dattqph14839.mob_2041.click.LoaiSachClick;
import com.dattqph14839.mob_2041.dao.LoaiSachDao;
import com.dattqph14839.mob_2041.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachFragment extends Fragment {
    private RecyclerView recyclerView;
    private LoaiSachDao loaiSachDao;
    private List<LoaiSach> list = new ArrayList<>();
    private LoaiSachAdapter adapter;
    private FloatingActionButton button;
    private EditText  edTenLoai,  ed_editTenLoai;
    private Button btnThem, btnHuy, btn_edit, btnHuyEdit;
    private LoaiSach sach;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);

        loaiSachDao = new LoaiSachDao(getContext());
        recyclerView = view.findViewById(R.id.rv_data_loaisach);
        list = loaiSachDao.getAll();
        adapter = new LoaiSachAdapter(getContext(), list);
        button = view.findViewById(R.id.btn_fl_them_loaisach);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLog();
            }
        });
        adapter.setLoaiSachClick(new LoaiSachClick() {
            @Override
            public void onClick(LoaiSach loaiSach) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dia_log_loaisach_edit, null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();
                ed_editTenLoai = view1.findViewById(R.id.ed_tenloaisach_edit);
                btn_edit=view1.findViewById(R.id.btn_save_loaisach_edit);
                btnHuyEdit=view1.findViewById(R.id.btn_huy_loaisach_edi);
                ed_editTenLoai.setText(loaiSach.getTenLoai());
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ed_editTenLoai.getText().length() == 0) {
                            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                        } else {
                            loaiSach.setTenLoai(ed_editTenLoai.getText().toString());
                            if (loaiSachDao.upate(loaiSach) > 0) {
                                Toast.makeText(getContext(), "Sửa loại sách thành công", Toast.LENGTH_SHORT).show();
                                ed_editTenLoai.setText("");
                                list.clear();
                                list.addAll(loaiSachDao.getAll());
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "Sửa loại sách thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                btnHuyEdit.setOnClickListener(new View.OnClickListener() {
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

    private void openDiaLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dia_log_loaisach, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        edTenLoai = view.findViewById(R.id.ed_tenloaisach);
        btnThem=view.findViewById(R.id.btn_save_loaisach);
        btnHuy=view.findViewById(R.id.btn_huy_loaisach);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTenLoai.getText().length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    sach = new LoaiSach();
                    sach.setTenLoai(edTenLoai.getText().toString());
                    if (loaiSachDao.insert(sach) > 0) {
                        Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                        edTenLoai.setText("");
                        list.clear();
                        list.addAll(loaiSachDao.getAll());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Thêm loại sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}