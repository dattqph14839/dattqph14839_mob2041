package com.dattqph14839.mob_2041;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dattqph14839.mob_2041.fragment.DoanhThuFragment;
import com.dattqph14839.mob_2041.fragment.DoiMatKhauFragment;
import com.dattqph14839.mob_2041.fragment.LoaiSachFragment;
import com.dattqph14839.mob_2041.fragment.PhieuMuonFragment;
import com.dattqph14839.mob_2041.fragment.SachFragment;
import com.dattqph14839.mob_2041.fragment.ThanhVienFragment;
import com.dattqph14839.mob_2041.fragment.ThemNguoiDungFragment;
import com.dattqph14839.mob_2041.fragment.Top10SachFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View headerLayout;
    private TextView tvUser;
    private static final int phieuMuon = 0;
    private static final int loaiSach = 1;
    private static final int sach = 2;
    private static final int thanhVien = 3;
    private static final int top10Sach=4;
    private static final int doanhThu=5;
    private static final int themNguoiDung=6;
    private static final int doiMatKhau=7;
    private int currentFragment = phieuMuon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        headerLayout=navigationView.getHeaderView(0);
        tvUser=headerLayout.findViewById(R.id.tv_tenUser);
        Intent intent=getIntent();
        String user=intent.getStringExtra("user");
        tvUser.setText("Welcome "+user+"!");
        if (user.equalsIgnoreCase("admin")){
            navigationView.getMenu().findItem(R.id.sub_AddUser).setVisible(true);
        }
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new PhieuMuonFragment());
        setTitle("Quản lý phiếu mượn");
        navigationView.getMenu().findItem(R.id.nav_PhieuMuon).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_PhieuMuon) {
            if (currentFragment != phieuMuon) {
                replaceFragment(new PhieuMuonFragment());
                setTitle("Quản lý phiếu mượn");
                currentFragment = phieuMuon;
            }
        }else if (id==R.id.nav_LoaiSach){
            if (currentFragment!=loaiSach){
                replaceFragment(new LoaiSachFragment());
                setTitle("Quản lý loại sách");
                currentFragment=loaiSach;
            }
        }else if (id==R.id.nav_Sach){
            if (currentFragment!=sach){
                replaceFragment(new SachFragment());
                setTitle("Quản lý sách");
                currentFragment=sach;
            }
        }else if (id==R.id.nav_ThanhVien){
            if (currentFragment!=thanhVien){
                replaceFragment(new ThanhVienFragment());
                setTitle("Quản lý thành viên");
                currentFragment=thanhVien;
            }
        }else if (id==R.id.sub_Top){
            if (currentFragment!=top10Sach){
                replaceFragment(new Top10SachFragment());
                setTitle("Top 10 sách bán chạy nhất");
                currentFragment=top10Sach;
            }
        }else if (id==R.id.sub_DoanhThu){
            if (currentFragment!=doanhThu){
                replaceFragment(new DoanhThuFragment());
                setTitle("Doanh thu");
                currentFragment=doanhThu;
            }
        }else if (id==R.id.sub_AddUser){
            if (currentFragment!=themNguoiDung){
                replaceFragment(new ThemNguoiDungFragment());
                setTitle("Thêm người dùng");
                currentFragment=themNguoiDung;
            }
        }else if (id==R.id.sub_ChangePass){
            if (currentFragment!=doiMatKhau){
                replaceFragment(new DoiMatKhauFragment());
                setTitle("Đổi mật khẩu");
                currentFragment=doiMatKhau;
            }
        }else if (id==R.id.sub_Logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you want to exit ?")
                    .setTitle("Message")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            Dialog dialog = builder.create();
            dialog.show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment).commit();
    }
}