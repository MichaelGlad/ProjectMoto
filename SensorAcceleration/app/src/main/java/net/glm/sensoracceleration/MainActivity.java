package net.glm.sensoracceleration;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvSensorText;
    Button btnSensAccelerate;
    Button btnRotate;
    SensorManager sensorManager;
    Sensor sensorAcceliration,sensorLinearAcceleration,sensorGravity;
    Sensor sensorMagnet;

    StringBuilder stringBuilder = new StringBuilder();

    Timer timer;

    int rotation;

    float[] valuesAccel = new float[3];
    float[] valuesAccelMotion = new float[3];
    float[] valuesAccelGravity = new float[3];
    float[] valuesLinAccel = new float[3];
    float[] valuesGravity = new float[3];
    float[] valuesMagnetic = new float[3];

    float[] valuesResult = new float[3];
    float[] valuesResult2 = new float[3];

    float[] inR = new float[9];
    float[] outR = new float[9];
    float[] rotate = new float[9];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSensorText = (TextView) findViewById(R.id.tvSensorText);
        btnSensAccelerate = (Button) findViewById(R.id.btnSensAccelerate);
        btnSensAccelerate.setOnClickListener(this);
        btnRotate = (Button) findViewById(R.id.btnRotate);
        btnRotate.setOnClickListener(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorAcceliration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorLinearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
        timer.cancel();
    }

    void showAccelometerGravityInfo() {
        stringBuilder.setLength(0);
        stringBuilder.append("Accelerometer: " + format(valuesAccel))
                .append("\nAccel motion: " + format(valuesAccelMotion))
                .append("\nAccel gravity : " + format(valuesAccelGravity))
                .append("\n\nLin accel : " + format(valuesLinAccel))
                .append("\nGravity : " + format(valuesGravity));
        tvSensorText.setText(stringBuilder);
    }

    void showRotationInfo() {
        stringBuilder.setLength(0);
        stringBuilder.append("Orientation : " + format(valuesResult))
                .append("\nOrientation 2: " + format(valuesResult2))
        ;
        tvSensorText.setText(stringBuilder);
    }

    String format (float values[]){
        return String.format("%1$.1f\t\t%2$.1f\t\t\t\t\t\t\t\t%3$.1f",values[0],values[1],values[2]);
    }

    SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            switch (event.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i = 0; i < 3 ; i++) {
                        valuesAccel[i] = event.values[i];
                        valuesAccelGravity[i] = (float) (0.1* event.values[i] + 0.9 *valuesAccelGravity[i]);
                        valuesAccelMotion [i] = event.values[i] - valuesAccelGravity[i];
                    }
                    break;

                case Sensor.TYPE_LINEAR_ACCELERATION:
                    for (int i = 0; i < 3 ; i++) {
                        valuesLinAccel[i] = event.values[i];
                    }
                    break;

                case Sensor.TYPE_GRAVITY:
                    for (int i = 0; i < 3; i++) {
                        valuesGravity[i] = event.values[i];
                    }
                    break;

                case Sensor.TYPE_MAGNETIC_FIELD:
                    for (int i = 0; i < 3; i++) {
                        valuesMagnetic[i] = event.values[i];
                    }
                    break;

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSensAccelerate){
            sensorManager.registerListener(sensorListener,sensorAcceliration,SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(sensorListener,sensorLinearAcceleration,SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(sensorListener,sensorGravity,SensorManager.SENSOR_DELAY_NORMAL);

            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showAccelometerGravityInfo();
                        }
                    });

                }
            };
            timer.schedule(task,0,400);

        }


        if (v.getId() == R.id.btnRotate){
            sensorManager.registerListener(sensorListener, sensorAcceliration, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(sensorListener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL);

            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getDeviceOrientation();
                            getActualDeviceOrientation();
                            showRotationInfo();
                        }
                    });
                }
            };
            timer.schedule(task, 0, 400);

            WindowManager windowManager = ((WindowManager) getSystemService(Context.WINDOW_SERVICE));
            Display display = windowManager.getDefaultDisplay();
            rotation = display.getRotation();

        }

    }

    void getDeviceOrientation() {
        SensorManager.getRotationMatrix(rotate, null, valuesAccel, valuesMagnetic);
        SensorManager.getOrientation(rotate, valuesResult);

        valuesResult[0] = (float) Math.toDegrees(valuesResult[0]);
        valuesResult[1] = (float) Math.toDegrees(valuesResult[1]);
        valuesResult[2] = (float) Math.toDegrees(valuesResult[2]);
        return;
    }

    void getActualDeviceOrientation() {
        SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnetic);
        int x_axis = SensorManager.AXIS_X;
        int y_axis = SensorManager.AXIS_Y;
        switch (rotation) {
            case (Surface.ROTATION_0): break;
            case (Surface.ROTATION_90):
                x_axis = SensorManager.AXIS_Y;
                y_axis = SensorManager.AXIS_MINUS_X;
                break;
            case (Surface.ROTATION_180):
                y_axis = SensorManager.AXIS_MINUS_Y;
                break;
            case (Surface.ROTATION_270):
                x_axis = SensorManager.AXIS_MINUS_Y;
                y_axis = SensorManager.AXIS_X;
                break;
            default: break;
        }
        SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR);
        SensorManager.getOrientation(outR, valuesResult2);
        valuesResult2[0] = (float) Math.toDegrees(valuesResult2[0]);
        valuesResult2[1] = (float) Math.toDegrees(valuesResult2[1]);
        valuesResult2[2] = (float) Math.toDegrees(valuesResult2[2]);
        return;
    }
}

