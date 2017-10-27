package com.prostage.dental_manage.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.prostage.dental_manage.core.model.AdminModel;
import com.prostage.dental_manage.core.model.WorkingDayModel;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by congnguyen on 9/8/17.
 */

public class UserPreferences {
    private UserPreferences() {
    }

    private static final String TAG = "UserPreferences";
    public static final String PREF_REMEMBER = "prefRemember";
    public static final String PREF_ACCESS_TOKEN = "prefAccessToken";
    public static final String PREF_ID = "prefId";
    public static final String PREF_DENTIST_DATA = "prefDentistData";
    public static final String PREF_ADDRESS = "prefAddress";
    public static final String PREF_CLOSE_DAYS = "prefCloseDays";
    public static final String PREF_FULL_NAME = "prefFullName";
    public static final String PREF_NOTIFICATION_TEXT1 = "prefNotificationText1";
    public static final String PREF_NOTIFICATION_TEXT2 = "prefNotificationText2";
    public static final String PREF_NOTIFICATION_TIME1 = "prefNotificationTime1";
    public static final String PREF_NOTIFICATION_TIME2 = "prefNotificationTime2";
    public static final String PREF_TECHNIQUE = "prefTechnique";
    public static final String PREF_TEL = "prefTel";
    public static final String PREF_NUM_OF_DOCTORS = "prefNumOfDoctors";
    public static final String PREF_TIME_FOR_PER_PATIENT = "prefTimeForPerPatient";
    public static final String PREF_FREE = "prefFree";
    public static final String PREF_PRACTICE_REMARK = "prefPracticeRemark";
    public static final String PREF_REMARK = "prefRemark";
    public static final String PREF_MON_WORKING_TIME = "prefMonWorkingTime";
    public static final String PREF_TUES_WORKING_TIME = "prefTuesWorkingTime";
    public static final String PREF_WED_WORKING_TIME = "prefWebWorkingTime";
    public static final String PREF_THURS_WORKING_TIME = "prefThursWorkingTime";
    public static final String PREF_FRI_WORKING_TIME = "prefFriWorkingTime";
    public static final String PREF_SAT_WORKING_TIME = "prefSatWorkingTime";
    public static final String PREF_SUN_WORKING_TIME = "prefSunWorkingTime";
    public static final String PREF_HOLIDAY_WORKING_TIME = "prefHolidayWorkingTime";

    private static Context context;
    private static SharedPreferences prefs;

    public static void init(@NonNull Context context) {
        Log.d(TAG, "Creating new instance of UserPreferences");

        UserPreferences.context = context.getApplicationContext();
        UserPreferences.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getAccessToken() {
        return prefs.getString(PREF_ACCESS_TOKEN, "");
    }

    public static int getId() {
        return prefs.getInt(PREF_ID, -1);
    }

    public static String getAddress() {
        return prefs.getString(PREF_ADDRESS, "");
    }

    public static String getCloseDays() {
        return prefs.getString(PREF_CLOSE_DAYS, "");
    }

    public static String getFullName() {
        return prefs.getString(PREF_FULL_NAME, "");
    }

    public static String getNotificationText1() {
        return prefs.getString(PREF_NOTIFICATION_TEXT1, "");
    }

    public static String getNotificationText2() {
        return prefs.getString(PREF_NOTIFICATION_TEXT2, "");
    }

    public static int getNotificationTime1() {
        return prefs.getInt(PREF_NOTIFICATION_TIME1, -1);
    }

    public static int getNotificationTime2() {
        return prefs.getInt(PREF_NOTIFICATION_TIME2, -1);
    }

    public static String getTechnique() {
        return prefs.getString(PREF_TECHNIQUE, "");
    }

    public static String getTel() {
        return prefs.getString(PREF_TEL, "");
    }

    public static int getNumOfDoctors() {
        return prefs.getInt(PREF_NUM_OF_DOCTORS, 0);
    }

    public static int getTimeForPerPatient() {
        return prefs.getInt(PREF_TIME_FOR_PER_PATIENT, 0);
    }

    public static boolean getFree() {
        return prefs.getBoolean(PREF_FREE, true);
    }

    public static String getPracticeRemark() {
        return prefs.getString(PREF_PRACTICE_REMARK, "");
    }

    public static String getRemark() {
        return prefs.getString(PREF_REMARK, "");
    }

    public static WorkingDayModel getMonWorkingTime() {
        return readTimeArray(prefs.getString(PREF_MON_WORKING_TIME, null));
    }

    public static WorkingDayModel getTuesWorkingTime() {
        return readTimeArray(prefs.getString(PREF_TUES_WORKING_TIME, null));
    }

    public static WorkingDayModel getWebWorkingTime() {
        return readTimeArray(prefs.getString(PREF_WED_WORKING_TIME, null));
    }

    public static WorkingDayModel getThuWorkingTime() {
        return readTimeArray(prefs.getString(PREF_THURS_WORKING_TIME, null));
    }

    public static WorkingDayModel getFriWorkingTime() {
        return readTimeArray(prefs.getString(PREF_FRI_WORKING_TIME, null));
    }

    public static WorkingDayModel getSatWorkingTime() {
        return readTimeArray(prefs.getString(PREF_SAT_WORKING_TIME, null));
    }

    public static WorkingDayModel getSunWorkingTime() {
        return readTimeArray(prefs.getString(PREF_SUN_WORKING_TIME, null));
    }

    public static WorkingDayModel getHolidayWorkingTime() {
        return readTimeArray(prefs.getString(PREF_HOLIDAY_WORKING_TIME, null));
    }

    public static void setMonWorkingTime(WorkingDayModel model) {
        prefs.edit().putString(PREF_MON_WORKING_TIME, writeTimeArray(model)).apply();
    }

    public static void setTueWorkingTime(WorkingDayModel model) {
        prefs.edit().putString(PREF_TUES_WORKING_TIME, writeTimeArray(model)).apply();
    }

    public static void setWebWorkingTime(WorkingDayModel model) {
        prefs.edit().putString(PREF_WED_WORKING_TIME, writeTimeArray(model)).apply();
    }

    public static void setThuWorkingTime(WorkingDayModel model) {
        prefs.edit().putString(PREF_THURS_WORKING_TIME, writeTimeArray(model)).apply();
    }

    public static void setFriWorkingTime(WorkingDayModel model) {
        prefs.edit().putString(PREF_FRI_WORKING_TIME, writeTimeArray(model)).apply();
    }

    public static void setSatWorkingTime(WorkingDayModel model) {
        prefs.edit().putString(PREF_SAT_WORKING_TIME, writeTimeArray(model)).apply();
    }

    public static void setSunWorkingTime(WorkingDayModel model) {
        prefs.edit().putString(PREF_SUN_WORKING_TIME, writeTimeArray(model)).apply();
    }

    public static void setHolidayWorkingTime(WorkingDayModel model) {
        prefs.edit().putString(PREF_HOLIDAY_WORKING_TIME, writeTimeArray(model)).apply();
    }

    private static String writeTimeArray(WorkingDayModel model) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(model.getFirstShiftFromHour());
        jsonArray.put(model.getFirstShiftFromMin());
        jsonArray.put(model.getFirstShiftToHour());
        jsonArray.put(model.getFirstShiftToMin());
        jsonArray.put(model.getSecondShiftFromHour());
        jsonArray.put(model.getSecondShiftFromMin());
        jsonArray.put(model.getSecondShiftToHour());
        jsonArray.put(model.getSecondShiftToMin());

        return jsonArray.toString();
    }

    private static WorkingDayModel readTimeArray(String valueFromPrefs) {
        WorkingDayModel result = new WorkingDayModel();
        if (valueFromPrefs == null) {
            result.setFirstShiftFromHour(0);
            result.setFirstShiftFromMin(0);
            result.setFirstShiftToHour(0);
            result.setFirstShiftToMin(0);

            result.setSecondShiftFromHour(0);
            result.setSecondShiftFromMin(0);
            result.setSecondShiftToHour(0);
            result.setSecondShiftToMin(0);
        } else {
            try {
                JSONArray jsonArray = new JSONArray(valueFromPrefs);
                result.setFirstShiftFromHour(Integer.valueOf(jsonArray.getString(0)));
                result.setFirstShiftFromMin(Integer.valueOf(jsonArray.getString(1)));
                result.setFirstShiftToHour(Integer.valueOf(jsonArray.getString(2)));
                result.setFirstShiftToMin(Integer.valueOf(jsonArray.getString(3)));

                result.setSecondShiftFromHour(Integer.valueOf(jsonArray.getString(4)));
                result.setSecondShiftFromMin(Integer.valueOf(jsonArray.getString(5)));
                result.setSecondShiftToHour(Integer.valueOf(jsonArray.getString(6)));
                result.setSecondShiftToMin(Integer.valueOf(jsonArray.getString(7)));
            } catch (JSONException e) {
                Log.e(TAG, "Got JSON error when trying to get speeds from JSONArray");
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void setDentist(AdminModel adminModel) {
        Gson gson = new Gson();
        String json = gson.toJson(adminModel);
        prefs.edit().putString(PREF_DENTIST_DATA, json).apply();
    }

    public static AdminModel getDentist() {
        Gson gson = new Gson();
        String json = prefs.getString(PREF_DENTIST_DATA, "");
        if (!TextUtils.isEmpty(json)) {
            return gson.fromJson(json, AdminModel.class);
        } else {
            return null;
        }
    }

    public static boolean isRemember() {
        return prefs.getBoolean(PREF_REMEMBER, false);
    }

    public static void setRemember(boolean remember) {
        prefs.edit().putBoolean(PREF_REMEMBER, remember).apply();
    }

    public static void clear() {
        prefs.edit().clear().apply();
    }

    public static void setAccessToken(String accessToken) {
        if (!TextUtils.isEmpty(accessToken)) {
            prefs.edit().putString(PREF_ACCESS_TOKEN, accessToken).apply();
        } else {
            prefs.edit().putString(PREF_ACCESS_TOKEN, "").apply();
        }
    }

    public static void setId(int id) {
        prefs.edit().putInt(PREF_ID, id).apply();
    }
}
