package net.glm.motohelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import net.glm.motohelp.contracts.ViewPresentorContracts;
import net.glm.motohelp.presenters.ManagerPresenter;

public class InsertUserDataActivity extends AppCompatActivity implements View.OnClickListener {


    static final String MY_LOG = "My Log";
    private ViewPresentorContracts.Presenter mPresenter;


    EditText etFullName;
    EditText etPhoneNumber;
    EditText etEmail;
    EditText etMotorbikeKind;

    CheckBox checkBoxFuel;
    CheckBox checkBoxFlatTire;
    CheckBox checkBoxChain;
    CheckBox checkBoxEngine;
    CheckBox checkBoxAnother;

    Button btnAccept;

    User inputUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_user_data);
        mPresenter = ManagerPresenter.getInstance();

        etFullName = (EditText) findViewById(R.id.etFullName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMotorbikeKind = (EditText) findViewById(R.id.etMotobikeKind);

        checkBoxFuel = (CheckBox) findViewById(R.id.checkboxFuel);
        checkBoxFlatTire = (CheckBox) findViewById(R.id.checkboxFlatTire);
        checkBoxChain = (CheckBox) findViewById(R.id.checkboxChain);
        checkBoxEngine = (CheckBox) findViewById(R.id.checkboxEngine);
        checkBoxAnother = (CheckBox) findViewById(R.id.checkboxAnother);

        btnAccept = (Button) findViewById(R.id.btnAccept);

        btnAccept.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String inputName = etFullName.getText().toString();
        if (inputName.equals("")) {
            Toast.makeText(this, " Please input Your Name", Toast.LENGTH_SHORT).show();
            return ;
        }

        String inputPhone = etPhoneNumber.getText().toString();
        if (inputPhone.equals("")) {
            Toast.makeText(this, " Please input Your Phone Number", Toast.LENGTH_SHORT).show();
            return ;
        }

        inputUser = new User(
                inputName,
                inputPhone,
                etEmail.getText().toString(),
                etMotorbikeKind.getText().toString(),
                checkBoxFuel.isChecked(),
                checkBoxFlatTire.isChecked(),
                checkBoxChain.isChecked(),
                checkBoxEngine.isChecked(),
                checkBoxAnother.isChecked()
        );
        mPresenter.acceptUserData(inputUser);
        finish();
    }
}
