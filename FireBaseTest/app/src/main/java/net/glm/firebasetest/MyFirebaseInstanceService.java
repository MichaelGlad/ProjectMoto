package net.glm.firebasetest;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Michael on 08/09/2017.
 */

public class MyFirebaseInstanceService extends FirebaseInstanceIdService{

    private static final String REG_TOKEN = "Reg-Token";
    @Override
    public void onTokenRefresh() {
        String recentToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recentToken);
    }
}
