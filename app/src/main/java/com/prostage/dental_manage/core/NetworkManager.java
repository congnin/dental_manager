package com.prostage.dental_manage.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.prostage.dental_manage.DentalManageApp;
import com.prostage.dental_manage.R;
import com.prostage.dental_manage.core.model.AdminModel;
import com.prostage.dental_manage.core.model.RegisterModel;
import com.prostage.dental_manage.core.model.ResponseModel;
import com.prostage.dental_manage.core.model.ResultModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManager {
    public static final String TAG = "NetworkManager";
    private Context mContext;
    private IHttpResponse iHttpResponse;
    private ProgressDialog progressBar;

    public NetworkManager(Context context, IHttpResponse iHttpResponse) {
        this.iHttpResponse = iHttpResponse;
        this.mContext = context;
        progressBar = new ProgressDialog(context);
        progressBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void requestApi(final Call<ResponseModel> call, final int idRequest) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (call == null) {
                return;
            }
            progressBar.show();
            progressBar.setContentView(R.layout.dialog_progress_bar);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call1, Response<ResponseModel> response) {
                    if (mContext instanceof Activity) {
                        if (!((Activity) mContext).isFinishing())
                            if (progressBar != null && progressBar.isShowing()) {
                                progressBar.dismiss();
                            }
                    }

                    ResponseModel responseModel = response.body();
                    if (responseModel != null) {
                        if (responseModel.getStatus() == 200) {
                            iHttpResponse.onHttpComplete(responseModel.getMessage().toString(), idRequest);
                        } else {
                            Toast.makeText(mContext, responseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        iHttpResponse.onHttpError(mContext.getString(R.string.unknown_error_network), idRequest, 0);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call1, Throwable t) {
                    if (mContext instanceof Activity) {
                        if (!((Activity) mContext).isFinishing())
                            if ((progressBar != null) && progressBar.isShowing()) {
                                progressBar.dismiss();
                            }
                    }
                    Toast.makeText(mContext, mContext.getString(R.string.unknown_error_network),
                            Toast.LENGTH_LONG).show();
                    iHttpResponse.onHttpError("", idRequest, 0);
                }
            });
        }, 150);
    }

    public void requestApiNoProgress(final Call<ResponseModel> call, final int idRequest) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (call == null) {
                return;
            }
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call1, Response<ResponseModel> response) {
                    ResponseModel responseModel = response.body();
                    if (responseModel != null) {
                        if (responseModel.getStatus() == 200) {
                            iHttpResponse.onHttpComplete(responseModel.getMessage().toString(), idRequest);
                        } else {
                            Toast.makeText(mContext, responseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            iHttpResponse.onHttpError(responseModel.getMessage().toString(), idRequest, responseModel.getStatus());
                        }
                    } else {
                        iHttpResponse.onHttpError(mContext.getString(R.string.unknown_error_network), idRequest, 0);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call1, Throwable t) {
                    Toast.makeText(mContext, mContext.getString(R.string.unknown_error_network),
                            Toast.LENGTH_LONG).show();
                    iHttpResponse.onHttpError("", idRequest, 0);
                }
            });
        }, 150);
    }

    public void requestPostApi(final Call<ResultModel> call, final int idRequest) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (call == null) {
                return;
            }
            progressBar.show();
            progressBar.setContentView(R.layout.dialog_progress_bar);
            call.enqueue(new Callback<ResultModel>() {
                @Override
                public void onResponse(Call<ResultModel> call1, Response<ResultModel> response) {
                    if (mContext instanceof Activity) {
                        if (!((Activity) mContext).isFinishing())
                            if (progressBar != null && progressBar.isShowing()) {
                                progressBar.dismiss();
                            }
                    }

                    ResultModel responseModel = response.body();
                    if (responseModel != null) {
                        if (responseModel.getStatus() == 200) {
                            iHttpResponse.onHttpComplete(responseModel.getMessage(), idRequest);
                        } else {
                            Toast.makeText(mContext.getApplicationContext(), responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        iHttpResponse.onHttpError(mContext.getString(R.string.unknown_error_network), idRequest, 0);
                    }
                }

                @Override
                public void onFailure(Call<ResultModel> call1, Throwable t) {
                    if (mContext instanceof Activity) {
                        if (!((Activity) mContext).isFinishing())
                            if ((progressBar != null) && progressBar.isShowing()) {
                                progressBar.dismiss();
                            }
                    }
                    Toast.makeText(mContext.getApplicationContext(), mContext.getString(R.string.unknown_error_network),
                            Toast.LENGTH_LONG).show();
                    iHttpResponse.onHttpError("", idRequest, 0);
                }
            });
        }, 150);
    }

    public Call<ResponseModel> login(String username, String password) {
        MyEndPoint loginService = ServiceGenerator.createServiceLogin(MyEndPoint.class, username, password);
        return loginService.getTokenAdmin();
    }

    public Call<ResponseModel> getAllReservationsByDay(String formattedDate) {
        String accessToken = DentalManageApp.getToken();
        MyEndPoint mainService = ServiceGenerator.createServiceMain(MyEndPoint.class, accessToken);
        return mainService.getAllReservationsByDay(formattedDate);
    }

    public Call<ResponseModel> getAllUserByAdmin(int id) {
        String accessToken = DentalManageApp.getToken();
        MyEndPoint mainService = ServiceGenerator.createServiceMain(MyEndPoint.class, accessToken);
        return mainService.getAllUserByAdmin(id);
    }

    public Call<ResponseModel> getAdminInfoById(int id) {
        String accessToken = DentalManageApp.getToken();
        MyEndPoint mainService = ServiceGenerator.createServiceMain(MyEndPoint.class, accessToken);
        return mainService.getAdminInfoById(id);
    }

    public Call<ResultModel> saveUserInfo(RegisterModel model) {
        String accessToken = DentalManageApp.getToken();
        MyEndPoint mainService = ServiceGenerator.createServiceMain(MyEndPoint.class, accessToken);
        JsonObject data = new JsonObject();

        JsonObject user = new JsonObject();
        user.addProperty("username", model.getUsername());
        user.addProperty("adminId", model.getAdminId());

        JsonObject info = new JsonObject();
        info.addProperty("birthday", model.getBirthday());
        info.addProperty("firstName", model.getFirstName());
        info.addProperty("lastName", model.getLastName());
        info.addProperty("firstNickName", model.getFirstNickName());
        info.addProperty("lastNickName", model.getLastNickName());
        info.addProperty("note", model.getNote());
        info.addProperty("gender", model.getGender());
        info.addProperty("reservationDate", model.getReservationDate());
        data.add("user", user);
        data.add("info", info);

        JsonObject registrationData = new JsonObject();
        registrationData.add("data", data);
        return mainService.saveUserInfo(registrationData);
    }

    public Call<ResultModel> updateUserInfo(RegisterModel model) {
        String accessToken = DentalManageApp.getToken();
        MyEndPoint mainService = ServiceGenerator.createServiceMain(MyEndPoint.class, accessToken);
        JsonObject data = new JsonObject();

        JsonObject info = new JsonObject();
        info.addProperty("birthday", model.getBirthday());
        info.addProperty("firstName", model.getFirstName());
        info.addProperty("lastName", model.getLastName());
        info.addProperty("firstNickName", model.getFirstNickName());
        info.addProperty("lastNickName", model.getLastNickName());
        info.addProperty("note", model.getNote());
        info.addProperty("gender", model.getGender());
        info.addProperty("reservationDate", model.getReservationDate());
        info.addProperty("pushNotification", model.getPushNotification());
        data.add("info", info);

        JsonObject registrationData = new JsonObject();
        registrationData.add("data", data);
        return mainService.updateUserInfo(model.getId(), registrationData);
    }

    public Call<ResponseModel> getUserInfoById(int id) {
        String accessToken = DentalManageApp.getToken();
        MyEndPoint mainService = ServiceGenerator.createServiceMain(MyEndPoint.class, accessToken);
        return mainService.getUserInfoById(id);
    }

    public Call<ResultModel> saveAdminInfo(AdminModel model) {
        String accessToken = DentalManageApp.getToken();
        MyEndPoint mainService = ServiceGenerator.createServiceMain(MyEndPoint.class, accessToken);
        JsonObject info = new JsonObject();
        //info.addProperty("id", model.getId());
        info.addProperty("address", model.getAddress());
        info.addProperty("closeDays", model.getCloseDays());
        info.addProperty("fullName", model.getFullName());
        info.addProperty("notificationText1", model.getNotificationText1());
        info.addProperty("notificationText2", model.getNotificationText2());
        info.addProperty("notificationTime1", model.getNotificationTime1());
        info.addProperty("notificationTime2", model.getNotificationTime2());
        info.addProperty("technique", model.getTechnique());
        info.addProperty("tel", model.getTel());
//        info.addProperty("numOfDoctors", model.getNumOfDoctors());
//        info.addProperty("timeForPerPatient", model.getTimeForPerPatient());
//        info.addProperty("free", model.getFree());
//        data.addProperty("createdDate", model.getCreatedDate());
//        data.addProperty("updatedDate", model.getUpdatedDate());
        info.addProperty("practiceRemark", model.getPracticeRemark());
        info.addProperty("remark", model.getRemark());

        JsonObject workingObj;
        JsonArray arrWorkingObj = new JsonArray();
        for (int i = 0; i < 8; i++) {
            workingObj = new JsonObject();
            //workingObj.addProperty("id", model.getWorkingSet().get(i).getId());
//            workingObj.addProperty("idWorkingDay", model.getWorkingSet().get(i).getIdWorkingDay());
            workingObj.addProperty("firstShiftFromHour", model.getWorkingSet().get(i).getFirstShiftFromHour());
            workingObj.addProperty("firstShiftFromMin", model.getWorkingSet().get(i).getFirstShiftFromMin());
            workingObj.addProperty("firstShiftToHour", model.getWorkingSet().get(i).getFirstShiftToHour());
            workingObj.addProperty("firstShiftToMin", model.getWorkingSet().get(i).getFirstShiftToMin());
            workingObj.addProperty("secondShiftFromHour", model.getWorkingSet().get(i).getSecondShiftFromHour());
            workingObj.addProperty("secondShiftFromMin", model.getWorkingSet().get(i).getSecondShiftFromMin());
            workingObj.addProperty("secondShiftToHour", model.getWorkingSet().get(i).getSecondShiftToHour());
            workingObj.addProperty("secondShiftToMin", model.getWorkingSet().get(i).getSecondShiftToMin());
            arrWorkingObj.add(workingObj);
        }
        info.add("workingSet", arrWorkingObj);

        JsonObject data = new JsonObject();
        data.add("info", info);

        JsonObject adminData = new JsonObject();
        adminData.add("data", data);
        return mainService.saveAdminInfo(model.getId(), adminData);
    }
}
