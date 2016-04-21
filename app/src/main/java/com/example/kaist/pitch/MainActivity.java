package com.example.kaist.pitch;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private RelativeLayout mContainerView;

    private TextView mTextAzimuth;
    private TextView mTextPitch;
    private TextView mTextRoll;

    private TextView mTextCalibration;

    private TextView mTextX;
    private TextView mTextY;
    private TextView mTextZ;

    private TextView mTextX2;
    private TextView mTextY2;
    private TextView mTextZ2;

    private String TAG = "MainActivity";

    private SensorManager mSensorManager;

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                mTextAzimuth.setText("ori-X\t" + event.values[0]);
                mTextPitch.setText("ori-Y\t" + event.values[1]);
                mTextRoll.setText("ori-Z\t" + event.values[2]);
            } else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                mTextX.setText("acc-X\t" + event.values[0]);
                mTextY.setText("acc-Y\t" + event.values[1]);
                mTextZ.setText("acc-Z\t" + event.values[2]);
            } else if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
                mTextX2.setText("linacc-X\t" + event.values[0]);
                mTextY2.setText("linacc-Y\t" + event.values[1]);
                mTextZ2.setText("linacc-Z\t" + event.values[2]);
            } else if(event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR){
                mTextX.setText("game_rot-x\t" + event.values[0]);
                mTextY.setText("game_rot-y\t" + event.values[1]);
                mTextZ.setText("game_rot-z\t" + event.values[2]);
            } else if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
                mTextX2.setText("rot-x\t" + event.values[0]);
                mTextY2.setText("rot-y\t" + event.values[1]);
                mTextZ2.setText("rot-theta\t" + event.values[3]);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            switch (accuracy) {
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    mTextCalibration.setText("accuracy - medium");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    mTextCalibration.setText("accuracy - high");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    mTextCalibration.setText("accuracy - low");
                    break;
                case SensorManager.SENSOR_STATUS_UNRELIABLE:
                    mTextCalibration.setText("accuracy - unreliable");
                    break;
                case SensorManager.SENSOR_STATUS_NO_CONTACT:
                    mTextCalibration.setText("accuracy - no contact");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (RelativeLayout) findViewById(R.id.container);

        mTextAzimuth = (TextView) findViewById(R.id.azimuth);
        mTextPitch = (TextView) findViewById(R.id.pitch);
        mTextRoll = (TextView) findViewById(R.id.roll);

        mTextCalibration = (TextView) findViewById(R.id.calibration);

        //accelerator can reveal the tilt information
        mTextX = (TextView) findViewById(R.id.accX);
        mTextY = (TextView) findViewById(R.id.accY);
        mTextZ = (TextView) findViewById(R.id.accZ);

        //linear accel can represent the degree of power
        mTextX2 = (TextView) findViewById(R.id.linaccX);
        mTextY2 = (TextView) findViewById(R.id.linaccY);
        mTextZ2 = (TextView) findViewById(R.id.linaccZ);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
        } else {
            mContainerView.setBackground(getResources().getDrawable(R.color.black));
        }
    }
}

