package mansi.com.sensorlibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView sensor_list,pressure_sensor,light_sensor,proximity_sensor,accelerometer_sensor,voicerecording,step_counter,gyroscope_sensor;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensor_list = (TextView)findViewById(R.id.sensor_list);
        sensor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(),AvailableSensorActivity.class);
                startActivity(i);
            }
        });

        pressure_sensor = (TextView)findViewById(R.id.pressure_sensor);
        pressure_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(),PressureSensorActivity.class);
                startActivity(i);
            }
        });

        light_sensor = (TextView)findViewById(R.id.light_sensor);
        light_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(),LightSensorActivity.class);
                startActivity(i);
            }
        });

        proximity_sensor = (TextView)findViewById(R.id.proximity_sensor);
        proximity_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(),ProximitySensorActivity.class);
                startActivity(i);
            }
        });

        accelerometer_sensor = (TextView)findViewById(R.id.accelerometer_sensor);
        accelerometer_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(),AccelerometerActivity.class);
                startActivity(i);
            }
        });

        voicerecording = (TextView)findViewById(R.id.voicerecording);
        voicerecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(),VoiceRecordingActivity.class);
                startActivity(i);
            }
        });

        step_counter = (TextView)findViewById(R.id.stepcounter);
        step_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(),StepCounterActivity.class);
                startActivity(i);
            }
        });

        gyroscope_sensor = (TextView)findViewById(R.id.gyroscope_sensor);
        gyroscope_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(),GyroscopeSensorActivity.class);
                startActivity(i);
            }
        });
    }
}
