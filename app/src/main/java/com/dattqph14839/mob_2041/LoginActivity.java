package com.dattqph14839.mob_2041;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dattqph14839.mob_2041.dao.ThuThuDao;

public class LoginActivity extends AppCompatActivity {
    private long press;
    private String strUser, strPass;
    private EditText edUsername, edPass;
    private CheckBox remember;
    private Button btnDangNhap, btnHuy;
    private ThuThuDao thuThuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        thuThuDao = new ThuThuDao(this);
        anhXaView();
        loadData();
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkLogin();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUsername.setText("");
                edPass.setText("");
            }
        });
    }

    private void loadData() {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        Boolean kiemTra = pref.getBoolean("kt", false);
        if (kiemTra) {
            edUsername.setText(pref.getString("username", ""));
            edPass.setText(pref.getString("password", ""));
        }
        remember.setChecked(kiemTra);
    }

    private void checkLogin() {
        strUser = edUsername.getText().toString();
        strPass = edPass.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Tên đăng nhập và pasword không để trống", Toast.LENGTH_SHORT).show();
        } else {
            if (thuThuDao.checkLogin(strUser, strPass) > 0 || strUser.equals("admin") && strPass.equals("admin")) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                checkRememberUser(strUser,strPass,remember.isChecked());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user", strUser);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại username hoặc password sai", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void checkRememberUser(String a,String b,boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        if (status==true){
             editor.putString("username",a);
            editor.putString("password",b);
            editor.putBoolean("kt",status);
        }else {
            editor.clear();
        }
        editor.commit();
    }
    private void anhXaView() {
        edUsername = findViewById(R.id.ed_username_login);
        edPass = findViewById(R.id.ed_password_login);
        btnDangNhap = findViewById(R.id.btn_dangnhap_login);
        btnHuy = findViewById(R.id.btn_huy_login);
        remember = findViewById(R.id.chk_luu_account);
    }

    @Override
    public void onBackPressed() {
        if (press + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(LoginActivity.this, "nhấn lại để thoát", Toast.LENGTH_SHORT).show();
        }
        press = System.currentTimeMillis();
    }
}