package com.dattqph14839.mob_2041.model;

import java.util.Date;

public class PhieuMuon {
    private int maPM;
    private String maTT;
    private int maTV,maSach,tienThue;
    private Date ngay;
    private int traSach;

    public PhieuMuon() {
    }


    public PhieuMuon(int maPM, String maTT, int maTV, int maSach, int tienThue, Date ngay, int traSach) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngay = ngay;
        this.traSach = traSach;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    @Override
    public String toString() {
        return "PhieuMuon{" +
                "maPM=" + maPM +
                ", maTT='" + maTT + '\'' +
                ", maTV=" + maTV +
                ", maSach=" + maSach +
                ", tienThue=" + tienThue +
                ", traSach=" + traSach +
                ", ngay=" + ngay +
                '}';
    }
}
