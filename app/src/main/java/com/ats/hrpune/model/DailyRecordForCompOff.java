package com.ats.hrpune.model;

public class DailyRecordForCompOff {
    private int id;
    private String attDate;
    private int empId;
    private int lvSumupId ;
    private String attStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttDate() {
        return attDate;
    }

    public void setAttDate(String attDate) {
        this.attDate = attDate;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getLvSumupId() {
        return lvSumupId;
    }

    public void setLvSumupId(int lvSumupId) {
        this.lvSumupId = lvSumupId;
    }

    public String getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(String attStatus) {
        this.attStatus = attStatus;
    }

    @Override
    public String toString() {
        return "DailyRecordForCompOff{" +
                "id=" + id +
                ", attDate='" + attDate + '\'' +
                ", empId=" + empId +
                ", lvSumupId=" + lvSumupId +
                ", attStatus='" + attStatus + '\'' +
                '}';
    }
}
