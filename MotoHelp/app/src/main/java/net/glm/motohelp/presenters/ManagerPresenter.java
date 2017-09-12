package net.glm.motohelp.presenters;

import android.content.SharedPreferences;

import net.glm.motohelp.MyApplication;
import net.glm.motohelp.User;
import net.glm.motohelp.contracts.ViewPresentorContracts;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Michael on 12/09/2017.
 */

public class ManagerPresenter implements ViewPresentorContracts.Presenter {

    static final String MY_LOG = "My Log";
    private final String USER_NAME = "U.Name";
    private final String USER_PHONE = "U.Phone";
    private final String USER_EMAIL = "U.Email";
    private final String USER_MOTORBIKE = "U.Motorbike";
    private final String USER_BRING_FUEL = "U.BringFuel";
    private final String USER_REPAIR_FLAT_TIRE = "U.R.FlatTire";
    private final String USER_REPAIR_CHAIN = "U.R.Chain";
    private final String USER_REPAIR_ENGINE = "U.R.Engine";
    private final String USER_REPAIR_ANOTHER = "U.R.Another";
    private final String USER_ID = "U.ID";

    private static ManagerPresenter instance;

    private ViewPresentorContracts.MainView mainView;
    private User ourUser;

    private ManagerPresenter(){

    }

    public static void  initInstance(){
        if (instance == null){
            instance = new ManagerPresenter();
        }

    }
    public static synchronized ManagerPresenter getInstance(){
        return instance;
    }



    @Override
    public void setMainView (ViewPresentorContracts.MainView mainView){
        this.mainView = mainView;
    }

    @Override
    public User getUser() {

        if (ourUser == null){
            ourUser = getUserDataFromPhone();
        }

        if (ourUser == null){
            if(mainView != null){
                mainView.runUserDataActivity();
            }
        }

        return ourUser;
    }

    @Override
    public void acceptUserData(User user) {
        ourUser = user;

    }

    private User getUserDataFromPhone() {
        SharedPreferences sharedPreferences;
        sharedPreferences = MyApplication.getAppContext().getSharedPreferences("MyUser", MODE_PRIVATE);

        String savedName = sharedPreferences.getString(USER_NAME, "");
        if (savedName.equals("")) {
            return null;
        }

        String savedPhone = sharedPreferences.getString(USER_PHONE, "");
        if (savedPhone.equals("")) {
            return null;
        }

        int savedID = sharedPreferences.getInt(USER_ID, -1);
        if (savedID == -1) {
            return null;
        }

        User savedUser = new User(
                savedName,
                savedPhone,
                sharedPreferences.getString(USER_EMAIL, ""),
                sharedPreferences.getString(USER_MOTORBIKE, ""),
                sharedPreferences.getBoolean(USER_BRING_FUEL, false),
                sharedPreferences.getBoolean(USER_REPAIR_FLAT_TIRE, false),
                sharedPreferences.getBoolean(USER_REPAIR_CHAIN, false),
                sharedPreferences.getBoolean(USER_REPAIR_ENGINE, false),
                sharedPreferences.getBoolean(USER_REPAIR_ANOTHER, false)
        );

        savedUser.setId(savedID);
        return savedUser;
    }
}
