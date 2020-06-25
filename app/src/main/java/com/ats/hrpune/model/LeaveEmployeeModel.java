package com.ats.hrpune.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveEmployeeModel {

    @SerializedName("empId")
    @Expose
    private Integer empId;
    @SerializedName("empCode")
    @Expose
    private String empCode;
    @SerializedName("cmpCode")
    @Expose
    private String cmpCode;
    @SerializedName("empType")
    @Expose
    private Integer empType;
    @SerializedName("mobileNo1")
    @Expose
    private String mobileNo1;
    @SerializedName("mobileNo2")
    @Expose
    private String mobileNo2;
    @SerializedName("residenceLandNo")
    @Expose
    private String residenceLandNo;
    @SerializedName("contractorId")
    @Expose
    private Integer contractorId;
    @SerializedName("departId")
    @Expose
    private Integer departId;
    @SerializedName("designationId")
    @Expose
    private Integer designationId;
    @SerializedName("locationId")
    @Expose
    private Integer locationId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("motherName")
    @Expose
    private String motherName;
    @SerializedName("societySerialNo")
    @Expose
    private String societySerialNo;
    @SerializedName("panCardNo")
    @Expose
    private String panCardNo;
    @SerializedName("pfNo")
    @Expose
    private String pfNo;
    @SerializedName("esicNo")
    @Expose
    private String esicNo;
    @SerializedName("aadharNo")
    @Expose
    private String aadharNo;
    @SerializedName("uan")
    @Expose
    private String uan;
    @SerializedName("leavingReason")
    @Expose
    private String leavingReason;
    @SerializedName("isEmp")
    @Expose
    private Integer isEmp;
    @SerializedName("currentShiftid")
    @Expose
    private Integer currentShiftid;
    @SerializedName("nextShiftid")
    @Expose
    private Integer nextShiftid;
    @SerializedName("grossSalaryEst")
    @Expose
    private Integer grossSalaryEst;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("noticePayAmount")
    @Expose
    private Integer noticePayAmount;
    @SerializedName("salDedAtFullandfinal")
    @Expose
    private Integer salDedAtFullandfinal;
    @SerializedName("addedFrom")
    @Expose
    private Integer addedFrom;
    @SerializedName("rawData")
    @Expose
    private String rawData;
    @SerializedName("addedBySupervisorId")
    @Expose
    private Integer addedBySupervisorId;
    @SerializedName("loginName")
    @Expose
    private String loginName;
    @SerializedName("loginTime")
    @Expose
    private String loginTime;
    @SerializedName("plCalcBase")
    @Expose
    private Integer plCalcBase;
    @SerializedName("earnLeaveOpeningBalance")
    @Expose
    private Integer earnLeaveOpeningBalance;
    @SerializedName("empCategory")
    @Expose
    private String empCategory;
    @SerializedName("exgratiaPerc")
    @Expose
    private float exgratiaPerc;
    @SerializedName("newBasicRate")
    @Expose
    private Integer newBasicRate;
    @SerializedName("newHraRate")
    @Expose
    private Integer newHraRate;
    @SerializedName("newDaRate")
    @Expose
    private Integer newDaRate;
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
    @SerializedName("deptName")
    @Expose
    private String deptName;
    @SerializedName("empDesgn")
    @Expose
    private String empDesgn;
    @SerializedName("locName")
    @Expose
    private String locName;
    @SerializedName("orgName")
    @Expose
    private String orgName;
    @SerializedName("grossSalary")
    @Expose
    private Integer grossSalary;
    @SerializedName("shiftname")
    @Expose
    private String shiftname;
    @SerializedName("empTypeName")
    @Expose
    private String empTypeName;
    @SerializedName("salTypeName")
    @Expose
    private String salTypeName;
    @SerializedName("subCompName")
    @Expose
    private String subCompName;
    @SerializedName("hoCatName")
    @Expose
    private String hoCatName;
    @SerializedName("woCatName")
    @Expose
    private String woCatName;

    public LeaveEmployeeModel(Integer empId, String empCode, String cmpCode, Integer empType, String mobileNo1, String mobileNo2, String residenceLandNo, Integer contractorId, Integer departId, Integer designationId, Integer locationId, String firstName, String middleName, String surname, String motherName, String societySerialNo, String panCardNo, String pfNo, String esicNo, String aadharNo, String uan, String leavingReason, Integer isEmp, Integer currentShiftid, Integer nextShiftid, Integer grossSalaryEst, String emailId, Integer noticePayAmount, Integer salDedAtFullandfinal, Integer addedFrom, String rawData, Integer addedBySupervisorId, String loginName, String loginTime, Integer plCalcBase, Integer earnLeaveOpeningBalance, String empCategory, Integer exgratiaPerc, Integer newBasicRate, Integer newHraRate, Integer newDaRate, Integer delStatus, Integer exInt1, Integer exInt2, String exVar1, String exVar2, String deptName, String empDesgn, String locName, String orgName, Integer grossSalary, String shiftname, String empTypeName, String salTypeName, String subCompName, String hoCatName, String woCatName) {
        this.empId = empId;
        this.empCode = empCode;
        this.cmpCode = cmpCode;
        this.empType = empType;
        this.mobileNo1 = mobileNo1;
        this.mobileNo2 = mobileNo2;
        this.residenceLandNo = residenceLandNo;
        this.contractorId = contractorId;
        this.departId = departId;
        this.designationId = designationId;
        this.locationId = locationId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.surname = surname;
        this.motherName = motherName;
        this.societySerialNo = societySerialNo;
        this.panCardNo = panCardNo;
        this.pfNo = pfNo;
        this.esicNo = esicNo;
        this.aadharNo = aadharNo;
        this.uan = uan;
        this.leavingReason = leavingReason;
        this.isEmp = isEmp;
        this.currentShiftid = currentShiftid;
        this.nextShiftid = nextShiftid;
        this.grossSalaryEst = grossSalaryEst;
        this.emailId = emailId;
        this.noticePayAmount = noticePayAmount;
        this.salDedAtFullandfinal = salDedAtFullandfinal;
        this.addedFrom = addedFrom;
        this.rawData = rawData;
        this.addedBySupervisorId = addedBySupervisorId;
        this.loginName = loginName;
        this.loginTime = loginTime;
        this.plCalcBase = plCalcBase;
        this.earnLeaveOpeningBalance = earnLeaveOpeningBalance;
        this.empCategory = empCategory;
        this.exgratiaPerc = exgratiaPerc;
        this.newBasicRate = newBasicRate;
        this.newHraRate = newHraRate;
        this.newDaRate = newDaRate;
        this.delStatus = delStatus;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.deptName = deptName;
        this.empDesgn = empDesgn;
        this.locName = locName;
        this.orgName = orgName;
        this.grossSalary = grossSalary;
        this.shiftname = shiftname;
        this.empTypeName = empTypeName;
        this.salTypeName = salTypeName;
        this.subCompName = subCompName;
        this.hoCatName = hoCatName;
        this.woCatName = woCatName;
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

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public Integer getEmpType() {
        return empType;
    }

    public void setEmpType(Integer empType) {
        this.empType = empType;
    }

    public String getMobileNo1() {
        return mobileNo1;
    }

    public void setMobileNo1(String mobileNo1) {
        this.mobileNo1 = mobileNo1;
    }

    public String getMobileNo2() {
        return mobileNo2;
    }

    public void setMobileNo2(String mobileNo2) {
        this.mobileNo2 = mobileNo2;
    }

    public String getResidenceLandNo() {
        return residenceLandNo;
    }

    public void setResidenceLandNo(String residenceLandNo) {
        this.residenceLandNo = residenceLandNo;
    }

    public Integer getContractorId() {
        return contractorId;
    }

    public void setContractorId(Integer contractorId) {
        this.contractorId = contractorId;
    }

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    public Integer getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Integer designationId) {
        this.designationId = designationId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getSocietySerialNo() {
        return societySerialNo;
    }

    public void setSocietySerialNo(String societySerialNo) {
        this.societySerialNo = societySerialNo;
    }

    public String getPanCardNo() {
        return panCardNo;
    }

    public void setPanCardNo(String panCardNo) {
        this.panCardNo = panCardNo;
    }

    public String getPfNo() {
        return pfNo;
    }

    public void setPfNo(String pfNo) {
        this.pfNo = pfNo;
    }

    public String getEsicNo() {
        return esicNo;
    }

    public void setEsicNo(String esicNo) {
        this.esicNo = esicNo;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getUan() {
        return uan;
    }

    public void setUan(String uan) {
        this.uan = uan;
    }

    public String getLeavingReason() {
        return leavingReason;
    }

    public void setLeavingReason(String leavingReason) {
        this.leavingReason = leavingReason;
    }

    public Integer getIsEmp() {
        return isEmp;
    }

    public void setIsEmp(Integer isEmp) {
        this.isEmp = isEmp;
    }

    public Integer getCurrentShiftid() {
        return currentShiftid;
    }

    public void setCurrentShiftid(Integer currentShiftid) {
        this.currentShiftid = currentShiftid;
    }

    public Integer getNextShiftid() {
        return nextShiftid;
    }

    public void setNextShiftid(Integer nextShiftid) {
        this.nextShiftid = nextShiftid;
    }

    public Integer getGrossSalaryEst() {
        return grossSalaryEst;
    }

    public void setGrossSalaryEst(Integer grossSalaryEst) {
        this.grossSalaryEst = grossSalaryEst;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getNoticePayAmount() {
        return noticePayAmount;
    }

    public void setNoticePayAmount(Integer noticePayAmount) {
        this.noticePayAmount = noticePayAmount;
    }

    public Integer getSalDedAtFullandfinal() {
        return salDedAtFullandfinal;
    }

    public void setSalDedAtFullandfinal(Integer salDedAtFullandfinal) {
        this.salDedAtFullandfinal = salDedAtFullandfinal;
    }

    public Integer getAddedFrom() {
        return addedFrom;
    }

    public void setAddedFrom(Integer addedFrom) {
        this.addedFrom = addedFrom;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public Integer getAddedBySupervisorId() {
        return addedBySupervisorId;
    }

    public void setAddedBySupervisorId(Integer addedBySupervisorId) {
        this.addedBySupervisorId = addedBySupervisorId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getPlCalcBase() {
        return plCalcBase;
    }

    public void setPlCalcBase(Integer plCalcBase) {
        this.plCalcBase = plCalcBase;
    }

    public Integer getEarnLeaveOpeningBalance() {
        return earnLeaveOpeningBalance;
    }

    public void setEarnLeaveOpeningBalance(Integer earnLeaveOpeningBalance) {
        this.earnLeaveOpeningBalance = earnLeaveOpeningBalance;
    }

    public String getEmpCategory() {
        return empCategory;
    }

    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
    }

    public float getExgratiaPerc() {
        return exgratiaPerc;
    }

    public void setExgratiaPerc(float exgratiaPerc) {
        this.exgratiaPerc = exgratiaPerc;
    }

    public Integer getNewBasicRate() {
        return newBasicRate;
    }

    public void setNewBasicRate(Integer newBasicRate) {
        this.newBasicRate = newBasicRate;
    }

    public Integer getNewHraRate() {
        return newHraRate;
    }

    public void setNewHraRate(Integer newHraRate) {
        this.newHraRate = newHraRate;
    }

    public Integer getNewDaRate() {
        return newDaRate;
    }

    public void setNewDaRate(Integer newDaRate) {
        this.newDaRate = newDaRate;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEmpDesgn() {
        return empDesgn;
    }

    public void setEmpDesgn(String empDesgn) {
        this.empDesgn = empDesgn;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Integer grossSalary) {
        this.grossSalary = grossSalary;
    }

    public String getShiftname() {
        return shiftname;
    }

    public void setShiftname(String shiftname) {
        this.shiftname = shiftname;
    }

    public String getEmpTypeName() {
        return empTypeName;
    }

    public void setEmpTypeName(String empTypeName) {
        this.empTypeName = empTypeName;
    }

    public String getSalTypeName() {
        return salTypeName;
    }

    public void setSalTypeName(String salTypeName) {
        this.salTypeName = salTypeName;
    }

    public String getSubCompName() {
        return subCompName;
    }

    public void setSubCompName(String subCompName) {
        this.subCompName = subCompName;
    }

    public String getHoCatName() {
        return hoCatName;
    }

    public void setHoCatName(String hoCatName) {
        this.hoCatName = hoCatName;
    }

    public String getWoCatName() {
        return woCatName;
    }

    public void setWoCatName(String woCatName) {
        this.woCatName = woCatName;
    }

    @Override
    public String toString() {
        return "LeaveEmployeeModel{" +
                "empId=" + empId +
                ", empCode='" + empCode + '\'' +
                ", cmpCode='" + cmpCode + '\'' +
                ", empType=" + empType +
                ", mobileNo1='" + mobileNo1 + '\'' +
                ", mobileNo2='" + mobileNo2 + '\'' +
                ", residenceLandNo='" + residenceLandNo + '\'' +
                ", contractorId=" + contractorId +
                ", departId=" + departId +
                ", designationId=" + designationId +
                ", locationId=" + locationId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", surname='" + surname + '\'' +
                ", motherName='" + motherName + '\'' +
                ", societySerialNo='" + societySerialNo + '\'' +
                ", panCardNo='" + panCardNo + '\'' +
                ", pfNo='" + pfNo + '\'' +
                ", esicNo='" + esicNo + '\'' +
                ", aadharNo='" + aadharNo + '\'' +
                ", uan='" + uan + '\'' +
                ", leavingReason='" + leavingReason + '\'' +
                ", isEmp=" + isEmp +
                ", currentShiftid=" + currentShiftid +
                ", nextShiftid=" + nextShiftid +
                ", grossSalaryEst=" + grossSalaryEst +
                ", emailId='" + emailId + '\'' +
                ", noticePayAmount=" + noticePayAmount +
                ", salDedAtFullandfinal=" + salDedAtFullandfinal +
                ", addedFrom=" + addedFrom +
                ", rawData='" + rawData + '\'' +
                ", addedBySupervisorId=" + addedBySupervisorId +
                ", loginName='" + loginName + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", plCalcBase=" + plCalcBase +
                ", earnLeaveOpeningBalance=" + earnLeaveOpeningBalance +
                ", empCategory='" + empCategory + '\'' +
                ", exgratiaPerc=" + exgratiaPerc +
                ", newBasicRate=" + newBasicRate +
                ", newHraRate=" + newHraRate +
                ", newDaRate=" + newDaRate +
                ", delStatus=" + delStatus +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", deptName='" + deptName + '\'' +
                ", empDesgn='" + empDesgn + '\'' +
                ", locName='" + locName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", grossSalary=" + grossSalary +
                ", shiftname='" + shiftname + '\'' +
                ", empTypeName='" + empTypeName + '\'' +
                ", salTypeName='" + salTypeName + '\'' +
                ", subCompName='" + subCompName + '\'' +
                ", hoCatName='" + hoCatName + '\'' +
                ", woCatName='" + woCatName + '\'' +
                '}';
    }
}
