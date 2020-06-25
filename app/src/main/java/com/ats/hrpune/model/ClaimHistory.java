package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClaimHistory {
    @SerializedName("caHeadId")
    @Expose
    private Integer caHeadId;
    @SerializedName("empId")
    @Expose
    private Integer empId;
    @SerializedName("projectId")
    @Expose
    private Integer projectId;
    @SerializedName("projectTitle")
    @Expose
    private String projectTitle;
    @SerializedName("claimTitle")
    @Expose
    private String claimTitle;
    @SerializedName("claimFromDate")
    @Expose
    private String claimFromDate;
    @SerializedName("claimToDate")
    @Expose
    private String claimToDate;
    @SerializedName("claimAmount")
    @Expose
    private Integer claimAmount;
    @SerializedName("claimFinalStatus")
    @Expose
    private Integer claimFinalStatus;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;
    @SerializedName("empFname")
    @Expose
    private String empFname;
    @SerializedName("empSname")
    @Expose
    private String empSname;
    @SerializedName("empCode")
    @Expose
    private String empCode;
    @SerializedName("empDeptName")
    @Expose
    private String empDeptName;

    public Integer getCaHeadId() {
        return caHeadId;
    }

    public void setCaHeadId(Integer caHeadId) {
        this.caHeadId = caHeadId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getClaimTitle() {
        return claimTitle;
    }

    public void setClaimTitle(String claimTitle) {
        this.claimTitle = claimTitle;
    }

    public String getClaimFromDate() {
        return claimFromDate;
    }

    public void setClaimFromDate(String claimFromDate) {
        this.claimFromDate = claimFromDate;
    }

    public String getClaimToDate() {
        return claimToDate;
    }

    public void setClaimToDate(String claimToDate) {
        this.claimToDate = claimToDate;
    }

    public Integer getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Integer claimAmount) {
        this.claimAmount = claimAmount;
    }

    public Integer getClaimFinalStatus() {
        return claimFinalStatus;
    }

    public void setClaimFinalStatus(Integer claimFinalStatus) {
        this.claimFinalStatus = claimFinalStatus;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    public String getEmpFname() {
        return empFname;
    }

    public void setEmpFname(String empFname) {
        this.empFname = empFname;
    }

    public String getEmpSname() {
        return empSname;
    }

    public void setEmpSname(String empSname) {
        this.empSname = empSname;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpDeptName() {
        return empDeptName;
    }

    public void setEmpDeptName(String empDeptName) {
        this.empDeptName = empDeptName;
    }

    @Override
    public String toString() {
        return "ClaimHistory{" +
                "caHeadId=" + caHeadId +
                ", empId=" + empId +
                ", projectId=" + projectId +
                ", projectTitle='" + projectTitle + '\'' +
                ", claimTitle='" + claimTitle + '\'' +
                ", claimFromDate='" + claimFromDate + '\'' +
                ", claimToDate='" + claimToDate + '\'' +
                ", claimAmount=" + claimAmount +
                ", claimFinalStatus=" + claimFinalStatus +
                ", exVar1='" + exVar1 + '\'' +
                ", empFname='" + empFname + '\'' +
                ", empSname='" + empSname + '\'' +
                ", empCode='" + empCode + '\'' +
                ", empDeptName='" + empDeptName + '\'' +
                '}';
    }
}
