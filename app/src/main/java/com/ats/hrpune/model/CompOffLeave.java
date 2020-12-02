package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompOffLeave {

    @SerializedName("empTypeId")
    @Expose
    private Integer empTypeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("attType")
    @Expose
    private String attType;
    @SerializedName("lmApplicable")
    @Expose
    private String lmApplicable;
    @SerializedName("halfDay")
    @Expose
    private String halfDay;
    @SerializedName("whWork")
    @Expose
    private String whWork;
    @SerializedName("minWorkHr")
    @Expose
    private String minWorkHr;
    @SerializedName("minworkApplicable")
    @Expose
    private String minworkApplicable;
    @SerializedName("otApplicable")
    @Expose
    private String otApplicable;
    @SerializedName("otTime")
    @Expose
    private String otTime;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("otType")
    @Expose
    private String otType;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("weeklyHolidayLateAllowed")
    @Expose
    private Integer weeklyHolidayLateAllowed;
    @SerializedName("weeklyHolidayLateAllowedMin")
    @Expose
    private Integer weeklyHolidayLateAllowedMin;
    @SerializedName("earlyGoingAllowed")
    @Expose
    private Integer earlyGoingAllowed;
    @SerializedName("earlyGoingMin")
    @Expose
    private Integer earlyGoingMin;
    @SerializedName("maxLateTimeAllowed")
    @Expose
    private Integer maxLateTimeAllowed;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exInt2")
    @Expose
    private Integer exInt2;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;
    @SerializedName("prodIncentiveApp")
    @Expose
    private String prodIncentiveApp;

    public Integer getEmpTypeId() {
        return empTypeId;
    }

    public void setEmpTypeId(Integer empTypeId) {
        this.empTypeId = empTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAttType() {
        return attType;
    }

    public void setAttType(String attType) {
        this.attType = attType;
    }

    public String getLmApplicable() {
        return lmApplicable;
    }

    public void setLmApplicable(String lmApplicable) {
        this.lmApplicable = lmApplicable;
    }

    public String getHalfDay() {
        return halfDay;
    }

    public void setHalfDay(String halfDay) {
        this.halfDay = halfDay;
    }

    public String getWhWork() {
        return whWork;
    }

    public void setWhWork(String whWork) {
        this.whWork = whWork;
    }

    public String getMinWorkHr() {
        return minWorkHr;
    }

    public void setMinWorkHr(String minWorkHr) {
        this.minWorkHr = minWorkHr;
    }

    public String getMinworkApplicable() {
        return minworkApplicable;
    }

    public void setMinworkApplicable(String minworkApplicable) {
        this.minworkApplicable = minworkApplicable;
    }

    public String getOtApplicable() {
        return otApplicable;
    }

    public void setOtApplicable(String otApplicable) {
        this.otApplicable = otApplicable;
    }

    public String getOtTime() {
        return otTime;
    }

    public void setOtTime(String otTime) {
        this.otTime = otTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOtType() {
        return otType;
    }

    public void setOtType(String otType) {
        this.otType = otType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getWeeklyHolidayLateAllowed() {
        return weeklyHolidayLateAllowed;
    }

    public void setWeeklyHolidayLateAllowed(Integer weeklyHolidayLateAllowed) {
        this.weeklyHolidayLateAllowed = weeklyHolidayLateAllowed;
    }

    public Integer getWeeklyHolidayLateAllowedMin() {
        return weeklyHolidayLateAllowedMin;
    }

    public void setWeeklyHolidayLateAllowedMin(Integer weeklyHolidayLateAllowedMin) {
        this.weeklyHolidayLateAllowedMin = weeklyHolidayLateAllowedMin;
    }

    public Integer getEarlyGoingAllowed() {
        return earlyGoingAllowed;
    }

    public void setEarlyGoingAllowed(Integer earlyGoingAllowed) {
        this.earlyGoingAllowed = earlyGoingAllowed;
    }

    public Integer getEarlyGoingMin() {
        return earlyGoingMin;
    }

    public void setEarlyGoingMin(Integer earlyGoingMin) {
        this.earlyGoingMin = earlyGoingMin;
    }

    public Integer getMaxLateTimeAllowed() {
        return maxLateTimeAllowed;
    }

    public void setMaxLateTimeAllowed(Integer maxLateTimeAllowed) {
        this.maxLateTimeAllowed = maxLateTimeAllowed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public Integer getExInt2() {
        return exInt2;
    }

    public void setExInt2(Integer exInt2) {
        this.exInt2 = exInt2;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    public String getExVar2() {
        return exVar2;
    }

    public void setExVar2(String exVar2) {
        this.exVar2 = exVar2;
    }

    public String getProdIncentiveApp() {
        return prodIncentiveApp;
    }

    public void setProdIncentiveApp(String prodIncentiveApp) {
        this.prodIncentiveApp = prodIncentiveApp;
    }

    @Override
    public String toString() {
        return "CompOffLeave{" +
                "empTypeId=" + empTypeId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", attType='" + attType + '\'' +
                ", lmApplicable='" + lmApplicable + '\'' +
                ", halfDay='" + halfDay + '\'' +
                ", whWork='" + whWork + '\'' +
                ", minWorkHr='" + minWorkHr + '\'' +
                ", minworkApplicable='" + minworkApplicable + '\'' +
                ", otApplicable='" + otApplicable + '\'' +
                ", otTime='" + otTime + '\'' +
                ", details='" + details + '\'' +
                ", otType='" + otType + '\'' +
                ", companyId=" + companyId +
                ", weeklyHolidayLateAllowed=" + weeklyHolidayLateAllowed +
                ", weeklyHolidayLateAllowedMin=" + weeklyHolidayLateAllowedMin +
                ", earlyGoingAllowed=" + earlyGoingAllowed +
                ", earlyGoingMin=" + earlyGoingMin +
                ", maxLateTimeAllowed=" + maxLateTimeAllowed +
                ", status=" + status +
                ", delStatus=" + delStatus +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", prodIncentiveApp='" + prodIncentiveApp + '\'' +
                '}';
    }
}
