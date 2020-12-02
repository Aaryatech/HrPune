package com.ats.hrpune.model;

public class Login {

    private int empId ;
    private String empCode ;
    private String firstName ;
    private String middleName;
    private String surname;
    private String motherName;
    private String emailId;
    private int userId;
    private String locationIds;
    private boolean isError;

    private String userPwd;
    private int designType;
    private String hodDeptIds;

    private int isVisit;
    private String empPhoto;
    private int accessRoleId;

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public boolean getIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public String getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(String locationIds) {
        this.locationIds = locationIds;
    }

    public int getDesignType() {
        return designType;
    }

    public void setDesignType(int designType) {
        this.designType = designType;
    }

    public String getHodDeptIds() {
        return hodDeptIds;
    }

    public void setHodDeptIds(String hodDeptIds) {
        this.hodDeptIds = hodDeptIds;
    }

    public boolean isError() {
        return isError;
    }

    public int getIsVisit() {
        return isVisit;
    }

    public void setIsVisit(int isVisit) {
        this.isVisit = isVisit;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public int getAccessRoleId() {
        return accessRoleId;
    }

    public void setAccessRoleId(int accessRoleId) {
        this.accessRoleId = accessRoleId;
    }

    @Override
    public String toString() {
        return "Login{" +
                "empId=" + empId +
                ", empCode='" + empCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", surname='" + surname + '\'' +
                ", motherName='" + motherName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", userId=" + userId +
                ", locationIds='" + locationIds + '\'' +
                ", isError=" + isError +
                ", userPwd='" + userPwd + '\'' +
                ", designType=" + designType +
                ", hodDeptIds='" + hodDeptIds + '\'' +
                ", isVisit=" + isVisit +
                ", empPhoto='" + empPhoto + '\'' +
                ", accessRoleId=" + accessRoleId +
                '}';
    }
}
