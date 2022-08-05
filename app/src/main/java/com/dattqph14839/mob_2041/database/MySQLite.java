package com.dattqph14839.mob_2041.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLite extends SQLiteOpenHelper {
    public MySQLite(Context context) {
        super(context, "SPM.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_ThuThu = "CREATE TABLE ThuThu (maTT TEXT PRIMARY KEY, hoTen TEXT NOT NULL,matKhau TEXT NOT NULL)";
        db.execSQL(create_ThuThu);
        String create_LoaiSach = "CREATE TABLE LoaiSach (maLoai INTEGER PRIMARY KEY AUTOINCREMENT, tenLoai TEXT NOT NULL)";
        db.execSQL(create_LoaiSach);
        String create_ThanhVien = "CREATE TABLE ThanhVien (maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT NOT NULL, namSinh TEXT NOT NULL)";
        db.execSQL(create_ThanhVien);
        String create_Sach = "CREATE TABLE Sach (maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                " tenSach TEXT NOT NULL, giaThue INTEGER NOT NULL, maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(create_Sach);
        String create_PhieuMuon = "CREATE TABLE PhieuMuon (maPM INTEGER PRIMARY KEY AUTOINCREMENT, maTT TEXT REFERENCES ThuThu(maTT)" +
                ",maTV INTEGER REFERENCES ThanhVien(maTV),maSach INTEGER REFERENCES Sach(maSach)," +
                "tienThue INTEGER NOT NULL,ngay DATE NOT NULL, traSach INTEGER NOT NULL)";
        db.execSQL(create_PhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ThuThu");
        db.execSQL("DROP TABLE IF EXISTS LoaiSach");
        db.execSQL("DROP TABLE IF EXISTS ThanhVien");
        db.execSQL("DROP TABLE IF EXISTS Sach");
        db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
        onCreate(db);
    }
}
