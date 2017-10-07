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
 * Created by sphere76 on 16/6/17.
 */
public class LightSensorActivity extends AppCompatActivity{

    TextView light;
    SensorManager sensorManager;
    Sensor light_sensor;
    SensorEvent sevent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);

        light = (TextView)findViewById(R.id.light);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        light_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(light_sensor != null)
        {
            sensorManager.registerListener(LightSensorListener,light_sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            light.setText("Light Sensor is not Available");
        }
    }

    private final SensorEventListener LightSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT)
            {
                light.setText("Light Intensity:"+event.values[0]+" lux");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
