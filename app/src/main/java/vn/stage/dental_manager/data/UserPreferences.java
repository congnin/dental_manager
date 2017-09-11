package vn.stage.dental_manager.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import vn.stage.dental_manager.data.model.AdminModel;

/**
 * Created by congn on 9/11/2017.
 */

public class UserPreferences {
    private UserPreferences() {
    }

    private static final String TAG = "UserPreferences";

    private static final String PREF_ACCESS_TOKEN = "prefAccessToken";
    private static final String PREF_REMEMBER = "prefRemember";
    private static final String PREF_ID = "prefId";
    private static final String PREF_ADMIN_DATA = "prefAdminData";
    private static final String PREF_TOKEN_DATE = "prefTokenDate";

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

    public static void setAccessToken(String accessToken) {
        if (!TextUtils.isEmpty(accessToken)) {
            prefs.edit().putString(PREF_ACCESS_TOKEN, accessToken).apply();
        } else {
            prefs.edit().putString(PREF_ACCESS_TOKEN, "").apply();
        }
    }

    public static boolean isRemember() {
        return prefs.getBoolean(PREF_REMEMBER, false);
    }

    public static void setRemember(boolean isRemember) {
        prefs.edit().putBoolean(PREF_REMEMBER, isRemember).apply();
    }

    public static int getId() {
        return prefs.getInt(PREF_ID, -1);
    }

    public static void setId(int id) {
        prefs.edit().putInt(PREF_ID, id).apply();
    }

    public static AdminModel getAdminData() {
        Gson gson = new Gson();
        String json = prefs.getString(PREF_ADMIN_DATA, "");
        if (!TextUtils.isEmpty(json)) {
            return gson.fromJson(json, AdminModel.class);
        } else {
            return null;
        }
    }

    public static void setAdminData(AdminModel model) {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        prefs.edit().putString(PREF_ADMIN_DATA, json).apply();
    }

    public static long getTokenTime() {
        return prefs.getLong(PREF_TOKEN_DATE, 0);
    }

    public static void setTokenTime() {
        long now = System.currentTimeMillis();
        prefs.edit().putLong(PREF_TOKEN_DATE, now).apply();
    }

    public static void clear() {
        prefs.edit().clear().apply();
    }
}
