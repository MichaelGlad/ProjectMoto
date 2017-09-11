package net.glm.motohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {


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


    Button btnAccidend;
    ImageButton btnFuel, btnFlatTire, btnChain, btnEngine, btnAnother;
    TextInputEditText etEventInformation;
    User curentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curentUser = getUserDataFromPhone();
//        if (curentUser == null){
//            Intent getUserIntent = new Intent(this,InsertUserDataActivity.class);
//            startActivity(getUserIntent);
//        }

        btnAccidend = (Button) findViewById(R.id.btnAccident);
        btnFuel = (ImageButton) findViewById(R.id.btnFuel);
        btnFlatTire = (ImageButton) findViewById(R.id.btnFlatTire);
        btnChain = (ImageButton) findViewById(R.id.btnChain);
        btnEngine = (ImageButton) findViewById(R.id.btnEngine);
        btnAnother = (ImageButton) findViewById(R.id.btnAnother);

        etEventInformation = (TextInputEditText) findViewById(R.id.etEventInformation);



        btnAccidend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getUserIntent = new Intent(MainActivity.this, InsertUserDataActivity.class);
                startActivity(getUserIntent);
            }
        });
    }



    private User getUserDataFromPhone() {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("MyUser", MODE_PRIVATE);

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
                savedName, savedPhone,
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
