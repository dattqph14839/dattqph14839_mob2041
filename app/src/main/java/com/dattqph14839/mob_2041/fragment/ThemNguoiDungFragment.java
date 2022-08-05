package com.dattqph14839.mob_2041.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.dattqph14839.mob_2041.R;
import com.dattqph14839.mob_2041.dao.ThuThuDao;
import com.dattqph14839.mob_2041.model.ThuThu;

public class ThemNguoiDungFragment extends Fragment {
    private ThuThuDao thuThuDao;
    private EditText edDn, edHT, edPass, edPassRepeat;
    private Button btnSave, btnHuy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);
       thuThuDao=new ThuThuDao(getContext());
        edDn = view.findViewById(R.id.ed_tendangnhap_themtt);
        edHT = view.findViewById(R.id.ed_hoten_themtt);
        edPass = view.findViewById(R.id.ed_matkhau_themtt);
        edPassRepeat = view.findViewById(R.id.ed_matkhauRepeat_themtt);
        btnSave = view.findViewById(R.id.btn_save_themtt);
        btnHuy = view.findViewById(R.id.btn_huy_themtt);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ThuThu thu=new ThuThu();
                 thu.setMaTT(edDn.getText().toString());
                 thu.setHoTen(edHT.getText().toString());
                 thu.setMatKhau(edPass.getText().toString());
                 if (valiDate()>0){
                     if (thuThuDao.insert(thu)>0){
                         Toast.makeText(getContext(),"Thêm tài khoản thành công",Toast.LENGTH_SHORT).show();
                         edDn.setText("");
                         edHT.setText("");
                         edPass.setText("");
                         edPassRepeat.setText("");
                     }else {
                         Toast.makeText(getContext(),"Thêm tài khoản thất bại",Toast.LENGTH_SHORT).show();
                     }
                 }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edDn.setText("");
                edHT.setText("");
                edPass.setText("");
                edPassRepeat.setText("");
            }
        });

        return view;
    }

    private int valiDate() {
        int check = 1;
        if (edDn.getText().length() == 0 || edHT.getText().length() == 0 || edPass.getText().length() == 0 || edPassRepeat.getText().length() == 0) {
            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edPass.getText().toString();
            String passRepeat = edPassRepeat.getText().toString();
            if (!pass.equals(passRepeat)) {
                Toast.makeText(getContext(), "Mật khẩu lặp lại không đúng", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}