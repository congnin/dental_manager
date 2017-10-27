package com.prostage.dental_manage;

import android.content.Context;

import com.prostage.dental_manage.base.UserPreferences;

/**
 * Created by congnguyen on 9/8/17.
 */

public class ClientConfig {
    private ClientConfig(){}

    private static boolean initialized = false;

    public static synchronized void initialize(Context context) {
        if(initialized) {
            return;
        }

        UserPreferences.init(context);
        initialized = true;
    }
}
