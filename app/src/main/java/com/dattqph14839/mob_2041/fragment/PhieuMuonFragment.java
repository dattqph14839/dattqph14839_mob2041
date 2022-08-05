package com.dattqph14839.mob_2041.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dattqph14839.mob_2041.R;
import com.dattqph14839.mob_2041.adapter.PhieuMuonAdapter;
import com.dattqph14839.mob_2041.click.PhieuMuonClick;
import com.dattqph14839.mob_2041.dao.PhieuMuonDao;
import com.dattqph14839.mob_2041.dao.SachDao;
import com.dattqph14839.mob_2041.dao.ThanhVienDao;
import com.dattqph14839.mob_2041.model.PhieuMuon;
import com.dattqph14839.mob_2041.model.Sach;
import com.dattqph14839.mob_2041.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton button;
    private int matv,mas,tien;
    private PhieuMuonDao phieuMuonDao;
    private List<PhieuMuon> list = new ArrayList<>();
    private PhieuMuonAdapter adapter;
    private TextView ngay_sua, tien_sua;
    private Button them, huy_them, sua, huy_sua;
    private Spinner spn_ThanhVien_them, spn_Sach_them, spn_ThanhVien_sua, spn_Sach_sua;
    private CheckBox ckh_them, ckh_sua;
    private List<Sach> sachList = new ArrayList<>();
    private List<ThanhVien> vienList = new ArrayList<>();
    private SachDao sachDao;
    private ThanhVienDao thanhVienDao;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        phieuMuonDao = new PhieuMuonDao(getContext());
        sachDao = new SachDao(getContext());
        thanhVienDao = new ThanhVienDao(getContext());
        recyclerView = view.findViewById(R.id.rv_data_phieumuon);
        list = phieuMuonDao.getAll();
        adapter = new PhieuMuonAdapter(getContext(), list);
        button = view.findViewById(R.id.btn_fl_them_phieumuon);
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dia_log_phieumuon, null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();
                spn_ThanhVien_them = view1.findViewById(R.id.spn_thanhvien);
                spn_Sach_them = view1.findViewById(R.id.spn_sach);
                them = view1.findViewById(R.id.btn_save_phieumuon);
                huy_them = view1.findViewById(R.id.btn_huy_phieumuon);
                ckh_them = view1.findViewById(R.id.chk_status_sach);
                vienList = thanhVienDao.getAll();
                sachList = sachDao.getAll();
                ArrayAdapter adapter_thanhvien = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, vienList);
                ArrayAdapter adapter_sach = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, sachList);
                spn_ThanhVien_them.setAdapter(adapter_thanhvien);
                spn_Sach_them.setAdapter(adapter_sach);
                spn_ThanhVien_them.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           matv=vienList.get(position).getMaTV();
                        Toast.makeText(getContext(), "Chọn: "+vienList.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spn_Sach_them.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mas=sachList.get(position).getMaSach();
                        tien=sachList.get(position).getGiaThue();
                        Toast.makeText(getContext(), "Chọn: "+sachList.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (vienList.size()>0&&sachList.size()>0){
                            PhieuMuon phieuMuon = new PhieuMuon();
                            ThanhVien vien = (ThanhVien) spn_ThanhVien_them.getSelectedItem();
                            Sach sach = (Sach) spn_Sach_them.getSelectedItem();
                            phieuMuon.setMaSach(sach.getMaSach());
                            phieuMuon.setMaTV(vien.getMaTV());
                            if (ckh_them.isChecked()) {
                                phieuMuon.setTraSach(1);
                            } else {
                                phieuMuon.setTraSach(0);
                            }
                            Sach sach1 = sachDao.getID(phieuMuon.getMaSach() + "");
                            phieuMuon.setTienThue(sach1.getGiaThue());
                            phieuMuon.setNgay(java.sql.Date.valueOf(String.valueOf(date)));
                            if (phieuMuonDao.insert(phieuMuon) > 0) {
                                Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(phieuMuonDao.getAll());
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getContext(), "Bạn phải có thành viên và sách", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                huy_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        adapter.setPhieuMuonClick(new PhieuMuonClick() {
            @Override
            public void onClick(PhieuMuon phieuMuon) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view2 = LayoutInflater.from(getContext()).inflate(R.layout.dia_log_phieumuon_edit, null);
                builder.setView(view2);
                Dialog dialog = builder.create();
                dialog.show();
                ngay_sua = view2.findViewById(R.id.tv_ngay_phieumuon_edit);
                tien_sua = view2.findViewById(R.id.tv_tienthue_edit);
                spn_ThanhVien_sua = view2.findViewById(R.id.spn_thanhvien_edit);
                spn_Sach_sua = view2.findViewById(R.id.spn_sach_edit);
                sua = view2.findViewById(R.id.btn_save_phieumuon_edit);
                huy_sua = view2.findViewById(R.id.btn_huy_phieumuon_edit);
                ckh_sua = view2.findViewById(R.id.chk_status_sach_edit);
                ngay_sua.setText("Ngày thuê: " + sdf.format(phieuMuon.getNgay()));
                tien_sua.setText("Tiền thuê: " + phieuMuon.getTienThue());
                if (phieuMuon.getTraSach()==1) {
                    ckh_sua.setChecked(true);
                } else {
                    ckh_sua.setChecked(false);
                }
                vienList = thanhVienDao.getAll();
                sachList = sachDao.getAll();
                ArrayAdapter adapter_thanhvien = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, vienList);
                ArrayAdapter adapter_sach = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, sachList);
                spn_ThanhVien_sua.setAdapter(adapter_thanhvien);
                spn_Sach_sua.setAdapter(adapter_sach);
                spn_ThanhVien_sua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        matv=vienList.get(position).getMaTV();
                        Toast.makeText(getContext(), "Chọn: "+vienList.get(position).getHoTen(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spn_Sach_sua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mas=sachList.get(position).getMaSach();
                        tien=sachList.get(position).getGiaThue();
                        Toast.makeText(getContext(), "Chọn: "+sachList.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                int vt_sach = -1;
                int vt_thanhvien = -1;
                for (int i = 0; i < vienList.size(); i++) {
                    if (phieuMuon.getMaTV() == (vienList.get(i).getMaTV())) {
                        vt_thanhvien = i;
                        break;
                    }

                }
                spn_ThanhVien_sua.setSelection(vt_thanhvien);

                for (int i = 0; i < sachList.size(); i++) {
                    if (phieuMuon.getMaSach() == (sachList.get(i).getMaSach())) {
                        vt_sach = i;
                        break;
                    }

                }
                spn_Sach_sua.setSelection(vt_sach);
                sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Sua","Sua");
                        ThanhVien vien = (ThanhVien) spn_ThanhVien_sua.getSelectedItem();
                        Sach sach = (Sach) spn_Sach_sua.getSelectedItem();
                        phieuMuon.setMaSach(sach.getMaSach());
                        phieuMuon.setMaTV(vien.getMaTV());
                        phieuMuon.setNgay(java.sql.Date.valueOf(String.valueOf(date)));
                        Sach sach2 = sachDao.getID(phieuMuon.getMaSach() + "");
                        phieuMuon.setTienThue(sach2.getGiaThue());
                        if (ckh_sua.isChecked()) {
                            phieuMuon.setTraSach(1);
                        } else {
                            phieuMuon.setTraSach(0);
                        }
                        if (phieuMuonDao.upate(phieuMuon) > 0) {
                            Toast.makeText(getContext(), "Sửa phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(phieuMuonDao.getAll());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Sửa phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                huy_sua.setOnClickListener(new View.OnClickListener() {
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
}