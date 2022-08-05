package com.dattqph14839.mob_2041.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dattqph14839.mob_2041.database.MySQLite;
import com.dattqph14839.mob_2041.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDao {
    private MySQLite mySQLite;
    private SQLiteDatabase db;

    public ThanhVienDao(Context context) {
        mySQLite=new MySQLite(context);
        db=mySQLite.getWritableDatabase();
    }
    public long insert(ThanhVien t){
        ContentValues values=new ContentValues();
        values.put("hoTen",t.getHoTen());
        values.put("namSinh",t.getNamSinh());
        return db.insert("ThanhVien",null,values);
    }
    public int update(ThanhVien t){
        ContentValues values=new ContentValues();
        values.put("hoTen",t.getHoTen());
        values.put("namSinh",t.getNamSinh());
        return db.update("ThanhVien",values,"maTV=?",new String[]{t.getMaTV()+""});
    }
    public int delete(int ma){
        return db.delete("ThanhVien","maTV=?",new String[]{ma+""});
    }
    public List<ThanhVien> getDaTa(String sql,String...selectionArgs){
        List<ThanhVien> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        if (c.getCount() > 0) {
            c.moveToNext();
            while (!c.isAfterLast()) {
                int a = c.getInt(0);
                String b = c.getString(1);
                String cc = c.getString(2);
                list.add(new ThanhVien(a,b,cc));
                c.moveToNext();
            }
            c.close();
        }
        return list;
    }
    public List<ThanhVien> getAll(){
        String sql="select * from ThanhVien";
        return getDaTa(sql);
    }
    public ThanhVien getID(String id){
        String sql="select * from ThanhVien where maTV=?";
        List<ThanhVien> list=getDaTa(sql,id);
        return list.get(0);
    }
}
