package com.example.liujiao.sanitationwork.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liujiao on 2017/5/3.
 */

public class Work implements Parcelable {
    private int wid;
    private String wname;
    private String wpay;
    private int wnum;
    private String wstate;
    private String wduty;
    private String wrequire;

    public String getWduty() {
        return wduty;
    }

    public void setWduty(String wduty) {
        this.wduty = wduty;
    }

    public String getWrequire() {
        return wrequire;
    }

    public void setWrequire(String wrequire) {
        this.wrequire = wrequire;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getWpay() {
        return wpay;
    }

    public void setWpay(String wpay) {
        this.wpay = wpay;
    }

    public int getWnum() {
        return wnum;
    }

    public void setWnum(int wnum) {
        this.wnum = wnum;
    }

    public String getWstate() {
        return wstate;
    }

    public void setWstate(String wstate) {
        this.wstate = wstate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 用于将想要传递的数据写入到Parcel容器中。
     *
     * @param parcel
     * @param i
     */
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(wid);
        parcel.writeString(wname);
        parcel.writeString(wduty);
        parcel.writeString(wpay);
        parcel.writeString(wrequire);
        parcel.writeString(wstate);
        parcel.writeInt(wnum);

    }

    public static final Parcelable.Creator<Work> CREATOR = new Parcelable.Creator<Work>() {
        @Override
        public Work createFromParcel(Parcel parcel) {
            Work work = new Work();
            work.wid = parcel.readInt();
            work.wname = parcel.readString();
            work.wduty = parcel.readString();
            work.wpay = parcel.readString();
            work.wrequire = parcel.readString();
            work.wstate = parcel.readString();
            work.wnum = parcel.readInt();
            return work;
        }

        @Override
        public Work[] newArray(int i) {
            return new Work[i];
        }
    };
}
