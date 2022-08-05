package com.dattqph14839.mob_2041.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

public class DoiMatKhauFragment extends Fragment {
    private ThuThuDao thuThuDao;
    private EditText edPassOld, edpassNew, edPassRepeat;
    private Button btnLua, btnHuy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        thuThuDao = new ThuThuDao(getContext());
        edPassOld = view.findViewById(R.id.ed_pass_old);
        edpassNew = view.findViewById(R.id.ed_pass_new);
        edPassRepeat = view.findViewById(R.id.ed_pass_repeat);
        btnLua = view.findViewById(R.id.btn_save_changepass);
        btnHuy = view.findViewById(R.id.btn_huy_changepass);
        btnLua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("username", "");
                if (valiDate() > 0) {
                    ThuThu thuThu = thuThuDao.getID(user);
                    thuThu.setMatKhau(edpassNew.getText().toString());
                    thuThuDao.updatePass(thuThu);
                    if (thuThuDao.updatePass(thuThu) > 0) {
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edpassNew.setText("");
                        edPassRepeat.setText("");
                    } else {
                        Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassOld.setText("");
                edpassNew.setText("");
                edPassRepeat.setText("");
            }
        });
        return view;
    }

    private int valiDate() {
        int check = 1;
        if (edPassOld.getText().toString().isEmpty() || edpassNew.getText().toString().isEmpty() || edPassRepeat.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = pref.getString("password", "");
            String passNew = edpassNew.getText().toString();
            String passRepeat = edPassRepeat.getText().toString();
            if (!passOld.equals(edPassOld.getText().toString())) {
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!passNew.equals(passRepeat)) {
                Toast.makeText(getContext(), "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}