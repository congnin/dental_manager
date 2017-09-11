package vn.stage.dental_manager;

import android.support.multidex.MultiDexApplication;

/**
 * Created by congn on 9/11/2017.
 */

public class DentalApp extends MultiDexApplication {
    private static DentalApp singleton;

    public static DentalApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        singleton = this;
        ClientConfig.initialize(this);
    }
}
