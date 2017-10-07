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
public class PressureSensorActivity extends AppCompatActivity implements SensorEventListener{

    TextView pressure;
    SensorManager sensorManager;
    Sensor pressure_sensor;
    float millibars_of_pressure;
    SensorEvent sevent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure_sensor);

        pressure = (TextView)findViewById(R.id.pressure);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        pressure_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        if(pressure_sensor != null)
        {
            onSensorChanged(sevent);
        }
        else
        {
            pressure.setText("Pressure Sensor is not available");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        millibars_of_pressure = event.values[0];
        pressure.setText("Current Pressure:"+millibars_of_pressure);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,pressure_sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
