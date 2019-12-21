package com.example.myitime;

import java.io.Serializable;

public class Set implements Serializable {
    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public Set(String setting, int pictureId, String dating) {
        this.setting = setting;
        this.pictureId = pictureId;
        this.date = dating;
    }

    String setting;
    int pictureId;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String days) {
        this.date =days;
    }
}
