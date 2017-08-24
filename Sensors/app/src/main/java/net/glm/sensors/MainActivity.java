package net.glm.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static final String MY_LOGS = "myLogs";
    TextView tvScrollText;
    SensorManager sensorManager;
    List<Sensor> sensorsList;
    Sensor sensorLight;
    Sensor sensorAcceleration;
    Button btnList,btnLight,btnAcceliration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScrollText = (TextView) findViewById(R.id.tvScrollText);
        btnList = (Button) findViewById(R.id.btnSensList);
        btnList.setOnClickListener(this);
        btnLight = (Button) findViewById(R.id.btnSensLight);
        btnLight.setOnClickListener(this);
        btnAcceliration = (Button) findViewById(R.id.btnSensAccelirate);
        btnAcceliration.setOnClickListener(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorsList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnSensList){
            sensorManager.unregisterListener(listenerLight, sensorLight);
            StringBuilder sb = new StringBuilder();

            Log.d(MY_LOGS,"The btnSensList is clicked");

            for (Sensor sensor : sensorsList) {
                sb.append("name = ").append(sensor.getName())
                        .append(", type = ").append(sensor.getType())
                        .append("\nvendor = ").append(sensor.getVendor())
                        .append(" ,version = ").append(sensor.getVersion())
                        .append("\nmax = ").append(sensor.getMaximumRange())
                        .append(", resolution = ").append(sensor.getResolution())
                        .append("\n--------------------------------------\n");
            }
            tvScrollText.setText(sb);

        }
        if (view.getId() == R.id.btnSensLight){
            sensorManager.registerListener(listenerLight, sensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }
        if (view.getId() == R.id.btnSensAccelirate){

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerLight,sensorLight);
    }

    SensorEventListener listenerLight = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            tvScrollText.setText(String.valueOf(event.values[0]));
        }
    };
}
