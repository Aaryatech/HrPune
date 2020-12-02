package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InitalApprove {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("empCode")
    @Expose
    private String empCode;
    @SerializedName("empName")
    @Expose
    private String empName;
    @SerializedName("attDate")
    @Expose
    private String attDate;
    @SerializedName("attStatus")
    @Expose
    private String attStatus;
    @SerializedName("lvSumupId")
    @Expose
    private Integer lvSumupId;
    @SerializedName("workingHrs")
    @Expose
    private String workingHrs;
    @SerializedName("inTime")
    @Expose
    private String inTime;
    @SerializedName("recStatus")
    @Expose
    private String recStatus;
    @SerializedName("loginName")
    @Expose
    private String loginName;
    @SerializedName("loginTime")
    @Expose
    private Object loginTime;
    @SerializedName("importDate")
    @Expose
    private Object importDate;
    @SerializedName("empId")
    @Expose
    private Integer empId;
    @SerializedName("otHr")
    @Expose
    private String otHr;
    @SerializedName("currentShiftid")
    @Expose
    private Integer currentShiftid;
    @SerializedName("lateMark")
    @Expose
    private String lateMark;
    @SerializedName("lateMin")
    @Expose
    private Integer lateMin;
    @SerializedName("reason")
    @Expose
    private Object reason;
    @SerializedName("currentShiftname")
    @Expose
    private String currentShiftname;
    @SerializedName("freezeBySupervisor")
    @Expose
    private Integer freezeBySupervisor;
    @SerializedName("commentsSupervisor")
    @Expose
    private Object commentsSupervisor;
    @SerializedName("getPassUsedCount")
    @Expose
    private Integer getPassUsedCount;
    @SerializedName("getPassUsedHour")
    @Expose
    private Integer getPassUsedHour;
    @SerializedName("getPassUsedHourReason")
    @Expose
    private Object getPassUsedHourReason;
    @SerializedName("rawDataInout")
    @Expose
    private Object rawDataInout;
    @SerializedName("manualOtHr")
    @Expose
    private Integer manualOtHr;
    @SerializedName("fullNight")
    @Expose
    private Integer fullNight;
    @SerializedName("halfNight")
    @Expose
    private Integer halfNight;
    @SerializedName("outTime")
    @Expose
    private String outTime;
    @SerializedName("earlyGoingMark")
    @Expose
    private Integer earlyGoingMark;
    @SerializedName("earlyGoingMin")
    @Expose
    private Integer earlyGoingMin;
    @SerializedName("multipleEntries")
    @Expose
    private String multipleEntries;
    @SerializedName("casetype")
    @Expose
    private String casetype;
    @SerializedName("isFixed")
    @Expose
    private Integer isFixed;
    @SerializedName("byFileUpdated")
    @Expose
    private Integer byFileUpdated;
    @SerializedName("locationId")
    @Expose
    private Integer locationId;
    @SerializedName("empType")
    @Expose
    private Integer empType;
    @SerializedName("empJson")
    @Expose
    private String empJson;
    @SerializedName("atsummUid")
    @Expose
    private String atsummUid;
    @SerializedName("fileName")
    @Expose
    private Object fileName;
    @SerializedName("rowId")
    @Expose
    private Integer rowId;

    private Boolean isChecked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public String getAttDate() {
        return attDate;
    }

    public void setAttDate(String attDate) {
        this.attDate = attDate;
    }

    public String getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(String attStatus) {
        this.attStatus = attStatus;
    }

    public Integer getLvSumupId() {
        return lvSumupId;
    }

    public void setLvSumupId(Integer lvSumupId) {
        this.lvSumupId = lvSumupId;
    }

    public String getWorkingHrs() {
        return workingHrs;
    }

    public void setWorkingHrs(String workingHrs) {
        this.workingHrs = workingHrs;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Object getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Object loginTime) {
        this.loginTime = loginTime;
    }

    public Object getImportDate() {
        return importDate;
    }

    public void setImportDate(Object importDate) {
        this.importDate = importDate;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getOtHr() {
        return otHr;
    }

    public void setOtHr(String otHr) {
        this.otHr = otHr;
    }

    public Integer getCurrentShiftid() {
        return currentShiftid;
    }

    public void setCurrentShiftid(Integer currentShiftid) {
        this.currentShiftid = currentShiftid;
    }

    public String getLateMark() {
        return lateMark;
    }

    public void setLateMark(String lateMark) {
        this.lateMark = lateMark;
    }

    public Integer getLateMin() {
        return lateMin;
    }

    public void setLateMin(Integer lateMin) {
        this.lateMin = lateMin;
    }

    public Object getReason() {
        return reason;
    }

    public void setReason(Object reason) {
        this.reason = reason;
    }

    public String getCurrentShiftname() {
        return currentShiftname;
    }

    public void setCurrentShiftname(String currentShiftname) {
        this.currentShiftname = currentShiftname;
    }

    public Integer getFreezeBySupervisor() {
        return freezeBySupervisor;
    }

    public void setFreezeBySupervisor(Integer freezeBySupervisor) {
        this.freezeBySupervisor = freezeBySupervisor;
    }

    public Object getCommentsSupervisor() {
        return commentsSupervisor;
    }

    public void setCommentsSupervisor(Object commentsSupervisor) {
        this.commentsSupervisor = commentsSupervisor;
    }

    public Integer getGetPassUsedCount() {
        return getPassUsedCount;
    }

    public void setGetPassUsedCount(Integer getPassUsedCount) {
        this.getPassUsedCount = getPassUsedCount;
    }

    public Integer getGetPassUsedHour() {
        return getPassUsedHour;
    }

    public void setGetPassUsedHour(Integer getPassUsedHour) {
        this.getPassUsedHour = getPassUsedHour;
    }

    public Object getGetPassUsedHourReason() {
        return getPassUsedHourReason;
    }

    public void setGetPassUsedHourReason(Object getPassUsedHourReason) {
        this.getPassUsedHourReason = getPassUsedHourReason;
    }

    public Object getRawDataInout() {
        return rawDataInout;
    }

    public void setRawDataInout(Object rawDataInout) {
        this.rawDataInout = rawDataInout;
    }

    public Integer getManualOtHr() {
        return manualOtHr;
    }

    public void setManualOtHr(Integer manualOtHr) {
        this.manualOtHr = manualOtHr;
    }

    public Integer getFullNight() {
        return fullNight;
    }

    public void setFullNight(Integer fullNight) {
        this.fullNight = fullNight;
    }

    public Integer getHalfNight() {
        return halfNight;
    }

    public void setHalfNight(Integer halfNight) {
        this.halfNight = halfNight;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public Integer getEarlyGoingMark() {
        return earlyGoingMark;
    }

    public void setEarlyGoingMark(Integer earlyGoingMark) {
        this.earlyGoingMark = earlyGoingMark;
    }

    public Integer getEarlyGoingMin() {
        return earlyGoingMin;
    }

    public void setEarlyGoingMin(Integer earlyGoingMin) {
        this.earlyGoingMin = earlyGoingMin;
    }

    public String getMultipleEntries() {
        return multipleEntries;
    }

    public void setMultipleEntries(String multipleEntries) {
        this.multipleEntries = multipleEntries;
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype;
    }

    public Integer getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Integer isFixed) {
        this.isFixed = isFixed;
    }

    public Integer getByFileUpdated() {
        return byFileUpdated;
    }

    public void setByFileUpdated(Integer byFileUpdated) {
        this.byFileUpdated = byFileUpdated;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getEmpType() {
        return empType;
    }

    public void setEmpType(Integer empType) {
        this.empType = empType;
    }

    public String getEmpJson() {
        return empJson;
    }

    public void setEmpJson(String empJson) {
        this.empJson = empJson;
    }

    public String getAtsummUid() {
        return atsummUid;
    }

    public void setAtsummUid(String atsummUid) {
        this.atsummUid = atsummUid;
    }

    public Object getFileName() {
        return fileName;
    }

    public void setFileName(Object fileName) {
        this.fileName = fileName;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "InitalApprove{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", empCode='" + empCode + '\'' +
                ", empName='" + empName + '\'' +
                ", attDate='" + attDate + '\'' +
                ", attStatus='" + attStatus + '\'' +
                ", lvSumupId=" + lvSumupId +
                ", workingHrs='" + workingHrs + '\'' +
                ", inTime='" + inTime + '\'' +
                ", recStatus='" + recStatus + '\'' +
                ", loginName='" + loginName + '\'' +
                ", loginTime=" + loginTime +
                ", importDate=" + importDate +
                ", empId=" + empId +
                ", otHr='" + otHr + '\'' +
                ", currentShiftid=" + currentShiftid +
                ", lateMark='" + lateMark + '\'' +
                ", lateMin=" + lateMin +
                ", reason=" + reason +
                ", currentShiftname='" + currentShiftname + '\'' +
                ", freezeBySupervisor=" + freezeBySupervisor +
                ", commentsSupervisor=" + commentsSupervisor +
                ", getPassUsedCount=" + getPassUsedCount +
                ", getPassUsedHour=" + getPassUsedHour +
                ", getPassUsedHourReason=" + getPassUsedHourReason +
                ", rawDataInout=" + rawDataInout +
                ", manualOtHr=" + manualOtHr +
                ", fullNight=" + fullNight +
                ", halfNight=" + halfNight +
                ", outTime='" + outTime + '\'' +
                ", earlyGoingMark=" + earlyGoingMark +
                ", earlyGoingMin=" + earlyGoingMin +
                ", multipleEntries='" + multipleEntries + '\'' +
                ", casetype='" + casetype + '\'' +
                ", isFixed=" + isFixed +
                ", byFileUpdated=" + byFileUpdated +
                ", locationId=" + locationId +
                ", empType=" + empType +
                ", empJson='" + empJson + '\'' +
                ", atsummUid='" + atsummUid + '\'' +
                ", fileName=" + fileName +
                ", rowId=" + rowId +
                ", isChecked=" + isChecked +
                '}';
    }
}
