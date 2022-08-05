package com.dattqph14839.mob_2041.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dattqph14839.mob_2041.database.MySQLite;
import com.dattqph14839.mob_2041.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDao {
    private MySQLite mySQLite;
    private SQLiteDatabase db;

    public ThuThuDao(Context context) {
        mySQLite = new MySQLite(context);
        db = mySQLite.getWritableDatabase();
    }

    public long insert(ThuThu t) {
        ContentValues values = new ContentValues();
        values.put("maTT", t.getMaTT());
        values.put("hoTen", t.getHoTen());
        values.put("matKhau", t.getMatKhau());
        return db.insert("ThuThu", null, values);
    }

    public int updatePass(ThuThu t) {
        ContentValues values = new ContentValues();
        values.put("hoTen", t.getHoTen());
        values.put("matKhau", t.getMatKhau());
        return db.update("ThuThu", values, "maTT=?", new String[]{t.getMaTT()});
    }

    public int delete(String t) {
        return db.delete("ThuThu", "maTT=?", new String[]{t});
    }

    public List<ThuThu> getData(String sql, String... selectionArgs) {
        List<ThuThu> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        if (c.getCount() > 0) {
            c.moveToFirst() ;
            while (!c.isAfterLast()) {
                String a = c.getString(0);
                String b = c.getString(1);
                String cc = c.getString(2);
                list.add(new ThuThu(a,b,cc));
                c.moveToNext();
            }
            c.close();
        }
        return list;
    }
    public List<ThuThu> getAll(){
       String sql="select * from ThuThu";
       return getData(sql);
    }
    public ThuThu getID(String id){
        String sql="select * from ThuThu where maTT=?";
        List<ThuThu> list=getData(sql,id);
        return list.get(0);
    }
    public int checkLogin(String id,String pass){
        String sql="select * from ThuThu where maTT=? and matKhau=?";
        List<ThuThu> list=getData(sql,id,pass);
        if (list.size()==0){
            return -1;
        }else {
            return 1;
        }
    }
}
