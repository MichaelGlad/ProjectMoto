package net.glm.motohelp;

import android.app.Application;
import android.content.Context;

import net.glm.motohelp.presenters.ManagerPresenter;

/**
 * Created by Michael on 12/09/2017.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate (){
        super.onCreate();
        MyApplication.context = getApplicationContext();
        ManagerPresenter.initInstance();
    }

    public static Context getAppContext(){
        return MyApplication.context;
    }
}
