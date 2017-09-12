package net.glm.motohelp.presenters;

import android.content.SharedPreferences;
import android.util.Log;

import net.glm.motohelp.MyApplication;
import net.glm.motohelp.User;
import net.glm.motohelp.contracts.ViewPresentorContracts;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Michael on 12/09/2017.
 */

public class ManagerPresenter implements ViewPresentorContracts.Presenter {

    static final String MY_LOG = "My Log";
    static final String MY_USER_PREF = "MyUserPref";
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


        Log.d(MY_LOG,"Inside the Get User");
        if (ourUser != null) Log.d(MY_LOG,"The User Now" + ourUser.getFullName() + "  Phone " + ourUser.getPhoneNumber());
        return ourUser;
    }

    @Override
    public void acceptUserData(User user) {
        ourUser = user;
        saveUserDataOnPhone (ourUser);

    }

    private User getUserDataFromPhone() {
        SharedPreferences sharedPreferences;
        sharedPreferences = MyApplication.getAppContext().getSharedPreferences(MY_USER_PREF, MODE_PRIVATE);

        String savedName = sharedPreferences.getString(USER_NAME, "");
        if (savedName.equals("")) {
            return null;
        }

        String savedPhone = sharedPreferences.getString(USER_PHONE, "");
        if (savedPhone.equals("")) {
            return null;
        }

        int savedID = sharedPreferences.getInt(USER_ID, -1);
//        if (savedID == -1) {
//            return null;
//        }

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

    private void saveUserDataOnPhone (User ourUser){

        SharedPreferences sharedPreferences;
        sharedPreferences = MyApplication.getAppContext().getSharedPreferences(MY_USER_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_NAME,ourUser.getFullName())
                .putString(USER_PHONE,ourUser.getPhoneNumber())
                .putString(USER_EMAIL,ourUser.getEmail())
                .putString(USER_MOTORBIKE,ourUser.getMotorbikeKind())
                .putBoolean(USER_BRING_FUEL, ourUser.getBringFuel())
                .putBoolean(USER_REPAIR_FLAT_TIRE, ourUser.getRepairFlatTire())
                .putBoolean(USER_REPAIR_CHAIN, ourUser.getRepairChain())
                .putBoolean(USER_REPAIR_ENGINE, ourUser.getRepairEngine())
                .putBoolean(USER_REPAIR_ANOTHER, ourUser.getRepairAnotherProblems());
        editor.commit();




    }
}
