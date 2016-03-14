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

    private TextView mTextAccX;
    private TextView mTextAccY;
    private TextView mTextAccZ;

    private TextView mTextLinAccX;
    private TextView mTextLinAccY;
    private TextView mTextLinAccZ;

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
                mTextAccX.setText("acc-X\t" + event.values[0]);
                mTextAccY.setText("acc-Y\t" + event.values[1]);
                mTextAccZ.setText("acc-Z\t" + event.values[2]);
            } else if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
                mTextLinAccX.setText("linacc-X\t" + event.values[0]);
                mTextLinAccY.setText("linacc-Y\t" + event.values[1]);
                mTextLinAccZ.setText("linacc-Z\t" + event.values[2]);
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

        mTextAccX = (TextView) findViewById(R.id.accX);
        mTextAccY = (TextView) findViewById(R.id.accY);
        mTextAccZ = (TextView) findViewById(R.id.accZ);

        mTextLinAccX = (TextView) findViewById(R.id.linaccX);
        mTextLinAccY = (TextView) findViewById(R.id.linaccY);
        mTextLinAccZ = (TextView) findViewById(R.id.linaccZ);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);
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

