package net.glm.motohelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import net.glm.motohelp.contracts.ViewPresentorContracts;
import net.glm.motohelp.presenters.ManagerPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPresentorContracts.MainView {


    static final String MY_LOG = "My Log";

    private ViewPresentorContracts.Presenter mPresenter;



    Button btnAccidend;
    ImageButton btnFuel, btnFlatTire, btnChain, btnEngine, btnAnother;
    TextInputEditText etEventInformation;
    User ourUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = ManagerPresenter.getInstance();
        mPresenter.setMainView(this);

        ourUser = mPresenter.getUser();

        Log.d(MY_LOG,"After get User in onCreate");
        if (ourUser != null) Log.d(MY_LOG,"User Name is " + ourUser.getFullName());


        btnAccidend = (Button) findViewById(R.id.btnAccident);
        btnFuel = (ImageButton) findViewById(R.id.btnFuel);
        btnFlatTire = (ImageButton) findViewById(R.id.btnFlatTire);
        btnChain = (ImageButton) findViewById(R.id.btnChain);
        btnEngine = (ImageButton) findViewById(R.id.btnEngine);
        btnAnother = (ImageButton) findViewById(R.id.btnAnother);

        etEventInformation = (TextInputEditText) findViewById(R.id.etEventInformation);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ourUser == null) {

            if (mPresenter == null) {
                mPresenter = ManagerPresenter.getInstance();
            }
            ourUser = mPresenter.getUser();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter = null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void runUserDataActivity() {
        Intent getUserIntent = new Intent(MainActivity.this, InsertUserDataActivity.class);
        startActivity(getUserIntent);

    }
}
