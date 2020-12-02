package com.ats.hrpune.inerfaces;


import com.ats.hrpune.model.ApplyLeaveDetail;
import com.ats.hrpune.model.AuthorityIds;
import com.ats.hrpune.model.BalanceLeaveModel;
import com.ats.hrpune.model.ClaimApp;
import com.ats.hrpune.model.ClaimApply;
import com.ats.hrpune.model.ClaimApplyHeader;
import com.ats.hrpune.model.ClaimDetail;
import com.ats.hrpune.model.ClaimHeader;
import com.ats.hrpune.model.ClaimHistory;
import com.ats.hrpune.model.ClaimHistoryModel;
import com.ats.hrpune.model.ClaimPayroll;
import com.ats.hrpune.model.ClaimProof;
import com.ats.hrpune.model.ClaimProofList;
import com.ats.hrpune.model.ClaimTrailstatus;
import com.ats.hrpune.model.ClaimType;
import com.ats.hrpune.model.CompOffLeave;
import com.ats.hrpune.model.CurrentYearModel;
import com.ats.hrpune.model.DashboardCount;
import com.ats.hrpune.model.FinalApprove;
import com.ats.hrpune.model.ForgetPass;
import com.ats.hrpune.model.Info;
import com.ats.hrpune.model.InitalApprove;
import com.ats.hrpune.model.LeaveApp;
import com.ats.hrpune.model.LeaveApply;
import com.ats.hrpune.model.LeaveEmployeeModel;
import com.ats.hrpune.model.LeaveHistory;
import com.ats.hrpune.model.LeaveInfo;
import com.ats.hrpune.model.LeaveWeeklyOffCount;
import com.ats.hrpune.model.Login;
import com.ats.hrpune.model.MyLeaveData;
import com.ats.hrpune.model.MyLeaveTrailData;
import com.ats.hrpune.model.ProjectList;
import com.ats.hrpune.model.SaveClaimTrail;
import com.ats.hrpune.model.SaveLeaveTrail;
import com.ats.hrpune.model.StructureData;
import com.ats.hrpune.model.TypeClaim;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface InterfaceApi {

    @POST("login")
    Call<Login> doLogin(@Header("Authorization") String authHeader, @Query("username") String username, @Query("userPass") String userPass);

//    @Header("Authorization") String authHeader,

    @POST("getDashboardCount")
    Call<DashboardCount> getDashboardCount(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getLeaveApplyListForPending")
    Call<ArrayList<LeaveApp>> getLeaveApplyListForPending(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("currYrId") int currYrId);

    @POST("getLeaveApplyListForInformation")
    Call<ArrayList<LeaveApp>> getLeaveApplyListForInfo(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("currYrId") int currYrId);

    @POST("getLeaveStatusList")
    Call<ArrayList<MyLeaveData>> getLeaveStatusList(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("status") ArrayList<Integer> status);

    @POST("getLeaveListByEmpId")
    Call<ArrayList<LeaveHistory>> getLeaveListByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);

    //ClaimHistoryModel
    @POST("getClaimStatusList")
    Call<ArrayList<ClaimApp>> getClaimStatusList(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("status") ArrayList<Integer> status);

    @POST("getClaimHeadListByEmpId")
    Call<ArrayList<ClaimHistory>> getClaimHeadListByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getEmpListForClaimAuthByEmpId")
    Call<ArrayList<LeaveEmployeeModel>> getEmpListForClaimAuthByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);

//    @POST("getEmployeeListByEmpId")
//    Call<ArrayList<LeaveEmployeeModel>> getEmployeeListByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getAuthorityWiseEmpListByEmpId")
    Call<ArrayList<LeaveEmployeeModel>> getEmployeeListByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);


    @POST("getLeaveHistoryList")
    Call<ArrayList<BalanceLeaveModel>> getBalanceLeave(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("currYrId") int currYrId);

    @GET("getCalculateYearListIsCurrent")
    Call<CurrentYearModel> getCurrentYear(@Header("Authorization") String authHeader);

    @GET("getClaimList")
    Call<ArrayList<ClaimType>> getClaimList(@Header("Authorization") String authHeader);

    @POST("getEmpClaimStructure")
    Call<ArrayList<TypeClaim>> getEmpClaimStructure(@Header("Authorization") String authHeader,@Query("empId") int empId);

    @POST("getProjectsListByCompanyId")
    Call<ArrayList<ProjectList>> getProjectsListByCompanyId(@Header("Authorization") String authHeader, @Query("companyId") int companyId);

    @POST("getLeaveTrailList")
    Call<ArrayList<MyLeaveTrailData>> getLeaveTrail(@Header("Authorization") String authHeader, @Query("leaveId") int leaveId);

    @POST("getAuthIdByEmpId")
    Call<AuthorityIds> getAuthIdByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getClaimAuthIds")
    Call<AuthorityIds> getClaimAuthIds(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("companyId") int companyId);


    @POST("updateLeaveStatus")
    Call<Info> updateLeaveStatus(@Header("Authorization") String authHeader, @Query("leaveId") int leaveId, @Query("status") int status);

    @POST("saveLeaveTrail")
    Call<SaveLeaveTrail> saveLeaveTrail(@Header("Authorization") String authHeader, @Body SaveLeaveTrail saveLeaveTrail);

    @POST("updateTrailId")
    Call<Info> updateLeaveTrailId(@Header("Authorization") String authHeader, @Query("leaveId") int leaveId, @Query("trailId") int trailId);

    @POST("updateClaimTrailId")
    Call<Info> updateClaimTrailId(@Header("Authorization") String authHeader, @Query("claimId") int claimId, @Query("trailId") int trailId);


    @POST("saveLeaveApply")
    Call<LeaveApply> saveLeaveApply(@Header("Authorization") String authHeader, @Body LeaveApply leaveApply);

//    @POST("saveClaimApply")
//    Call<ClaimApply> saveClaimApply(@Header("Authorization") String authHeader, @Body ClaimApply claimApply);

    @POST("saveClaimHeaderAndDetail")
    Call<ClaimApplyHeader> saveClaimHeaderAndDetail(@Header("Authorization") String authHeader, @Body ClaimApplyHeader claimApplyHeader);


    @POST("saveClaimTrail")
    Call<SaveClaimTrail> saveClaimTrail(@Header("Authorization") String authHeader, @Body SaveClaimTrail saveClaimTrail);

    @POST("updateClaimStatus")
    Call<Info> updateClaimStatus(@Header("Authorization") String authHeader, @Query("claimId") int claimId, @Query("status") int status,@Query("month") int month,@Query("year") int year);

    @POST("getClaimApplyListForPending")
    Call<ArrayList<ClaimApp>> getClaimApplyListForPending(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getClaimApplyListForInformation")
    Call<ArrayList<ClaimApp>> getClaimApplyListForInfo(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getClaimTrailList")
    Call<ArrayList<ClaimTrailstatus>> getClaimTrail(@Header("Authorization") String authHeader, @Query("claimId") int claimId);

    @POST("getEmpClaimInfoListByTrailEmpId")
    Call<ArrayList<ClaimHeader>> getEmpClaimInfoListByTrailEmpId(@Header("Authorization") String authHeader, @Query("claimId") int claimId);

    @POST("getClaimDetailListByEmpId")
    Call<ArrayList<ClaimDetail>> getClaimDetailListByEmpId(@Header("Authorization") String authHeader, @Query("claimId") int claimId);

    @POST("getClaimStructureDetailByEmpId")
    Call<ArrayList<StructureData>> getClaimStructureDetailByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getClaimProofByClaimId")
    Call<ArrayList<ClaimProof>> getClaimProofByClaimId(@Header("Authorization") String authHeader, @Query("claimId") int claimId);


    @Multipart
    @POST("photoUpload")
    Call<JSONObject> imageUpload(@Header("Authorization") String authHeader, @Part MultipartBody.Part[] filePath, @Part("imageName") ArrayList<String> name, @Part("type") RequestBody type);

    @POST("saveClaimProof")
    Call<ArrayList<ClaimProof>> saveClaimProof(@Header("Authorization") String authHeader, @Body ArrayList<ClaimProof> claimProof);

    @POST("getClaimProofByClaimId")
    Call<ArrayList<ClaimProofList>> getClaimProofList(@Header("Authorization") String authHeader, @Query("claimId") int claimId);

    @POST("updateToken")
    Call<Info> updateUserToken(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("token") String token);

    @POST("calculateHolidayBetweenDate")
    Call<LeaveWeeklyOffCount> getLeaveCountByEmp(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

    @POST("updateIsVistStatus")
    Call<Info> updatePassword(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("password") String password);

    @Multipart
    @POST("updateEmpProfPicForApp")
    Call<Info> profileImageUpload(@Header("Authorization") String authHeader, @Query("empId") int empId, @Part MultipartBody.Part profilePic);

//    @POST("checkUserName")
//    Call<ForgetPass> forgetPassword(@Header("Authorization") String authHeader, @Query("inputValue") String inputValue);

    @POST("checkUserName")
    Call<Info> forgetPassword(@Header("Authorization") String authHeader, @Query("inputValue") String inputValue);

    @POST("getDailyDailyRecordForOtApproval")
    Call<ArrayList<InitalApprove>> getDailyDailyRecordForOtApproval(@Header("Authorization") String authHeader, @Query("date") String date,@Query("empId") int empId);

    @POST("updateOtApproveStatus")
    Call<Info> updateOtApproveStatus(@Header("Authorization") String authHeader, @Query("ids") ArrayList<Integer> date,@Query("status") int empId);

    @POST("getDailyDailyRecordForFinalOtApproval")
    Call<ArrayList<InitalApprove>> getDailyDailyRecordForFinalOtApproval(@Header("Authorization") String authHeader, @Query("date") String date,@Query("empId") int empId);

    @POST("updateAttendaceFinalRecordByempId")
    Call<Info> updateAttendaceFinalRecordByempId(@Header("Authorization") String authHeader, @Query("fromDate") String fromDate,@Query("toDate") String toDate,@Query("userId") int userId,@Query("year") int year,@Query("month") int month,@Query("empIds") ArrayList<Integer> empIds);

    @POST("checkPayslipisgenerated")
    Call<Info> checkPayslipisgenerated(@Header("Authorization") String authHeader, @Query("month") int month, @Query("year") int year,@Query("empId") int empId);


    @POST("changeInDailyDailyAfterLeaveTransactionByApp")
    Call<Info> changeInDailyDailyAfterLeaveTransactionByApp(@Header("Authorization") String authHeader, @Query("fromDate") String fromDate, @Query("toDate") String toDate, @Query("empId") int empId , @Query("userId") int userId);


    @POST("checkDateForRepetedLeaveValidation")
    Call<LeaveInfo> checkDateForRepetedLeaveValidation(@Header("Authorization") String authHeader, @Query("fromDate") String fromDate, @Query("toDate") String toDate, @Query("empId") int empId , @Query("leaveTypeId") int leaveTypeId,@Query("shortName") String shortName,@Query("noOfDays") int noOfDays);

    @POST("getEmpTypeByempId")
    Call<CompOffLeave> getEmpTypeByempId(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("updateweeklyoffotStatutoused")
    Call<Info> updateweeklyoffotStatutoused(@Header("Authorization") String authHeader, @Query("dailydaillyIds") String dailydaillyIds ,@Query("status") int status);

    @POST("getLeaveApplyDetailsByLeaveId")
    Call<ApplyLeaveDetail> getLeaveApplyDetailsByLeaveId(@Header("Authorization") String authHeader, @Query("leaveId") int leaveId);

    @POST("getSettingByKey")
    Call<ClaimPayroll> getSettingByKey(@Header("Authorization") String authHeader, @Query("limitKey") String limitKey);

}
