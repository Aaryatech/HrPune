package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypeClaim {

    @SerializedName("clmStructDetailsId")
    @Expose
    private Integer clmStructDetailsId;
    @SerializedName("clmTypeId")
    @Expose
    private Integer clmTypeId;
    @SerializedName("clmAmt")
    @Expose
    private Integer clmAmt;
    @SerializedName("clmStructHeadId")
    @Expose
    private Integer clmStructHeadId;
    @SerializedName("claimStructName")
    @Expose
    private String claimStructName;
    @SerializedName("claimTypeTitle")
    @Expose
    private String claimTypeTitle;
    @SerializedName("claimTypeTitleShort")
    @Expose
    private String claimTypeTitleShort;

    public Integer getClmStructDetailsId() {
        return clmStructDetailsId;
    }

    public void setClmStructDetailsId(Integer clmStructDetailsId) {
        this.clmStructDetailsId = clmStructDetailsId;
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

    public Integer getClmStructHeadId() {
        return clmStructHeadId;
    }

    public void setClmStructHeadId(Integer clmStructHeadId) {
        this.clmStructHeadId = clmStructHeadId;
    }

    public String getClaimStructName() {
        return claimStructName;
    }

    public void setClaimStructName(String claimStructName) {
        this.claimStructName = claimStructName;
    }

    public String getClaimTypeTitle() {
        return claimTypeTitle;
    }

    public void setClaimTypeTitle(String claimTypeTitle) {
        this.claimTypeTitle = claimTypeTitle;
    }

    public String getClaimTypeTitleShort() {
        return claimTypeTitleShort;
    }

    public void setClaimTypeTitleShort(String claimTypeTitleShort) {
        this.claimTypeTitleShort = claimTypeTitleShort;
    }

    @Override
    public String toString() {
        return "TypeClaim{" +
                "clmStructDetailsId=" + clmStructDetailsId +
                ", clmTypeId=" + clmTypeId +
                ", clmAmt=" + clmAmt +
                ", clmStructHeadId=" + clmStructHeadId +
                ", claimStructName='" + claimStructName + '\'' +
                ", claimTypeTitle='" + claimTypeTitle + '\'' +
                ", claimTypeTitleShort='" + claimTypeTitleShort + '\'' +
                '}';
    }
}
