package com.dattqph14839.mob_2041.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dattqph14839.mob_2041.database.MySQLite;
import com.dattqph14839.mob_2041.model.PhieuMuon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuMuonDao {
    private MySQLite mySQLite;
    private SQLiteDatabase db;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    public PhieuMuonDao(Context context){
        mySQLite=new MySQLite(context);
        db=mySQLite.getWritableDatabase();
    }
    public long insert(PhieuMuon p){
        ContentValues values=new ContentValues();
        values.put("maTT",p.getMaTT());
        values.put("maTV",p.getMaTV());
        values.put("maSach",p.getMaSach());
        values.put("tienThue",p.getTienThue());
        values.put("ngay",sdf.format(p.getNgay()));
        values.put("traSach",p.getTraSach());
        return db.insert("PhieuMuon",null,values);
    }
    public int upate(PhieuMuon p){
        ContentValues values=new ContentValues();
        values.put("maTT",p.getMaTT());
        values.put("maTV",p.getMaTV());
        values.put("maSach",p.getMaSach());
        values.put("tienThue",p.getTienThue());
        values.put("ngay",sdf.format(p.getNgay()));
        values.put("traSach",p.getTraSach());
        return db.update("PhieuMuon",values,"maPM=?",new String[]{p.getMaPM()+""});
    }
    public int delete(int p){
        return db.delete("PhieuMuon","maPM=?",new String[]{p+""});
    }
    public List<PhieuMuon> getDaTa(String sql, String...selectionArgs){
        List<PhieuMuon> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        if (c.getCount() > 0) {
            c.moveToNext();
            while (!c.isAfterLast()) {
                int a = c.getInt(0);
                String b = c.getString(1);
                int cc=c.getInt(2);
                int d=c.getInt(3);
                int dd=c.getInt(4);
                Date ngay;
                try {
                    ngay=sdf.parse(c.getString(5));
                } catch (ParseException e) {
                    ngay=new Date("2002-09-13");
                }
                int ddd=c.getInt(6);
                list.add(new PhieuMuon(a,b,cc,d,dd,ngay,ddd));
                c.moveToNext();
            }
            c.close();
        }
        return list;
    }
    public List<PhieuMuon> getAll(){
        String sql="select * from PhieuMuon";
        return getDaTa(sql);
    }
    public PhieuMuon getID(String id){
        String sql="select * from PhieuMuon where maPM=?";
        List<PhieuMuon> list=getDaTa(sql,id);
        return list.get(0);
    }
}
