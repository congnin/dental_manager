package com.prostage.dental_manage.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.prostage.dental_manage.R;
import com.prostage.dental_manage.authentication.AuthenticationActivity;
import com.prostage.dental_manage.authentication.AuthenticationFragment;
import com.prostage.dental_manage.base.MainActivity;
import com.prostage.dental_manage.base.UserPreferences;

import java.util.concurrent.TimeUnit;

/**
 * Created by congnguyen on 9/11/17.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int AFTER_DAYS = 1;

    private static final String PREFS_NAME = "TokenTimePrefs";
    private static final String KEY_TIME_GET_TOKEN = "KEY_TIME_GET_TOKEN";
    private static SharedPreferences mPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        long tokenTime = mPreferences.getLong(KEY_TIME_GET_TOKEN, 0);

        if (tokenTime == 0) {
            AuthenticationFragment authenticationFragment = AuthenticationFragment.newInstance();
            loadFragment(authenticationFragment);
        } else {
            if (!checkEnoughOneDayTime() && UserPreferences.isRemember()
                    && !TextUtils.isEmpty(UserPreferences.getAccessToken())) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                UserPreferences.clear();
                AuthenticationFragment authenticationFragment = AuthenticationFragment.newInstance();
                loadFragment(authenticationFragment);
            }
        }
    }

    private boolean checkEnoughOneDayTime() {
        long now = System.currentTimeMillis();
        long tokenTime = mPreferences.getLong(KEY_TIME_GET_TOKEN, now);
        long diff = now - tokenTime;
        long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return diffDays >= AFTER_DAYS;
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.flContainer, fragment, "main")
                .addToBackStack(null)
                .commit();
    }
}
