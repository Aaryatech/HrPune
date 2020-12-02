package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinalApprove {

    private String fromDate;
    private String toDate;
    private Integer userId;
    private Integer year;
    private Integer month;
    private Integer empId;

    public FinalApprove(String fromDate, String toDate, Integer userId, Integer year, Integer month, Integer empId) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.empId = empId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "FinalApprove{" +
                "fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", userId=" + userId +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", empId=" + empId +
                '}';
    }
}
