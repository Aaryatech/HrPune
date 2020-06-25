package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StructureData {

    @SerializedName("clmStructDetailsId")
    @Expose
    private Integer clmStructDetailsId;
    @SerializedName("clmStructHeadId")
    @Expose
    private Integer clmStructHeadId;
    @SerializedName("clmTypeId")
    @Expose
    private Integer clmTypeId;
    @SerializedName("clmAmt")
    @Expose
    private Integer clmAmt;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("makerUserId")
    @Expose
    private Integer makerUserId;
    @SerializedName("makerDatetime")
    @Expose
    private String makerDatetime;
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

    public Integer getClmStructDetailsId() {
        return clmStructDetailsId;
    }

    public void setClmStructDetailsId(Integer clmStructDetailsId) {
        this.clmStructDetailsId = clmStructDetailsId;
    }

    public Integer getClmStructHeadId() {
        return clmStructHeadId;
    }

    public void setClmStructHeadId(Integer clmStructHeadId) {
        this.clmStructHeadId = clmStructHeadId;
    }

    public Integer getClmTypeId() {
        return clmTypeId;
    }

    public void setClmTypeId(Integer clmTypeId) {
        this.clmTypeId = clmTypeId;
    }

    public Integer getClmAmt() {
        return clmAmt;
    }

    public void setClmAmt(Integer clmAmt) {
        this.clmAmt = clmAmt;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getMakerUserId() {
        return makerUserId;
    }

    public void setMakerUserId(Integer makerUserId) {
        this.makerUserId = makerUserId;
    }

    public String getMakerDatetime() {
        return makerDatetime;
    }

    public void setMakerDatetime(String makerDatetime) {
        this.makerDatetime = makerDatetime;
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

    @Override
    public String toString() {
        return "StructureData{" +
                "clmStructDetailsId=" + clmStructDetailsId +
                ", clmStructHeadId=" + clmStructHeadId +
                ", clmTypeId=" + clmTypeId +
                ", clmAmt=" + clmAmt +
                ", delStatus=" + delStatus +
                ", isActive=" + isActive +
                ", makerUserId=" + makerUserId +
                ", makerDatetime='" + makerDatetime + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                '}';
    }
}
