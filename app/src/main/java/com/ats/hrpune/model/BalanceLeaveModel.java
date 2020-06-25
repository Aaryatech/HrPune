package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceLeaveModel {

    private int lvTypeId;

    private int lvsId;

    private String lvTitleShort;

    private String lvTitle;

    private float lvsAllotedLeaves;

    private float balLeave;

    private float aplliedLeaeve;

    private float sactionLeave;

    private int isFile;

    private int maxAccumulateCarryforward;

    private int isCarryforward;

    private int maxCarryforward;

    private String lvsName;

    private int isInCash;

    public BalanceLeaveModel(int lvTypeId, int lvsId, String lvTitleShort, String lvTitle, float lvsAllotedLeaves, float balLeave, float aplliedLeaeve, float sactionLeave, int isFile, int maxAccumulateCarryforward, int isCarryforward, int maxCarryforward, String lvsName, int isInCash) {
        this.lvTypeId = lvTypeId;
        this.lvsId = lvsId;
        this.lvTitleShort = lvTitleShort;
        this.lvTitle = lvTitle;
        this.lvsAllotedLeaves = lvsAllotedLeaves;
        this.balLeave = balLeave;
        this.aplliedLeaeve = aplliedLeaeve;
        this.sactionLeave = sactionLeave;
        this.isFile = isFile;
        this.maxAccumulateCarryforward = maxAccumulateCarryforward;
        this.isCarryforward = isCarryforward;
        this.maxCarryforward = maxCarryforward;
        this.lvsName = lvsName;
        this.isInCash = isInCash;
    }

    public int getLvTypeId() {
        return lvTypeId;
    }

    public void setLvTypeId(int lvTypeId) {
        this.lvTypeId = lvTypeId;
    }

    public int getLvsId() {
        return lvsId;
    }

    public void setLvsId(int lvsId) {
        this.lvsId = lvsId;
    }

    public String getLvTitleShort() {
        return lvTitleShort;
    }

    public void setLvTitleShort(String lvTitleShort) {
        this.lvTitleShort = lvTitleShort;
    }

    public String getLvTitle() {
        return lvTitle;
    }

    public void setLvTitle(String lvTitle) {
        this.lvTitle = lvTitle;
    }

    public float getLvsAllotedLeaves() {
        return lvsAllotedLeaves;
    }

    public void setLvsAllotedLeaves(float lvsAllotedLeaves) {
        this.lvsAllotedLeaves = lvsAllotedLeaves;
    }

    public float getBalLeave() {
        return balLeave;
    }

    public void setBalLeave(float balLeave) {
        this.balLeave = balLeave;
    }

    public float getAplliedLeaeve() {
        return aplliedLeaeve;
    }

    public void setAplliedLeaeve(float aplliedLeaeve) {
        this.aplliedLeaeve = aplliedLeaeve;
    }

    public float getSactionLeave() {
        return sactionLeave;
    }

    public void setSactionLeave(float sactionLeave) {
        this.sactionLeave = sactionLeave;
    }

    public int getIsFile() {
        return isFile;
    }

    public void setIsFile(int isFile) {
        this.isFile = isFile;
    }

    public int getMaxAccumulateCarryforward() {
        return maxAccumulateCarryforward;
    }

    public void setMaxAccumulateCarryforward(int maxAccumulateCarryforward) {
        this.maxAccumulateCarryforward = maxAccumulateCarryforward;
    }

    public int getIsCarryforward() {
        return isCarryforward;
    }

    public void setIsCarryforward(int isCarryforward) {
        this.isCarryforward = isCarryforward;
    }

    public int getMaxCarryforward() {
        return maxCarryforward;
    }

    public void setMaxCarryforward(int maxCarryforward) {
        this.maxCarryforward = maxCarryforward;
    }

    public String getLvsName() {
        return lvsName;
    }

    public void setLvsName(String lvsName) {
        this.lvsName = lvsName;
    }

    public int getIsInCash() {
        return isInCash;
    }

    public void setIsInCash(int isInCash) {
        this.isInCash = isInCash;
    }

    @Override
    public String toString() {
        return "BalanceLeaveModel{" +
                "lvTypeId=" + lvTypeId +
                ", lvsId=" + lvsId +
                ", lvTitleShort='" + lvTitleShort + '\'' +
                ", lvTitle='" + lvTitle + '\'' +
                ", lvsAllotedLeaves=" + lvsAllotedLeaves +
                ", balLeave=" + balLeave +
                ", aplliedLeaeve=" + aplliedLeaeve +
                ", sactionLeave=" + sactionLeave +
                ", isFile=" + isFile +
                ", maxAccumulateCarryforward=" + maxAccumulateCarryforward +
                ", isCarryforward=" + isCarryforward +
                ", maxCarryforward=" + maxCarryforward +
                ", lvsName='" + lvsName + '\'' +
                ", isInCash=" + isInCash +
                '}';
    }
}
