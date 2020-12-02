package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaveInfo {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("dailyrecordlistforcompoff")
    @Expose
    private List<DailyRecordForCompOff> dailyrecordlistforcompoff;
    @SerializedName("error")
    @Expose
    private Boolean error;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DailyRecordForCompOff> getDailyrecordlistforcompoff() {
        return dailyrecordlistforcompoff;
    }

    public void setDailyrecordlistforcompoff(List<DailyRecordForCompOff> dailyrecordlistforcompoff) {
        this.dailyrecordlistforcompoff = dailyrecordlistforcompoff;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "LeaveInfo{" +
                "msg='" + msg + '\'' +
                ", dailyrecordlistforcompoff=" + dailyrecordlistforcompoff +
                ", error=" + error +
                '}';
    }
}
