package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClaimPayroll {

    @SerializedName("settingId")
    @Expose
    private Integer settingId;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("serialized")
    @Expose
    private Integer serialized;
    @SerializedName("editable")
    @Expose
    private Integer editable;
    @SerializedName("labels")
    @Expose
    private Object labels;
    @SerializedName("defaultValue")
    @Expose
    private Object defaultValue;
    @SerializedName("explaination")
    @Expose
    private Object explaination;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exVar1")
    @Expose
    private Object exVar1;

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSerialized() {
        return serialized;
    }

    public void setSerialized(Integer serialized) {
        this.serialized = serialized;
    }

    public Integer getEditable() {
        return editable;
    }

    public void setEditable(Integer editable) {
        this.editable = editable;
    }

    public Object getLabels() {
        return labels;
    }

    public void setLabels(Object labels) {
        this.labels = labels;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getExplaination() {
        return explaination;
    }

    public void setExplaination(Object explaination) {
        this.explaination = explaination;
    }

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public Object getExVar1() {
        return exVar1;
    }

    public void setExVar1(Object exVar1) {
        this.exVar1 = exVar1;
    }

    @Override
    public String toString() {
        return "ClaimPayroll{" +
                "settingId=" + settingId +
                ", group='" + group + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", serialized=" + serialized +
                ", editable=" + editable +
                ", labels=" + labels +
                ", defaultValue=" + defaultValue +
                ", explaination=" + explaination +
                ", exInt1=" + exInt1 +
                ", exVar1=" + exVar1 +
                '}';
    }
}
