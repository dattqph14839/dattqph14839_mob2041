package com.dattqph14839.mob_2041.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dattqph14839.mob_2041.database.MySQLite;
import com.dattqph14839.mob_2041.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    private MySQLite mySQLite;
    private SQLiteDatabase db;
    public SachDao(Context context){
        mySQLite=new MySQLite(context);
        db=mySQLite.getWritableDatabase();
    }
    public long insert(Sach s){
        ContentValues values=new ContentValues();
        values.put("tenSach",s.getTenSach());
        values.put("giaThue",s.getGiaThue());
        values.put("maLoai",s.getMaLoai());
        return db.insert("Sach",null,values);
    }
    public int upate(Sach s){
        ContentValues values=new ContentValues();
        values.put("tenSach",s.getTenSach());
        values.put("giaThue",s.getGiaThue());
        values.put("maLoai",s.getMaLoai());
        return db.update("Sach",values,"maSach=?",new String[]{s.getMaSach()+""});
    }
    public int delete(int s){
        return db.delete("Sach","maSach=?",new String[]{s+""});
    }
    public List<Sach> getDaTa(String sql, String...selectionArgs){
        List<Sach> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        if (c.getCount() > 0) {
            c.moveToNext();
            while (!c.isAfterLast()) {
                int a = c.getInt(0);
                String b = c.getString(1);
                int cc = c.getInt(2);
                int d = c.getInt(3);
                list.add(new Sach(a,b,cc,d));
                c.moveToNext();
            }
            c.close();
        }
        return list;
    }
    public List<Sach> getAll(){
        String sql="select * from Sach";
        return getDaTa(sql);
    }
    public Sach getID(String id){
        String sql="select * from Sach where maSach=?";
        List<Sach> list=getDaTa(sql,id);
        return list.get(0);
    }
}
