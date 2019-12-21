package com.example.myitime;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Date implements Serializable {


    public Date(String name, String time, int picture, int years, int months, int days, int hous, int minutes,int targetyear,int targetmonth,int targetday,int targethour,int targetminute,int daysnumber,int zhouqi) {
        this.name = name;
        this.time = time;
        this.picture = picture;
        this.years = years;
        this.months = months;
        this.days = days;
        this.hous = hous;
        this.minutes = minutes;
        this.targetday=targetday;
        this.targetmonth=targetmonth;
        this.targetyear=targetyear;
        this.targethour=targethour;
        this.targetminute=targetminute;
        this.daysnumber=daysnumber;
        this.zhouqi=zhouqi;

       // this.expirationTime = expirationTime;
    }

    private String name;         //标题
    private String time;         //标签
    private int picture;        //图片
    private int years;          //相差年份
    private int months;          //相差月份
    private int days;             //相差日数
    private int hous;              //相差小时数
    private int minutes;            //相差分钟数
    private int seconds;             //相差秒数
    private String expirationTime;      //描述
    private int targetyear;             //目的年数
    private int targetmonth;              //目的月数
    private int targetday;                 //目的日数
    private int targethour;               //目的小时数
    private int targetminute;             //目的分钟数
    private int daysnumber;                //相差总日数
    private Bitmap bitmap;                 //图片
    private int zhouqi;                  //设置周期

    public int getZhouqi() {
        return zhouqi;
    }

    public void setZhouqi(int zhouqi) {
        this.zhouqi = zhouqi;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getDaysnumber() {
        return daysnumber;
    }

    public void setDaysnumber(int daysnumber) {
        this.daysnumber = daysnumber;
    }

    public int getTargethour() {
        return targethour;
    }

    public void setTargethour(int targethour) {
        this.targethour = targethour;
    }

    public int getTargetminute() {
        return targetminute;
    }

    public void setTargetminute(int targetminute) {
        this.targetminute = targetminute;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getTargetyear() {
        return targetyear;
    }

    public void setTargetyear(int targetyear) {
        this.targetyear = targetyear;
    }

    public int getTargetmonth() {
        return targetmonth;
    }

    public void setTargetmonth(int targetmonth) {
        this.targetmonth = targetmonth;
    }

    public int getTargetday() {
        return targetday;
    }

    public void setTargetday(int targetday) {
        this.targetday = targetday;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }


    public String getExpirationTime() {

        if (targetmonth == 1) {
            return expirationTime = "Jan" + "," + targetday + "," + targetyear;
        } else if (targetmonth == 2) {
            return expirationTime = "Feb" + "," + targetday + "," + targetyear;
        } else if (targetmonth == 3) {
            return expirationTime = "Mar" + "," + targetday + "," + targetyear;
        } else if (targetmonth == 4) {
            return expirationTime = "Apr" + "," + targetday + "," + targetyear;
        } else if (targetmonth == 5) {
            return expirationTime = "May" + "," + targetday + "," + targetyear;
        } else if (targetmonth == 6) {
            return expirationTime = "Jun" + "," + targetday + "," + targetyear;
        } else if (targetmonth==7) {
            return expirationTime = "Jul" + "," + targetday + "," + targetyear;
        } else if (targetmonth==8) {
            return expirationTime = "Aug" + "," + targetday + "," + targetyear;
        } else if (targetmonth==9) {
            return expirationTime = "Sep" + "," + targetday + "," + targetyear;
        } else if (targetmonth==10) {
            return expirationTime = "Oct" + "," + targetday + "," + targetyear;
        }else if (targetmonth==11) {
            return expirationTime = "Nov" + "," + targetday + "," + targetyear;
        }else if (targetmonth==12){
                return expirationTime = "Dec" + "," +  targetday+ "," + targetyear;
        }else {
            return "  ";
        }
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHous() {
        return hous;
    }

    public void setHous(int hous) {
        this.hous = hous;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}