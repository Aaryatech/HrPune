package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClaimApp {

    @SerializedName("caHeadId")
    @Expose
    private Integer caHeadId;
    @SerializedName("empId")
    @Expose
    private Integer empId;
    @SerializedName("empCode")
    @Expose
    private String empCode;
    @SerializedName("empName")
    @Expose
    private String empName;
    @SerializedName("projId")
    @Expose
    private Integer projId;
    @SerializedName("claimTitle")
    @Expose
    private String claimTitle;
    @SerializedName("projectTitle")
    @Expose
    private String projectTitle;
    @SerializedName("caFromDt")
    @Expose
    private String caFromDt;
    @SerializedName("caToDt")
    @Expose
    private String caToDt;
    @SerializedName("claimStatus")
    @Expose
    private Integer claimStatus;
    @SerializedName("claimAmount")
    @Expose
    private Integer claimAmount;
    @SerializedName("circulatedTo")
    @Expose
    private String circulatedTo;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;
    @SerializedName("caIniAuthEmpId")
    @Expose
    private Integer caIniAuthEmpId;
    @SerializedName("caFinAuthEmpId")
    @Expose
    private Integer caFinAuthEmpId;
    @SerializedName("empPhoto")
    @Expose
    private String empPhoto;

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

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getClaimTitle() {
        return claimTitle;
    }

    public void setClaimTitle(String claimTitle) {
        this.claimTitle = claimTitle;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getCaFromDt() {
        return caFromDt;
    }

    public void setCaFromDt(String caFromDt) {
        this.caFromDt = caFromDt;
    }

    public String getCaToDt() {
        return caToDt;
    }

    public void setCaToDt(String caToDt) {
        this.caToDt = caToDt;
    }

    public Integer getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(Integer claimStatus) {
        this.claimStatus = claimStatus;
    }

    public Integer getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Integer claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getCirculatedTo() {
        return circulatedTo;
    }

    public void setCirculatedTo(String circulatedTo) {
        this.circulatedTo = circulatedTo;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    public Integer getCaIniAuthEmpId() {
        return caIniAuthEmpId;
    }

    public void setCaIniAuthEmpId(Integer caIniAuthEmpId) {
        this.caIniAuthEmpId = caIniAuthEmpId;
    }

    public Integer getCaFinAuthEmpId() {
        return caFinAuthEmpId;
    }

    public void setCaFinAuthEmpId(Integer caFinAuthEmpId) {
        this.caFinAuthEmpId = caFinAuthEmpId;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    @Override
    public String toString() {
        return "ClaimApp{" +
                "caHeadId=" + caHeadId +
                ", empId=" + empId +
                ", empCode='" + empCode + '\'' +
                ", empName='" + empName + '\'' +
                ", projId=" + projId +
                ", claimTitle='" + claimTitle + '\'' +
                ", projectTitle='" + projectTitle + '\'' +
                ", caFromDt='" + caFromDt + '\'' +
                ", caToDt='" + caToDt + '\'' +
                ", claimStatus=" + claimStatus +
                ", claimAmount=" + claimAmount +
                ", circulatedTo='" + circulatedTo + '\'' +
                ", exVar1='" + exVar1 + '\'' +
                ", caIniAuthEmpId=" + caIniAuthEmpId +
                ", caFinAuthEmpId=" + caFinAuthEmpId +
                ", empPhoto='" + empPhoto + '\'' +
                '}';
    }

    //    private Integer claimId;
//    private Integer empId;
//    private String empCode;
//    private String empName;
//    private Integer projectId;
//    private String projectTitle;
//    private Integer claimTypeId;
//    private String claimTypeName;
//    private Integer claimAmount;
//    private String claimRemarks;
//    private String claimDate;
//    private String circulatedTo;
//    private Integer exInt1;
//    private Integer caIniAuthEmpId;
//    private Integer caFinAuthEmpId;
//    private String empPhoto;



}
