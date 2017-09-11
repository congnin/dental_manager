package vn.stage.dental_manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import vn.stage.dental_manager.R;
import vn.stage.dental_manager.data.UserPreferences;
import vn.stage.dental_manager.fragment.AuthenticationFragment;

/**
 * Created by congn on 9/11/2017.
 */

public class SplashActivity extends AppCompatActivity {
    public static final String TAG = "SplashActivity";

    private static final int AFTER_DAYS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkShouldLogin();
    }

    private void checkShouldLogin() {
        if (!TextUtils.isEmpty(UserPreferences.getAccessToken())) {
            long now = System.currentTimeMillis();
            long tokenTime = UserPreferences.getTokenTime();
            long diff = now - tokenTime;
            long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (UserPreferences.isRemember() && diffDays <= AFTER_DAYS) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            UserPreferences.clear();
            AuthenticationFragment authenticationFragment = AuthenticationFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.flContainer, authenticationFragment, AuthenticationFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
