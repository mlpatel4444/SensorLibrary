package mansi.com.sensorlibrary;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by sphere76 on 21/6/17.
 */
public class GyroscopeSensorActivity extends AppCompatActivity{

    TextView textX,textY,textZ,result;

    SensorManager sensorManager;
    Sensor sensor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope_sensor);

        textX = (TextView)findViewById(R.id.textX);
        textY = (TextView)findViewById(R.id.textY);
        textZ = (TextView)findViewById(R.id.textZ);
        result = (TextView)findViewById(R.id.result);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(sensor != null)
        {
            onResume();
        }
        else
        {
            result.setText("Gyroscope Sensor is not available");
            //finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gyroListener);
    }

    public SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            textX.setText("X : " + x + " rad/s");
            textY.setText("Y : " + y + " rad/s");
            textZ.setText("Z : " + z + " rad/s");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
