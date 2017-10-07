package mansi.com.sensorlibrary;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by sphere76 on 16/6/17.
 */
public class ProximitySensorActivity extends AppCompatActivity{

    TextView proximity;
    SensorManager sensorManager;
    Sensor proximity_sensor;
    SensorEvent sevent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_sensor);

        proximity = (TextView)findViewById(R.id.proximity);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        proximity_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(proximity_sensor != null)
        {
            sensorManager.registerListener(ProximitySensorListener,proximity_sensor,2 * 1000 * 1000);
        }
        else
        {
            proximity.setText("Proximity Sensor is not Available");
        }
    }

    private final SensorEventListener ProximitySensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_PROXIMITY)
            {
                proximity.setText("Proximity Sensor Value:"+event.values[0]);
                if(event.values[0] < proximity_sensor.getMaximumRange())
                {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
                else
                {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
