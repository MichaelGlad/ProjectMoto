package net.glm.motohelp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by Michael on 10/09/2017.
 */

public interface UserSendToServer {

    @POST("user")
    Call<Integer> createAccount (@Body User user);
}
