package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClaimHeader {

    @SerializedName("claimTrailPkey")
    @Expose
    private Integer claimTrailPkey;
    @SerializedName("claimId")
    @Expose
    private Integer claimId;
    @SerializedName("empId")
    @Expose
    private Integer empId;
    @SerializedName("empRemarks")
    @Expose
    private String empRemarks;
    @SerializedName("empCode")
    @Expose
    private String empCode;
    @SerializedName("caFromDt")
    @Expose
    private String caFromDt;
    @SerializedName("caToDt")
    @Expose
    private String caToDt;
    @SerializedName("projectTitle")
    @Expose
    private String projectTitle;
    @SerializedName("claimStatus")
    @Expose
    private Integer claimStatus;
    @SerializedName("claimTitle")
    @Expose
    private String claimTitle;
    @SerializedName("claimAmount")
    @Expose
    private String claimAmount;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("makerEnterDatetime")
    @Expose
    private String makerEnterDatetime;
    @SerializedName("empFname")
    @Expose
    private String empFname;
    @SerializedName("empMname")
    @Expose
    private String empMname;
    @SerializedName("empSname")
    @Expose
    private String empSname;
    int visibleStatus;

    public Integer getClaimTrailPkey() {
        return claimTrailPkey;
    }

    public void setClaimTrailPkey(Integer claimTrailPkey) {
        this.claimTrailPkey = claimTrailPkey;
    }

    public Integer getClaimId() {
        return claimId;
    }

    public void setClaimId(Integer claimId) {
        this.claimId = claimId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpRemarks() {
        return empRemarks;
    }

    public void setEmpRemarks(String empRemarks) {
        this.empRemarks = empRemarks;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
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

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public Integer getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(Integer claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getClaimTitle() {
        return claimTitle;
    }

    public void setClaimTitle(String claimTitle) {
        this.claimTitle = claimTitle;
    }

    public String getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(String claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMakerEnterDatetime() {
        return makerEnterDatetime;
    }

    public void setMakerEnterDatetime(String makerEnterDatetime) {
        this.makerEnterDatetime = makerEnterDatetime;
    }

    public String getEmpFname() {
        return empFname;
    }

    public void setEmpFname(String empFname) {
        this.empFname = empFname;
    }

    public String getEmpMname() {
        return empMname;
    }

    public void setEmpMname(String empMname) {
        this.empMname = empMname;
    }

    public String getEmpSname() {
        return empSname;
    }

    public void setEmpSname(String empSname) {
        this.empSname = empSname;
    }

    public int getVisibleStatus() {
        return visibleStatus;
    }

    public void setVisibleStatus(int visibleStatus) {
        this.visibleStatus = visibleStatus;
    }

    @Override
    public String toString() {
        return "ClaimHeader{" +
                "claimTrailPkey=" + claimTrailPkey +
                ", claimId=" + claimId +
                ", empId=" + empId +
                ", empRemarks='" + empRemarks + '\'' +
                ", empCode='" + empCode + '\'' +
                ", caFromDt='" + caFromDt + '\'' +
                ", caToDt='" + caToDt + '\'' +
                ", projectTitle='" + projectTitle + '\'' +
                ", claimStatus=" + claimStatus +
                ", claimTitle='" + claimTitle + '\'' +
                ", claimAmount='" + claimAmount + '\'' +
                ", userName='" + userName + '\'' +
                ", makerEnterDatetime='" + makerEnterDatetime + '\'' +
                ", empFname='" + empFname + '\'' +
                ", empMname='" + empMname + '\'' +
                ", empSname='" + empSname + '\'' +
                ", visibleStatus=" + visibleStatus +
                '}';
    }
}
