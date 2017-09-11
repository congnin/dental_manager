package vn.stage.dental_manager;

import android.content.Context;

import vn.stage.dental_manager.data.UserPreferences;

/**
 * Created by congn on 9/11/2017.
 */

public class ClientConfig {
    private ClientConfig() {
    }

    private static boolean initialized = false;

    public static synchronized void initialize(Context context) {
        if (initialized) {
            return;
        }
        UserPreferences.init(context);
        initialized = true;
    }
}
