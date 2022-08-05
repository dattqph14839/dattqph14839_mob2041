package com.dattqph14839.mob_2041.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dattqph14839.mob_2041.R;
import com.dattqph14839.mob_2041.dao.ThongKeDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DoanhThuFragment extends Fragment {
    private Button tuNgay, denNgay, btnDoanhThu;
    private EditText ed_tuNGay, ed_denNGay;
    private TextView tv_soTien;
    private ThongKeDao thongKeDao;
    private int mYear, mMonth, mDay;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        tuNgay = view.findViewById(R.id.btn_tu_ngay);
        denNgay = view.findViewById(R.id.btn_den_ngay);
        btnDoanhThu = view.findViewById(R.id.btn_doanh_thu);
        ed_tuNGay = view.findViewById(R.id.ed_tu_ngay);
        ed_denNGay = view.findViewById(R.id.ed_den_ngay);
        tv_soTien = view.findViewById(R.id.tv_doanhthu);
        DatePickerDialog.OnDateSetListener mDataTuNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
                ed_tuNGay.setText(sdf.format(c.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener mDataDenNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
                ed_denNGay.setText(sdf.format(c.getTime()));
            }
        };
        tuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getContext(), 0, mDataTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        denNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getContext(), 0, mDataDenNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongKeDao = new ThongKeDao(getContext());
                Log.e("Doan thu","Doanh thu");
                String tu = ed_tuNGay.getText().toString();
                String den = ed_denNGay.getText().toString();
                tv_soTien.setText("Doanh thu: " + thongKeDao.getDoanhThu(tu, den) + "VNĐ");
            }
        });
        return view;
    }
}