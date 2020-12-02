package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplyLeaveDetail {

    @SerializedName("leaveId")
    @Expose
    private Integer leaveId;
    @SerializedName("calYrId")
    @Expose
    private Integer calYrId;
    @SerializedName("leaveTitle")
    @Expose
    private String leaveTitle;
    @SerializedName("empId")
    @Expose
    private Integer empId;
    @SerializedName("empName")
    @Expose
    private String empName;
    @SerializedName("iniAuthEmpId")
    @Expose
    private String iniAuthEmpId;
    @SerializedName("finAuthEmpId")
    @Expose
    private String finAuthEmpId;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("empCode")
    @Expose
    private String empCode;
    @SerializedName("lvTypeId")
    @Expose
    private Integer lvTypeId;
    @SerializedName("leaveTypeName")
    @Expose
    private String leaveTypeName;
    @SerializedName("leaveDuration")
    @Expose
    private String leaveDuration;
    @SerializedName("leaveFromdt")
    @Expose
    private String leaveFromdt;
    @SerializedName("leaveTodt")
    @Expose
    private String leaveTodt;
    @SerializedName("leaveNumDays")
    @Expose
    private Integer leaveNumDays;
    @SerializedName("leaveEmpReason")
    @Expose
    private String leaveEmpReason;
    @SerializedName("circulatedTo")
    @Expose
    private String circulatedTo;
    @SerializedName("empPhoto")
    @Expose
    private String empPhoto;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public Integer getCalYrId() {
        return calYrId;
    }

    public void setCalYrId(Integer calYrId) {
        this.calYrId = calYrId;
    }

    public String getLeaveTitle() {
        return leaveTitle;
    }

    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getIniAuthEmpId() {
        return iniAuthEmpId;
    }

    public void setIniAuthEmpId(String iniAuthEmpId) {
        this.iniAuthEmpId = iniAuthEmpId;
    }

    public String getFinAuthEmpId() {
        return finAuthEmpId;
    }

    public void setFinAuthEmpId(String finAuthEmpId) {
        this.finAuthEmpId = finAuthEmpId;
    }

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Integer getLvTypeId() {
        return lvTypeId;
    }

    public void setLvTypeId(Integer lvTypeId) {
        this.lvTypeId = lvTypeId;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    public String getLeaveDuration() {
        return leaveDuration;
    }

    public void setLeaveDuration(String leaveDuration) {
        this.leaveDuration = leaveDuration;
    }

    public String getLeaveFromdt() {
        return leaveFromdt;
    }

    public void setLeaveFromdt(String leaveFromdt) {
        this.leaveFromdt = leaveFromdt;
    }

    public String getLeaveTodt() {
        return leaveTodt;
    }

    public void setLeaveTodt(String leaveTodt) {
        this.leaveTodt = leaveTodt;
    }

    public Integer getLeaveNumDays() {
        return leaveNumDays;
    }

    public void setLeaveNumDays(Integer leaveNumDays) {
        this.leaveNumDays = leaveNumDays;
    }

    public String getLeaveEmpReason() {
        return leaveEmpReason;
    }

    public void setLeaveEmpReason(String leaveEmpReason) {
        this.leaveEmpReason = leaveEmpReason;
    }

    public String getCirculatedTo() {
        return circulatedTo;
    }

    public void setCirculatedTo(String circulatedTo) {
        this.circulatedTo = circulatedTo;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getExVar2() {
        return exVar2;
    }

    public void setExVar2(String exVar2) {
        this.exVar2 = exVar2;
    }

    @Override
    public String toString() {
        return "ApplyLeaveDetail{" +
                "leaveId=" + leaveId +
                ", calYrId=" + calYrId +
                ", leaveTitle='" + leaveTitle + '\'' +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", iniAuthEmpId='" + iniAuthEmpId + '\'' +
                ", finAuthEmpId='" + finAuthEmpId + '\'' +
                ", exInt1=" + exInt1 +
                ", empCode='" + empCode + '\'' +
                ", lvTypeId=" + lvTypeId +
                ", leaveTypeName='" + leaveTypeName + '\'' +
                ", leaveDuration='" + leaveDuration + '\'' +
                ", leaveFromdt='" + leaveFromdt + '\'' +
                ", leaveTodt='" + leaveTodt + '\'' +
                ", leaveNumDays=" + leaveNumDays +
                ", leaveEmpReason='" + leaveEmpReason + '\'' +
                ", circulatedTo='" + circulatedTo + '\'' +
                ", empPhoto='" + empPhoto + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                '}';
    }
}
