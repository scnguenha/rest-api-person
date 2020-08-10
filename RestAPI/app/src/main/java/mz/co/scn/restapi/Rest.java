package mz.co.scn.restapi;

import android.app.Application;

import mz.co.scn.restapi.utils.SessionManager;

/**
 * Created by Sidónio Goenha on 08/08/2020.
 */
public class Rest extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SessionManager.init(this);
    }
}
