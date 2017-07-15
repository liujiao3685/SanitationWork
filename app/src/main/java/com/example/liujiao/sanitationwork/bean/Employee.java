package com.example.liujiao.sanitationwork.bean;

/**
 * Created by liujiao on 2017/5/3.
 */

public class Employee {
    private int eid;
    private String ename;
    private String epay;
    private String estate;
    private int ewid;//岗位ID
    private String ecard;//工资卡号

    public int getEwid() {
        return ewid;
    }

    public void setEwid(int ewid) {
        this.ewid = ewid;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEpay() {
        return epay;
    }

    public void setEpay(String epay) {
        this.epay = epay;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }


    public String getEcard() {
        return ecard;
    }

    public void setEcard(String ecard) {
        this.ecard = ecard;
    }
}
